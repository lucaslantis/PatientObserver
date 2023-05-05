package com.patientobserver;

import DataClasses.HealthProStats;
import DataClasses.UserDataShort;

import javax.swing.*;
import java.util.ArrayList;

/**
 * View class foe the Patient View TODO: NOT IMPLEMENTED
 */
public class PatientView extends AppView implements OAppView{
    /**
     * Constructor to setup view
     *
     * TODO: NOT IMPLEMENTED
     * @param title the title of the jframe
     */
    public PatientView(String title){
        super(title);
        setupView();
    }

    /**
     * Function to setup view buttons
     */
    @Override
    protected void setupViewButtons() {

    }
    /**
     * Function to setup the live view tab
     */
    @Override
    protected void setupViewStats() {

    }

    /**
     * Function to setup the statistics tab
     */
    @Override
    protected void setupViewData() {

    }

    /**
     * Function to setup the specific area for a vital sign display
     * @param textPane the text pane in the area
     * @param label the label associated
     * @param labelName the name of the label
     * @param iconLabel the icon label associate with the area
     * @param iconString the string name of the icon to be loaded from disk
     */
    @Override
    protected void setupPanelContent(JTextPane textPane, JLabel label, String labelName, JLabel iconLabel, String iconString) {

    }

    /**
     * Abstract interface function to update implementing class.
     */
    @Override
    public void updateView() {

    }

    /**
     * Function to get the id of the data being actively viewed
     *
     * @return id of user being viewed
     */
    @Override
    public Integer getViewingID() {
        return null;
    }

    /**
     * Function to update the subscribed patient list
     */
    @Override
    public void updateSubs(ArrayList<UserDataShort> subs) {

    }

    /**
     * Function to get the view users ID
     *
     * @return user id
     */
    @Override
    public Integer getUserID() {
        return null;
    }

    /**
     * Update vital statistics for the patient being viewed
     *
     * @param stats calculated statistics
     */
    @Override
    public void updateStats(HealthProStats stats) {

    }
}
