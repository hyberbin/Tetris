/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.forms;

import com.jplus.action.AIStrategy;

/**
 *
 * @author hyberbin
 */
public class Main {

    public static int sizeX = 30;
    public static int sizeY = 60;
    //public static int sizeX = 30;
    //public static int sizeY = 15;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AIStrategy.sleep=5;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);  
            }
        });
    }
}
