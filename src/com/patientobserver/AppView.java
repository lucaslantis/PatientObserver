package com.patientobserver;

import DataClasses.UserDataShort;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Abstract template class that other views will inherit from.
 *
 * Contains all the basic structure variable for the java swing setup.
 *
 */
public abstract class AppView extends JFrame{
    protected JPanel panel1;
    protected JPanel buttonsPanel;
    protected JPanel statsPanel;
    protected JPanel s1Panel;
    protected JPanel s2Panel;
    protected JPanel s3Panel;
    protected JPanel s4Panel;
    protected JTextPane s1TextPanel;
    protected JLabel s1Label;
    protected JTextPane s2TextPanel;
    protected JTextPane s3TextPanel;
    protected JTextPane s4TextPanel;
    protected JLabel s2Label;
    protected JLabel s3Label;
    protected JLabel s4Label;
    protected JLabel s1IconLabel;
    protected JLabel s2IconLabel;
    protected JLabel s3IconLabel;
    protected JLabel s4IconLabel;
    protected JTabbedPane tabbedPane1;
    protected JTextPane currPatientTextPane;
    protected JLabel currPatientLabel;
    protected JLabel detailsLabel;
    protected JTextPane comp11TextPane;
    protected JLabel sandbox1Label;
    protected JLabel comp11Label;
    protected JLabel comp12Label;
    protected JLabel comp13Label;
    protected JTextPane comp12TextPane;
    protected JTextPane comp13TextPane;
    protected JLabel sandbox2Label;
    protected JLabel sandbox3Label;
    protected JLabel sandbox4Label;
    protected JLabel comp21Label;
    protected JLabel comp22Label;
    protected JLabel comp23Label;
    protected JLabel comp31Label;
    protected JLabel comp32Label;
    protected JLabel comp33Label;
    protected JLabel comp41Label;
    protected JLabel comp42Label;
    protected JLabel comp43Label;
    protected JTextPane comp21TextPane;
    protected JTextPane comp22TextPane;
    protected JTextPane comp23TextPane;
    protected JTextPane comp31TextPane;
    protected JTextPane comp32TextPane;
    protected JTextPane comp33TextPane;
    protected JTextPane comp41TextPane;
    protected JTextPane comp42TextPane;
    protected JTextPane comp43TextPane;
    protected JButton button1;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4 = new JButton();
    protected JButton button5 = new JButton();
    protected JFrame frame;

    protected Integer viewingID;
    protected Integer userId;
    protected ArrayList<UserDataShort> subsList;

    /**
     * Constructor to initialize a view (Unused because AppView is an abstract class
     * @param title title of the j frame
     */
    public AppView(String title){
        super(title);
    }

    /**
     * The template method that contains the algorithm (recipe) for setting up user views
     */
    public void setupView(){
        setupPanels();
        setupViewStats();
        setupViewData();
        setupViewButtons();
    }

    /**
     * Function to setup panels when launching views (UI Setup)
     */
    protected void setupPanels(){
        try{
            Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
            insets.top = -1;
            insets.bottom = -1;
            insets.left = -1;
            insets.right = -1;

            tabbedPane1.setUI(new BasicTabbedPaneUI()
            {
                @Override
                protected void installDefaults()
                {
                    super.installDefaults();

                    highlight       = new Color(21, 25, 28);
                    lightHighlight  = new Color(21, 25, 28);

                    shadow          = new Color(21, 25, 28);
                    darkShadow      = new Color(21, 25, 28);
                }
            });
            tabbedPane1.setForegroundAt(0, Color.BLACK);
            tabbedPane1.setForegroundAt(1, Color.WHITE);

            buttonsPanel.setLayout(new GridLayout(4,1));
            panel1.setPreferredSize(new Dimension(840, 680));
            frame = this;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x - 400, y - 400);

            super.setContentPane(panel1);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Function to setup view buttons
     */
    abstract protected void setupViewButtons();

    /**
     * Function to setup the live view tab
     */
    abstract protected void setupViewStats();

    /**
     * Function to setup the statistics tab
     */
    abstract protected void setupViewData();

    /**
     * Function to setup the specific area for a vital sign display
     * @param textPane the text pane in the area
     * @param label the label associated
     * @param labelName the name of the label
     * @param iconLabel the icon label associate with the area
     * @param iconString the string name of the icon to be loaded from disk
     */
    abstract protected void setupPanelContent(JTextPane textPane, JLabel label, String labelName, JLabel iconLabel, String iconString);

}
