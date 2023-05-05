package com.patientobserver;

import DataClasses.HealthProStats;
import DataClasses.UserDataShort;

import java.util.ArrayList;

/**
 * Observer Interface to be implemented by AppView Subclasses
 */
public interface OAppView {
    /**
     * Abstract interface function to update implementing class.
     */
    public void updateView();
    /**
     * Function to get the id of the data being actively viewed
     * @return id of user being viewed
     */
    public Integer getViewingID();

    /**
     * Function to update the subscribed patient list
     */
    public void updateSubs(ArrayList<UserDataShort> subs);

    /**
     * Function to get the view users ID
     * @return user id
     */
    public Integer getUserID();

    /**
     * Update vital statistics for the patient being viewed
     * @param stats calculated statistics
     */
    public void updateStats(HealthProStats stats);
}
