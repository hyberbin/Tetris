/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.bean;

import java.awt.Color;

/**
 * 一个点（格子）的模型
 * @author hyberbin
 */
public class PointBean {

    private int x;
    private int y;
    public static Color c = new Color(15,161,211);

    public PointBean(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
