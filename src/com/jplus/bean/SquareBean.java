/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.bean;

import com.jplus.forms.Main;
import com.jplus.forms.MainFrame;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 一个方块的基本模型
 *
 * @author hyberbin
 */
public class SquareBean extends Thread {

    /**
     * 该方块有点阵列表
     */
    public LinkedList<PointBean> pointList;
    /** 当前块是否是活动状态 */
    public static boolean isLive = false;
    /** 当前块的类型 */
    public int type;
    /** 每次等待时间 */
    public static int sleep = 1000;
    /** 得分的倍率 */
    public int bei;
    /** 当前是否是暂停状态 */
    public boolean isPause = false;
    /** 当前块是否是虚拟的 */
    public boolean isVirtual = false;

    public SquareBean(int type) {
        this.type = type;
        //isLive = false;
        bei = 1;
    }

    /**
     * 旋转当前方块
     */
    public boolean rotation() {
        MainFrame.painter.drawWhite(this);
        PointBean c = getCenter();
        for (PointBean p : pointList) {
            int x = -p.getY() + c.getX() + c.getY();
            int y = p.getX() + c.getY() - c.getX();
            p.setX(x);
            p.setY(y);
        }
        boolean b = isDone(0);
        if (!b) {//如果不能旋转就还原
            for (PointBean p : pointList) {
                int x = p.getY() + c.getX() - c.getY();
                int y = -p.getX() + c.getY() + c.getX();
                p.setX(x);
                p.setY(y);
            }
        }
        MainFrame.painter.drawBlack(this);
        return b;
    }

    private PointBean getCenter() {
        return new PointBean(pointList.getFirst().getX(), pointList.getFirst().getY());
    }

    /**
     * 往下落一个
     */
    public synchronized boolean drop() {
        if (isPause) {
            return true;
        }
        boolean b = isDone(2);
        if (b) {
            MainFrame.painter.drawWhite(this);
            for (PointBean p : pointList) {
                p.setX(p.getX() + 1);
            }
            MainFrame.painter.drawBlack(this);
        }else{
            setBools();
        }
        return b;
    }

    /**
     * 向左
     */
    public synchronized boolean left() {
        boolean b = isDone(3);
        if (b) {
            MainFrame.painter.drawWhite(this);
            for (PointBean p : pointList) {
                p.setY(p.getY() - 1);
            }
            MainFrame.painter.drawBlack(this);
        }
        return b;
    }

    /**
     * 向右
     */
    public synchronized boolean right() {
        boolean b = isDone(1);
        if (b) {
            MainFrame.painter.drawWhite(this);
            for (PointBean p : pointList) {
                p.setY(p.getY() + 1);
            }
            MainFrame.painter.drawBlack(this);
        }
        return b;
    }

    /**
     * 是否已经完成下落过程
     *
     * @return
     */
    public boolean isDone(int f) {
        for (PointBean p : pointList) {
            switch (f) {
                case 0:
                    int x = p.getX();
                    int y = p.getY();
                    if (!(x >= 0 && x < Main.sizeX && y >= 0 && y < Main.sizeY && !MainFrame.bools.get(x)[y])) {
                        return false;
                    }
                    break;
                case 1://向右
                    if (p.getY() + 1 >= Main.sizeY) {
                        return false;
                    }
                    if (MainFrame.bools.get(p.getX())[p.getY() + 1]) {
                        return false;
                    }
                    break;
                case 2://向下
                    if (p.getX() + 1 >= Main.sizeX) {
                        return false;
                    }
                    if (MainFrame.bools.get(p.getX() + 1)[p.getY()]) {
                        return false;
                    }
                    break;
                case 3://向左
                    if (p.getY() - 1 < 0) {
                        return false;
                    }
                    if (MainFrame.bools.get(p.getX())[p.getY() - 1]) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    /**
     * 设置位置标记
     */
    private void setBools() {
        if (isVirtual) {
            return;
        }
        for (PointBean p : pointList) {
            MainFrame.bools.get(p.getX())[p.getY()] = true;
            if (p.getX() <= 1) {
                MainFrame.isGameOver = true;
            }
        }
        for (int i = 0; i < pointList.size(); i++) {
            PointBean p = pointList.get(i);
            Boolean[] bools = MainFrame.bools.get(p.getX());
            boolean flag = true;
            for (Boolean b : bools) {
                if (!b) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                remove(p.getX());
                bei++;
                i--;
            }
        }
    }

    /**
     * 一行已经满了消掉
     * @param row 行号
     */
    private void remove(int row) {
        MainFrame.bools.remove(row);
        Boolean[] bool = new Boolean[Main.sizeY];
        for (int y = 0; y < Main.sizeY; y++) {
            bool[y] = false;
        }
        MainFrame.bools.addFirst(bool);
        MainFrame.score += (bei * 2 - 1) * 100;
        MainFrame.scoreLable.setText(MainFrame.score + "分");
        MainFrame.painter.drawMain();
    }

    @Override
    public void run() {
        isLive = true;
        while (drop()) {
            try {
                sleep(sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(SquareBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isLive = false;
    }
}
