/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.bean;

import com.jplus.forms.Main;
import java.util.LinkedList;

/**
 *
 * @author hyberbin
 */
public class SquarePool {

    /** 当前方块总数 */
    public static final int size = 10;

    /**
     * 获得一种方块
     * @param type 类型
     * @return
     */
    public static SquareBean getSquare(int type) {
        SquareBean sq = new SquareBean(type);
        sq.pointList = new LinkedList<PointBean>();
        int x =Main.sizeY/2;
        switch (type) {
            case 0://右L                
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(2, x));
                sq.pointList.add(new PointBean(0, x + 1));
                return sq;
            case 1://左L
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(2, x));
                sq.pointList.add(new PointBean(0, x - 1));
                return sq;
            case 2://田字
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(0, x + 1));
                sq.pointList.add(new PointBean(1, x + 1));
                return sq;
            case 3://一字
                sq.pointList.add(new PointBean(0, x - 1));
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(0, x + 1));
                sq.pointList.add(new PointBean(0, x + 2));
                return sq;
            case 4://竖一
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(2, x));
                sq.pointList.add(new PointBean(3, x));
                return sq;
            case 5://右N型
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(1, x + 1));
                sq.pointList.add(new PointBean(2, x + 1));
                return sq;
            case 6://左N型
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(1, x - 1));
                sq.pointList.add(new PointBean(2, x - 1));
                return sq;
            case 7://右Z型
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(0, x + 1));
                sq.pointList.add(new PointBean(1, x + 1));
                sq.pointList.add(new PointBean(1, x + 2));
                return sq;
            case 8://左Z型
                sq.pointList.add(new PointBean(0, x + 1));
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(1, x - 1));
                return sq;
            case 9://山型
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(0, x + 1));
                sq.pointList.add(new PointBean(0, x + 2));
                sq.pointList.add(new PointBean(1, x + 1));
                return sq;
            case 10://十型
                sq.pointList.add(new PointBean(1, x));
                sq.pointList.add(new PointBean(0, x));
                sq.pointList.add(new PointBean(2, x));
                sq.pointList.add(new PointBean(1, x + 1));
                sq.pointList.add(new PointBean(1, x-1));
                return sq;
        }
        return null;
    }

    /**
     * 通过随机数获得相应方块
     * @return
     */
    public static SquareBean getOne() {
        return getSquare((int) (Math.random() * size));
    }
}
