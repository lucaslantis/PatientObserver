package com.patientobserver;
import javax.swing.*;

import java.awt.*;

/**
 * Main runner class that houses the main function (Runner)
 */
public class Main {
    /**
     * Main runner function
     * @param args Not Used
     */
    public static void main(String[] args) {
        System.out.println("Application has begun >>>");
        JFrame frame1 = new Login();
        frame1.setPreferredSize(new Dimension(600, 400));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame1.getWidth()) / 2;
        int y = (screenSize.height - frame1.getHeight()) / 2;
        frame1.setLocation(x - 300, y - 300);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }
}
