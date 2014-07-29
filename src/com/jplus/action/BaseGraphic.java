/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.action;

import com.jplus.bean.PointBean;
import com.jplus.bean.SquareBean;
import com.jplus.forms.Main;
import com.jplus.forms.MainFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 画板工具
 * @author hyberbin
 */
public class BaseGraphic extends Canvas {

    /** 图像 */
    private Graphics mainGraph;
    /** 下一个的图 */
    public final static int NEXT = 1;
    /** 主显示区的图 */
    public final static int MAIN = 2;
    /** 图像类型 */
    public int type;

    public BaseGraphic(int type) {
        this.type = type;
    }

    /**
     * 画图函数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (mainGraph == null) {
            mainGraph = this.getGraphics();
        }
        if (type == MAIN) {
            drawMain();
        } else if (type == NEXT) {
            drawNext();
        }
    }

    /**
     * 画一个黑的用来显示
     * @param square 方块对象
     */
    public void drawBlack(SquareBean square) {
        if (!square.isVirtual) {
            mainGraph.setColor(PointBean.c);
            for (PointBean p : square.pointList) {
                change(p);
            }
        }
    }

    /**
     * 画下一个方块
     * @param square
     */
    public void drawNext() {
        clear();
        SquareBean square = MainFrame.nextSquare;
        mainGraph.setColor(PointBean.c);
        for (PointBean p : square.pointList) {
            change(new PointBean(p.getX(), p.getY() -Main.sizeY/2+1));
        }
    }

    /**
     * 画一个白的用来清除原来的
     * @param square 方块对象
     */
    public void drawWhite(SquareBean square) {
        if (!square.isVirtual) {
            mainGraph.setColor(Color.WHITE);
            for (PointBean p : square.pointList) {
                change(p);
            }
        }
    }

    /**
     * 重新绘制画板
     */
    public void drawMain() {
        clear();
        for (int i = 0; i < Main.sizeX; i++) {
            Boolean[] bools = MainFrame.bools.get(i);
            for (int j = 0; j < Main.sizeY; j++) {
                if (bools[j]) {
                    MainFrame.painter.drawOne(i, j);
                }
            }
        }
    }

    /**
     * 数字与几何图形转化
     * @param p 单个块
     * @param center 中心块位置
     */
    private synchronized void change(PointBean p) {
        int y = (p.getX()) * 20;
        int x = (p.getY()) * 20;
        mainGraph.fillRect(x + 1, y + 1, 18, 18);
    }

    /** *
     * 清除画板
     */
    public synchronized void clear() {
        if (mainGraph == null) {
            return;
        }
        mainGraph.setColor(Color.WHITE);
        mainGraph.fillRect(0, 0, Main.sizeY * 20, Main.sizeX * 20);
    }

    /**
     * 画一个单个的块
     * @param x 横坐标
     * @param y 纵坐标
     */
    public synchronized void drawOne(int y, int x) {
        mainGraph.setColor(PointBean.c);
        x *= 20;
        y *= 20;
        mainGraph.fillRect(x + 1, y + 1, 18, 18);
    }
}
