/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.action;

import com.jplus.bean.PointBean;
import com.jplus.bean.SquareBean;
import com.jplus.bean.SquarePool;
import com.jplus.forms.Main;
import com.jplus.forms.MainFrame;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AI策略
 * @author hyberbin
 */
public class AIStrategy extends Thread {

    /** 左移的步数 */
    private int left = 0;
    /** 右移的步数 */
    private int right = 0;
    /** 下移的步数 */
    private int down = 0;
    /** 翻转的次数 */
    private int rotation = 0;
    /** 最大策略分数 */
    private int maxScore = -9999999;
    /** 当前方块对象 */
    private SquareBean square;
    /** 当前方块对象的类型 */
    private int type;
    public static int sleep = 50;

    /**
     * 人工智能策略
     * @param type 块的类型
     */
    public AIStrategy(int type) {
        SquareBean.isLive = true;
        this.type = type;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.sizeY; i++) {//确定要放在左边的位置
            boolean canright=true;
            for (int ro = 0; ro <= 4; ro++) {//确定要旋转几次
                int l = 0, r = 0, d = 0, s,rf=0;
                square = SquarePool.getSquare(type);
                square.isVirtual = true;
                for (int a = 1; a <= ro; a++) {//旋转
                    if (rf<10&&!square.rotation()) {//如果不能旋转
                        square.drop();//下降
                        a--;//旋转
                        d++;
                        rf++;//尝试操作次数
                    }
                }
                while (square.left()) {//移到最左
                    l++;
                }
                for (int a = 0; a < i; a++) {//再向右移                    
                    if (!square.right()) {
                        canright=false;
                        break;
                    }
                    r++;
                }
                if(!canright)break;
                while (square.drop()) {//移到最下 
                }
                s = getScore();
                if (s > maxScore) {
                    maxScore = s;
                    int le = l - r;
                    if (le > 0) {
                        left = le;
                        right = 0;
                    } else {
                        left = 0;
                        right = (-le);
                    }
                    down = d;
                    rotation = ro;
                }
            }
        }
        SquareBean.isLive = true;
        for (int i = 0; i < down; i++) {
            //下降到可以翻的位置
            sleepTime(sleep);
            MainFrame.current.drop();
        }
        for (int i = 0; i < rotation; i++) {//旋转到适当形状
            sleepTime(sleep);
            MainFrame.current.rotation();
        }
        for (int i = 0; i < left; i++) {//左移
            sleepTime(sleep);
            MainFrame.current.left();
        }
        for (int i = 0; i < right; i++) {//右移
            sleepTime(sleep);
            MainFrame.current.right();
        }
        while (MainFrame.current.drop()) {//移到最下  
            sleepTime(sleep);
        }
        SquareBean.isLive = false;
    }

    /**
     * @param square
     * @return
     */
    private int getScore() {
        int score = 0;
        LinkedList<Boolean[]> bools = MainFrame.bools;
        for (PointBean p : square.pointList) {
            //下面考虑加分情况
            if (p.getX() == Main.sizeX - 1) {//行在最下面
                score += 20;
            }
            if (p.getY() == 0) {//列在最左
                score += 20;
            }
            if (p.getY() == Main.sizeY - 1) {//列在最右
                score += 20;
            }
            if (p.getY() - 1 >= 0 && bools.get(p.getX())[p.getY() - 1]) {//左边有
                score += 20;
            }
            if (p.getY() + 1 < Main.sizeY && bools.get(p.getX())[p.getY() + 1]) {//右边有
                score += 20;
            }
            if (p.getX() + 1 < Main.sizeX && bools.get(p.getX() + 1)[p.getY()]) {//下面有
                score += 20;
            }
            //下面考虑高度
            score += (p.getX() * 15);//越在底下越好
        }
        for (PointBean p : square.pointList) {
            //下面考虑减分情况
            for (int i = p.getX(); i < Main.sizeX; i++) {
                if (!bools.get(i)[p.getY()]) {//如果放下去后所在块下面会有空缺就减分
                    score -= 10*i;
                    break;
                }
            }
        }
        for (PointBean p : square.pointList) {
            //下面考虑能消掉时的加分
            for (int i = 0; i < Main.sizeY; i++) {
                if (!(i == p.getY() || bools.get(p.getX())[i])) {
                    return score;
                }
            }
            score += 500;
        }
        return score;
    }

    private void sleepTime(int t) {
        try {
            sleep(t);
        } catch (InterruptedException ex) {
            Logger.getLogger(AIStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
