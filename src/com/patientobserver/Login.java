package com.patientobserver;

import DataClasses.UserDataShort;
import controller.ControllerMain;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle authentication and authorization
 */
public class Login extends JFrame{
    private JPanel panel1;
    private JTextField usernamef;
    private JPasswordField passwordField1;
    private JButton loginbtn;
    private JButton resetButton;
    private JPanel iconPanel;
    private JLabel iconLabel;
    private JButton loginAsDoctorButton;
    private JButton loginAsUserButton;
    protected JFrame frame;

    /**
     * Constructor implementing all the click listeners for the buttons on the login page
     */
    public Login() {
        ImageIcon img = new ImageIcon("img/eye.png");
        iconLabel.setIcon(img);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        loginbtn.setBorder(emptyBorder);
        resetButton.setBorder(emptyBorder);
        usernamef.setBorder(emptyBorder);
        passwordField1.setBorder(emptyBorder);
        DataHandler databaseHandle = new DataSim();
        setContentPane(panel1);
        frame=this;

        loginbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDataShort logininstance =  databaseHandle.authenticateUser(usernamef.getText(),String.valueOf(passwordField1.getPassword()));
                if (logininstance.getAuthorization()==true) {
                   UserDataShort.UserType a =logininstance.getUserType();
                   switch (logininstance.getUserType()) {
                       case  HEALTH_PRO:
                           launchHealthProView();
                           break;
                       case REGULAR_USR:

                           break;
                   }
               }else{
                   JOptionPane.showMessageDialog(frame, "Wrong password! ");

            }}
        });

        loginAsDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchHealthProView();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernamef.setText("");
                passwordField1.setText("");
            }
        });
    }

    /**
     * Function to launch the health pro view of the application
     */
    public void launchHealthProView(){
        setVisible(false);
        ControllerMain controllerMain = ControllerMain.getInstance();
        JFrame frame1 = new HealthProView("Patient Observer App", controllerMain.getViewController());
        frame1.setVisible(true);
    }
}
