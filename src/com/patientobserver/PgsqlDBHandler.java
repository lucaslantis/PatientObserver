package com.patientobserver;

import java.sql.*;
import java.util.ArrayList;

import DataClasses.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to connect and manage the PGSQL database
 */
public class PgsqlDBHandler implements DataHandler{
    private Connection databaseConnection = null;
    private boolean connectedToDatabase;
    private Logger LOGGER = LogManager.getLogger(PgsqlDBHandler.class);
    /**
     * PGSQL handler constructor
     */
    public PgsqlDBHandler(){
        connectToDatabase();
    }
// push try
    /**
     * Function connects to the database when called
     * @return boolean value for connected or not connected
     */
    public boolean connectToDatabase(){
        try {
            // Connection to the PatientObserver Database
            //String dbURL = "jdbc:postgresql://localhost/PatientObserverDB";
            String dbURL = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String pass = "123";

            databaseConnection = DriverManager.getConnection(dbURL, user, pass);
            if (databaseConnection != null) {
                LOGGER.info("This is an INFO level log message!");
                LOGGER.error("This is an INFO level log message!");
                System.out.println("here");
                connectedToDatabase = true;
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Function to add a user to the database
     * @param user user data
     * @return successful or not
     */
    public boolean addUser(UserDataTable user){
        try{
            String query = "INSERT INTO private.app_users (first_name, middle_name, last_name, phone_no, address, user_type) " +
                    "VALUES ('" + user.fName + "', '" + user.mName + "', '" + user.lName + "', '" + user.phoneNo + "', '" + user.address + "', '" + user.userType.toString() + "');" ;
            Statement st = databaseConnection.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Function to add user credentials to database
     * @param username username of existing user
     * @param password password for account
     * @param userId the user id of the existing user
     * @return successful or not
     */
    @Override
    public boolean addCred(String username, String password, Integer userId) {
        return false;
        //TODO: not implemented
    }

    /**
     * Function to authenticate the identity of a user (login purposes)
     * @param username the username of the user
     * @param password the password associated with this user name
     * @return the user data belonging to this user (username)
     */
    public UserDataShort authenticateUser(String username, String password) {
        int usrId = 0;
        String passwordChk = "";
        UserDataShort usrData;
        try{
            String query = "SELECT user_id, password FROM private.access_credentials WHERE user_name = '" + username + "';";
            PreparedStatement pst = databaseConnection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            usrId = rs.getInt(1);
            passwordChk = rs.getString(2);
            if(password.equals(passwordChk)){
                query = "SELECT * FROM private.app_users WHERE user_id = '" + usrId + "';";
                pst = databaseConnection.prepareStatement(query);
                rs = pst.executeQuery();
                rs.next();
                usrData = new UserDataShort(rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("user_type").equals("HealthPro") ? UserDataShort.UserType.HEALTH_PRO : UserDataShort.UserType.REGULAR_USR, true);
                return usrData;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return new UserDataShort(false);
    }

    /**
     * Function to assign subscription to users.
     *
     * @param user user id
     * @param sub  used id of user to be subscribed to
     * @return
     */
    @Override
    public boolean addSubs(Integer user, Integer sub) {
        return false;
    }

    /**
     * Function to get the list of subscribed patient data for a user
     *
     * @param userID the user id
     * @return list of subscribed patients.
     */
    @Override
    public Pair<Integer, ArrayList<Integer>> getSubs(Integer userID) {
        return null;
    }

    /**
     * Function to get the list of full subscribed patient data for a user
     *
     * @param userID the user id
     * @return list of subscribed patients full
     */
    @Override
    public Pair<Integer, ArrayList<UserDataShort>> getSubsData(Integer userID) {
        return null;
    }

    public boolean isConnected(){
        return connectedToDatabase;
    }

    /**
     * Function to add data to the datatable
     *
     * @param data patient data of type HealthProViewData
     */
    @Override
    public void addData(HealthProViewData data) {

    }

    /**
     * Function to get vital statistics for a specific patient
     *
     * @param userID the user ID of teh patient
     * @return the statistics data of type HealthProStats
     */
    @Override
    public HealthProStats getStatData(Integer userID) {
        return null;
    }
}
