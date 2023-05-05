package com.patientobserver;
import DataClasses.*;
import controller.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * View class extending the AppView class
 */
public class HealthProView extends AppView implements OAppView{
    private ViewController controller;
    private AppView self;

    Pair<Integer, Integer> heartBeat = new Pair<Integer, Integer>(30, 90);
    Pair<Integer, Integer> bpSys = new Pair<Integer, Integer>(30, 200);
    Pair<Integer, Integer> bpDias = new Pair<Integer, Integer>(30, 200);
    Pair<Integer, Integer> o2Sat = new Pair<Integer, Integer>(0, 100);
    Pair<Integer, Integer> respRate = new Pair<Integer, Integer>(4, 50);

    /**
     * Constructor to launch a view without the setup given in the next constructor
     * @param title the title of the jframe
     */
    public HealthProView(String title){
        super(title);
        setupView();
    }

    /**
     * COnstructor setting up view and class
     * @param title the title of the jFrame
     * @param controller the controller connected to this view
     */
    public HealthProView(String title, ViewController controller){
        super(title);
        setupView();
        this.controller = controller;
        controller.addView((OAppView)frame);
        self = this;
        EventData data = new EventData();
        data.eventType = EventData.EventType.GET_SUBSCRIBED_LIST;
        userId = 100000001;
        data.userID = userId;
        controller.addEvent(data);
    }

