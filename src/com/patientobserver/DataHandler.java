package com.patientobserver;

import DataClasses.*;

import java.util.ArrayList;

/**
 * Interface for the data handlers to implement
 */
public interface DataHandler {
    /**
     * Function to add user to the data handler
     * @param user user data
     * @return
     */
    public boolean addUser(UserDataTable user);

    /**
     * Function to add login credentials for a user.
     * @param username login username
     * @param password login password
     * @param userId their existing user id
     * @return successful or not
     */
    public boolean addCred(String username, String password, Integer userId);

    /**
     * Function to authenticate user (for login purposes)
     * @param username login username
     * @param password login password
     * @return user data with authentication state
     */
    public UserDataShort authenticateUser(String username, String password);

    /**
     * Function to assign subscription to users.
     * @param user user id
     * @param sub used id of user to be subscribed to
     * @return
     */
    public boolean addSubs(Integer user, Integer sub);

    /**
     * Function to get the list user IDs of subscribed patient data for a user
     * @param userID the user id
     * @return list of subscribed patients.
     */
    public Pair<Integer, ArrayList<Integer>> getSubs(Integer userID);

    /**
     * Function to get the list of full subscribed patient data for a user
     * @param userID the user id
     * @return list of subscribed patients full
     */
    public Pair<Integer, ArrayList<UserDataShort>> getSubsData(Integer userID);

    /**
     * Function to add check if the data handler is connected
     * @return returns connection status
     */
    public boolean isConnected();

    /**
     * Function to add data to the datatable
     * @param data patient data of type HealthProViewData
     */
    public void addData(HealthProViewData data);

    /**
     * Function to get vital statistics for a specific patient
     * @param userID the user ID of teh patient
     * @return the statistics data of type HealthProStats
     */
    public HealthProStats getStatData(Integer userID);

}