    /**
     * Template step to setup buttons in the view.
     */
    @Override
    protected void setupViewButtons() {
        try{
            button4 = new JButton();
            configureButton(button4, "Adjust.png", "Button to adjust notification thresholds for the patient");
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openThresholdDialogue();
                }
            });
            buttonsPanel.add(button4,0);

            button2 = new JButton();
            configureButton(button2, "PatientList.png", "Button to show subscribed patients list");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSubsDialogue();
                }
            });
            buttonsPanel.add(button2,1);

            button3 = new JButton();
            configureButton(button3, "AddRemove.png", "Button to add/remove patients from your subscription list");
            //buttonsPanel.add(button3,2); TODO: Not implemented yet...

            button1 = new JButton();
            configureButton(button1, "Logout.png", "Button to Logout");
            buttonsPanel.add(button1,2);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EventData event = new EventData(EventData.EventType.LOG_OUT);
                    controller.addEvent(event);
                    String[] str = {"null"};
                    controller = null;
                    frame.setVisible(false); //you can't see me!
                    frame.dispose(); //Destroy the JFrame object
                    Main.main(str);
                }
            });

        }
       catch (Exception e){
            System.out.println(e);
       }
    }

    /**
     * Template step method to setup vital signs panel content
     */
    @Override
    public void setupViewStats() {
        currPatientLabel.setText("Viewing Patient ");
        setupPanelContent(s1TextPanel, s1Label, "BPM", s1IconLabel, "HeartBeatIcon.png");
        setupPanelContent(s2TextPanel, s2Label, "BP", s2IconLabel, "BPIcon.png");
        setupPanelContent(s3TextPanel, s3Label, "SpO2", s3IconLabel, "O2Sat.png");
        setupPanelContent(s4TextPanel, s4Label, "RR", s4IconLabel, "RespirationRate.png");
    }

    /**
     * Function to setup the statistics tab
     */
    @Override
    protected void setupViewData() {
        setupDataPanel(sandbox1Label, comp11Label, comp12Label, comp13Label, "Heart Rate", "Max", "Min", "Avg");
        setupDataPanel(sandbox2Label, comp21Label, comp22Label, comp23Label, "Blood Pressure", "Max", "Min", "Avg");
        setupDataPanel(sandbox3Label, comp31Label, comp32Label, comp33Label, "O2 Saturation", "Max", "Min", "Avg");
        setupDataPanel(sandbox4Label, comp41Label, comp42Label, comp43Label, "Respiration Rate", "Max", "Min", "Avg");
    }

    /**
     * Function to setup the specific area for a vital sign display
     * @param textPane the text pane in the area
     * @param label the label associated
     * @param labelName the name of the label
     * @param iconLabel the icon label associate with the area
     * @param iconString the string name of the icon to be loaded from disk
     */
    protected void setupPanelContent(JTextPane textPane, JLabel label, String labelName, JLabel iconLabel, String iconString){
        textPane.setAlignmentX(50.0f);
        textPane.setAlignmentY(TextArea.CENTER_ALIGNMENT);
        textPane.setForeground(new Color(96, 198, 137));
        textPane.setBackground(new Color(21, 25, 28));
        textPane.setEditable(false);
        textPane.setText("---");
        label.setText(labelName);
        label.setForeground(new Color(96, 198, 137));
        try{
            ImageIcon img = new ImageIcon("img/" + iconString);
            iconLabel.setIcon(img);
            iconLabel.setText("");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Function to update the vital sign display panel and all its contents
     */
    @Override
    public void updateView() {
        HealthProViewData data = controller.getHPData();
        rightAlignSetText(s1TextPanel, s1Label, String.valueOf(data.heartRate), heartBeat);
        rightAlignSetText(s2TextPanel, s2Label, String.valueOf(data.bloodPressure.x) + "/" + String.valueOf(data.bloodPressure.y), bpSys);
        rightAlignSetText(s3TextPanel, s3Label, String.valueOf(String.valueOf(data.o2Sat)), o2Sat);
        rightAlignSetText(s4TextPanel, s4Label, String.valueOf(String.valueOf(data.respiratoryRate)), respRate);

        requestStatsUpdate();
    }

    /**
     * Function to right align text in the patient vital text panes
     * @param textPanel the specific text pane
     * @param label the J label associated with that vital
     * @param str the string to right align
     * @param threshold the threshold beyond which vital data turns red
     */
    private void rightAlignSetText(JTextPane textPanel, JLabel label, String str, Pair<Integer, Integer> threshold){
        try{
            try{
                if(Integer.valueOf(str) > threshold.y || Integer.valueOf(str) < threshold.x) {
                    textPanel.setForeground(new Color(194, 24, 88));
                    label.setForeground(new Color(194, 24, 88));
                }
                else{
                    textPanel.setForeground(new Color(96, 198, 137));
                    label.setForeground(new Color(96, 198, 137));
                }
            }
            catch(Exception e){

            }
            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
            document.insertString(document.getLength(), str, style);
            textPanel.setDocument(document);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Function to configure menu buttons
     * @param button the specific button to setup
     * @param imgName the name of the icon to be loaded for that image
     * @param toolTipString the string to be displayed while hovering using a mouse
     */
    private void configureButton(JButton button, String imgName, String toolTipString){
        button.setBackground(new Color(0, 0, 0));
        ImageIcon img = new ImageIcon("img/" + imgName);
        button.setIcon(img);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setToolTipText(toolTipString);
    }

    /**
     * Function to get the id of the data being actively viewed
     * @return id of user being viewed
     */
    public Integer getViewingID(){
        return viewingID;
    }

    /**
     * Function to update the subscribed patient list
     */
    @Override
    public void updateSubs(ArrayList<UserDataShort> subs) {
        this.subsList = subs;
        setViewing(subs.get(0));
    }

    /**
     * Function to get the view users ID
     *
     * @return user id
     */
    @Override
    public Integer getUserID() {
        return userId;
    }

    /**
     * Update vital statistics for the patient being viewed
     *
     * @param stats calculated statistics
     */
    @Override
    public void updateStats(HealthProStats stats) {
        comp11TextPane.setText(String.valueOf(stats.heartRate[1]));
        comp12TextPane.setText(String.valueOf(stats.heartRate[0]));
        comp13TextPane.setText(String.valueOf(stats.heartRate[2]));
        comp21TextPane.setText(String.valueOf(stats.bloodSys[1]));
        comp22TextPane.setText(String.valueOf(stats.bloodSys[0]));
        comp23TextPane.setText(String.valueOf(stats.bloodSys[2]));
        comp31TextPane.setText(String.valueOf(stats.O2Sat[1]));
        comp32TextPane.setText(String.valueOf(stats.O2Sat[0]));
        comp33TextPane.setText(String.valueOf(stats.O2Sat[2]));
        comp41TextPane.setText(String.valueOf(stats.respRate[1]));
        comp42TextPane.setText(String.valueOf(stats.respRate[0]));
        comp43TextPane.setText(String.valueOf(stats.respRate[2]));
    }

    /**
     * Function to setup the threshold view dialogue
     */
    public void openThresholdDialogue(){
        JDialog dialogue = new JDialog(frame , "Vitals Threshold Dialog", true);
        dialogue.setSize(500, 200);
        JPanel panel = new JPanel(new GridLayout(5, 3, 10, 0));
        panel.setBackground(new Color(0, 0, 0));

        JTextPane heartHighPane = new JTextPane();
        heartHighPane.setBackground(new Color(21, 25, 28));
        heartHighPane.setText(heartBeat.y.toString());
        heartHighPane.setForeground(new Color(194, 24, 88));

        JTextPane heartLowPane = new JTextPane();
        heartLowPane.setBackground(new Color(21, 25, 28));
        heartLowPane.setText(heartBeat.x.toString());
        heartLowPane.setForeground(new Color(194, 24, 88));

        JTextPane bpSysHigh = new JTextPane();
        bpSysHigh.setBackground(new Color(21, 25, 28));
        bpSysHigh.setText(bpSys.y.toString());
        bpSysHigh.setForeground(new Color(194, 24, 88));

        JTextPane bpSysLow = new JTextPane();
        bpSysLow.setBackground(new Color(21, 25, 28));
        bpSysLow.setText(bpSys.x.toString());
        bpSysLow.setForeground(new Color(194, 24, 88));

        JTextPane bpDiasHigh = new JTextPane();
        bpDiasHigh.setBackground(new Color(21, 25, 28));
        bpDiasHigh.setText(bpDias.y.toString());
        bpDiasHigh.setForeground(new Color(194, 24, 88));

        JTextPane bpDiasLow = new JTextPane();
        bpDiasLow.setBackground(new Color(21, 25, 28));
        bpDiasLow.setText(bpDias.x.toString());
        bpDiasLow.setForeground(new Color(194, 24, 88));

        JTextPane oSatHigh = new JTextPane();
        oSatHigh.setBackground(new Color(21, 25, 28));
        oSatHigh.setText(o2Sat.y.toString());
        oSatHigh.setForeground(new Color(194, 24, 88));

        JTextPane oSatLow = new JTextPane();
        oSatLow.setBackground(new Color(21, 25, 28));
        oSatLow.setText(o2Sat.x.toString());
        oSatLow.setForeground(new Color(194, 24, 88));

        JTextPane respHigh = new JTextPane();
        respHigh.setBackground(new Color(21, 25, 28));
        respHigh.setText(respRate.y.toString());
        respHigh.setForeground(new Color(194, 24, 88));

        JTextPane respLow = new JTextPane();
        respLow.setBackground(new Color(21, 25, 28));
        respLow.setText(respRate.x.toString());
        respLow.setForeground(new Color(194, 24, 88));

        panel.add(new JLabel("Heart Beat"));
        panel.add(heartHighPane);
        panel.add(heartLowPane);
        panel.add(new JLabel("Blood Pressure Sys"));
        panel.add(bpSysHigh);
        panel.add(bpSysLow);
        panel.add(new JLabel("Blood Pressure Dias"));
        panel.add(bpDiasHigh);
        panel.add(bpDiasLow);
        panel.add(new JLabel("O2 Saturation"));
        panel.add(oSatHigh);
        panel.add(oSatLow);
        panel.add(new JLabel("Respiration Rate"));
        panel.add(respHigh);
        panel.add(respLow);
        JPanel overpanel = new JPanel();
        overpanel.setBackground(new Color(0, 0, 0));
        overpanel.add(panel);
        dialogue.setContentPane(overpanel);

        dialogue.add(new JLabel());
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heartBeat.y = Integer.valueOf(heartHighPane.getText());
                heartBeat.x = Integer.valueOf(heartLowPane.getText());
                bpSys.y = Integer.valueOf(bpSysHigh.getText());
                bpSys.x = Integer.valueOf(bpSysLow.getText());
                bpDias.y = Integer.valueOf(bpDiasHigh.getText());
                bpDias.x = Integer.valueOf(bpDiasLow.getText());
                o2Sat.y = Integer.valueOf(oSatHigh.getText());
                o2Sat.x = Integer.valueOf(oSatLow.getText());
                respRate.y = Integer.valueOf(respHigh.getText());
                respRate.x = Integer.valueOf(respLow.getText());
                dialogue.setVisible(false);
            }
        });
        okBtn.setBackground(new Color(21, 25, 28));
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelBtn.setBackground(new Color(21, 25, 28));
        dialogue.add(okBtn);
        dialogue.add(cancelBtn);
        dialogue.setVisible(true);
    }

    /**
     * Function to open a dialogue where users choose the patient to monitor from their subscriptions list
     */
    public void openSubsDialogue(){
        JDialog dialogue = new JDialog(frame , "Subscriptions List Dialogue", true);
        dialogue.setSize(450, 200);
        dialogue.setBackground(new Color(0, 0, 0));
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 0));
        panel.setBackground(new Color(0, 0, 0));
        panel.setForeground(new Color(255, 255, 255));

        JLabel dialogueLabel = new JLabel("Click on the patient you want to observe and click ok to save change");
        dialogueLabel.setForeground(new Color(255, 255, 255));
        dialogueLabel.setBackground(new Color(0, 0, 0));
        JLabel listLabel = new JLabel("ID              First Name            Last Name");
        listLabel.setForeground(new Color(255, 255, 255));
        listLabel.setBackground(new Color(0, 0, 0));

        JButton okBtn = new JButton("OK");
        okBtn.setEnabled(false);
        okBtn.setPreferredSize(new Dimension(10, 10));
        okBtn.setMaximumSize(new Dimension(10, 10));
        okBtn.setBackground(new Color(60, 60, 60));


        JList subsListUI = new JList();
        subsListUI.setBackground(new Color(0, 0, 0));
        subsListUI.setForeground(new Color(255, 255, 255));
        subsListUI.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return subsList.size();
            }

            @Override
            public Object getElementAt(int i) {
                UserDataShort userDataShort = subsList.get(i);
                String listString = String.valueOf(userDataShort.userID) + "    " + userDataShort.fName + "    " + userDataShort.lName;
                return listString;
            }
        });
        subsListUI.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                okBtn.setEnabled(true);
            }
        });

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setViewing(subsList.get(subsListUI.getSelectedIndex()));
                dialogue.setVisible(false);
            }
        });

        JPanel labelPanel = new JPanel(new GridLayout(2, 0));
        labelPanel.setBackground(new Color(0, 0, 0));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 0));
        buttonPanel.setBackground(new Color(0, 0, 0));

        labelPanel.add(dialogueLabel, 0);
        labelPanel.add(listLabel, 1);
        panel.add(labelPanel, 0);
        panel.add(subsListUI, 1);
        buttonPanel.add(okBtn);
        panel.add(buttonPanel, 2);

        dialogue.add(panel);
        dialogue.setVisible(true);
    }

    /**
     * Function to set the current patient being monitored
     * @param data the patient's data
     */
    public void setViewing(UserDataShort data){
        this.viewingID = data.userID;
        detailsLabel.setText("     Name: " + data.fName + " " + data.lName + "         ID: " + String.valueOf(viewingID) + "\n");
    }

    /**
     * FUnction to setup statistics panel
     * @param l1 title panel
     * @param l2 data 1 panel
     * @param l3 data 2 panel
     * @param l4 data 3 panel
     * @param title string to set title
     * @param data1Str string to set data 1 panel
     * @param data2Str string to set data 2 panel
     * @param data3Str string to set data 3 panel
     */
    public void setupDataPanel(JLabel l1, JLabel l2, JLabel l3, JLabel l4, String title, String data1Str, String data2Str, String data3Str){
        l1.setText(title);
        l2.setText(data1Str);
        l3.setText(data2Str);
        l4.setText(data3Str);
    }

    /**
     * This function makes a request to the controller to the update the stats of the view.
     */
    public void requestStatsUpdate(){
        EventData e = new EventData();
        e.eventType = EventData.EventType.GET_STATS_DATA;
        e.userDataShort.userID = this.userId;
        e.userID = viewingID;
        controller.addEvent(e);
    }
}
