package com.patientobserver;
import DataClasses.*;
import controller.SendMail;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class to simulate a database when a database is not connected (for demo purposes).
 */
public class DataSim implements DataHandler{
    private Integer idTrack = 100000000;
    private int dataEntry = 0;
    private Hashtable<Integer, UserDataTable> userTable;
    private Hashtable<String, Pair<String, Integer>> credTable;
    private Hashtable<Integer, ArrayList<Integer>> subsTable;
    private ArrayList<HealthProViewData> dataTable;
    private SendMail sendMail = new SendMail();

    private Pair<Integer, Integer> heartBeat = new Pair<Integer, Integer>(30, 120);
    private Pair<Integer, Integer> bpSys = new Pair<Integer, Integer>(30, 200);
    private Pair<Integer, Integer> bpDias = new Pair<Integer, Integer>(30, 200);
    private Pair<Integer, Integer> o2Sat = new Pair<Integer, Integer>(0, 100);
    private Pair<Integer, Integer> respRate = new Pair<Integer, Integer>(4, 50);

    /**
     * DataSim constructor setting up database simulator
     */
    public DataSim(){
        userTable = new Hashtable<Integer, UserDataTable>();
        credTable = new Hashtable<String, Pair<String, Integer>>();
        subsTable = new Hashtable<Integer, ArrayList<Integer>>();
        dataTable = new ArrayList<>();
        UserDataTable demoUser1 = new UserDataTable("John", "Big", "Dough", UserDataShort.UserType.HEALTH_PRO, "123 St, Kamloops, BC", "250-250-2550", "befikadukaleab@gmail.com");
        UserDataTable demoUser2 = new UserDataTable("May", "June", "August", UserDataShort.UserType.REGULAR_USR, "124 St, Kamloops, BC", "778-250-2550", "ymamatjan@tru.ca");
        UserDataTable demoUser3 = new UserDataTable("Yasin", "-", "Mamatjan", UserDataShort.UserType.REGULAR_USR, "124 St, Kamloops, BC", "778-250-2550", "befikadukaleab@gmail.com");
        userTable.put(assignID(), demoUser1);
        userTable.put(assignID(), demoUser2);
        userTable.put(assignID(), demoUser3);
        subsTable.put(100000001, new ArrayList<Integer>(Arrays.asList(100000001, 100000002)));
        subsTable.put(100000003, new ArrayList<Integer>(Arrays.asList(100000001, 100000002)));
        credTable.put("desertboy", new Pair<String, Integer>("desertboy", 100000003));
    }

    /**
     * Function to add user profile
     * @param user user data
     * @return successful or not
     */
    public boolean addUser(UserDataTable user){
        userTable.put(assignID(), user);
        return true;
    }

    /**
     * Function to add login credentials for a user.
     *
     * @param username login username
     * @param password login password
     * @param userId   their existing user id
     * @return successful or not
     */
    @Override
    public boolean addCred(String username, String password, Integer userId) {
        if(userTable.containsKey(userId)){
            credTable.put(username, new Pair<String, Integer>(password, userId));
            return true;
        }
        return false;
    }

    /**
     * Function to authenticate user (for login purposes)
     *
     * @param username login username
     * @param password login password
     * @return user data with authentication state
     */
    @Override
    public UserDataShort authenticateUser(String username, String password) {
        if(credTable.containsKey(username)){
            Pair pass = credTable.get(username);
            if(pass.x.equals(password)){
                UserDataShort userDataShort = new UserDataShort(userTable.get(pass.y));
                userDataShort.authorized = true;
                return userDataShort;
            }
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
        if(subsTable.containsKey(user)){
            ArrayList<Integer> subs = subsTable.get(user);
            if (!subs.contains(sub)) {
                subsTable.get(user).add(sub);
            }
        }
        else{
            subsTable.put(user, new ArrayList<Integer>(sub));
        }
        return true;
    }

    /**
     * Function to get the list of subscribed patient data for a user
     *
     * @param userID the user id
     * @return list of subscribed patients.
     */
    @Override
    public Pair<Integer, ArrayList<Integer>> getSubs(Integer userID) {
        if(subsTable.containsKey(userID)){
            return new Pair<Integer, ArrayList<Integer>>(userID, subsTable.get(userID));
        }
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
        if(subsTable.containsKey(userID)){
            ArrayList<Integer> subsList = subsTable.get(userID);
            ArrayList<UserDataShort> userDataList = new ArrayList<>();
            for(Integer x : subsList){
                UserDataTable userDataFull = userTable.get(x);
                userDataFull.userID = x;
                UserDataShort userData = new UserDataShort(userDataFull);
                userDataList.add(userData);
            }
            return new Pair<Integer, ArrayList<UserDataShort>>(userID, userDataList);
        }
        return null;
    }

    /**
     * Function to add check if the data handler is connected
     *
     * @return returns connection status
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    /**
     * function to assign unique IDs to existing users
     * @return the new ID
     */
    private Integer assignID(){
        idTrack++;
        return idTrack;
    }

    /**
     * Function to add data to the datatable
     * @param data patient data of type HealthProViewData
     */
    public void addData(HealthProViewData data){
        data.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        dataTable.add(data);
        dataEntry++;
        /*if(data.heartRate < heartBeat.x || data.heartRate > heartBeat.y){
            sendMail.alert(userTable.get(data.userId).email);
        }
        else if((Integer)data.bloodPressure.y < bpSys.x || (Integer)data.bloodPressure.y > bpSys.y){
            sendMail.alert(userTable.get(data.userId).email);
        }
        else if(data.o2Sat < o2Sat.x || data.o2Sat > o2Sat.y){
            sendMail.alert(userTable.get(data.userId).email);
        }
        else if(data.respiratoryRate < respRate.x || data.respiratoryRate > respRate.y){
            sendMail.alert(userTable.get(data.userId).email);
        }*/
        /* TODO: Implement thresholding for the rest*/
    }

    /**
     * Function to get vital statistics for a specific patient
     *
     * @param userID the user ID of teh patient
     * @return the statistics data of type HealthProStats
     */
    @Override
    public HealthProStats getStatData(Integer userID) {
        int limit = 0, loopCount = 0;
        HealthProStats stats = new HealthProStats();
        if(dataTable.size() > 20){
            limit = dataTable.size() - 20;
        }
        for(int i = dataTable.size() - 1; i > limit; i--){
            if(dataTable.get(i).userId == userID){
                if(dataTable.get(i).heartRate > stats.heartRate[1]){
                    stats.heartRate[1] = dataTable.get(i).heartRate;
                }
                else if(dataTable.get(i).heartRate < stats.heartRate[0]){
                    stats.heartRate[0] = dataTable.get(i).heartRate;
                }
                if((int)dataTable.get(i).bloodPressure.x > stats.bloodSys[1]){
                    stats.bloodSys[1] = (int)dataTable.get(i).bloodPressure.x;
                }
                else if((int)dataTable.get(i).bloodPressure.x > stats.bloodSys[0] || stats.bloodSys[0] == 999){
                    stats.bloodSys[0] = (int)dataTable.get(i).bloodPressure.x;
                }
                if((int)dataTable.get(i).bloodPressure.y > stats.bloodDias[1]){
                    stats.bloodDias[1] = (int)dataTable.get(i).bloodPressure.y;
                }
                else if((int)dataTable.get(i).bloodPressure.y > stats.bloodDias[0] || stats.bloodDias[0] == 999){
                    stats.bloodDias[0] = (int)dataTable.get(i).bloodPressure.y;
                }
                if(dataTable.get(i).o2Sat > stats.O2Sat[1]){
                    stats.O2Sat[1] = dataTable.get(i).o2Sat;
                }
                else if(dataTable.get(i).o2Sat < stats.O2Sat[0]){
                    stats.O2Sat[0] = dataTable.get(i).o2Sat;
                }
                if(dataTable.get(i).respiratoryRate > stats.respRate[1]){
                    stats.respRate[1] = dataTable.get(i).respiratoryRate;
                }
                else if(dataTable.get(i).respiratoryRate < stats.respRate[0]){
                    stats.respRate[0] = dataTable.get(i).respiratoryRate;
                }

                stats.heartRate[2] += dataTable.get(i).heartRate;
                stats.bloodSys[2] += (int)dataTable.get(i).bloodPressure.x;
                stats.bloodDias[2] += (int)dataTable.get(i).bloodPressure.y;
                stats.O2Sat[2] += dataTable.get(i).o2Sat;
                stats.respRate[2] += dataTable.get(i).respiratoryRate;

                loopCount++;
            }
        }

        stats.heartRate[2] /= loopCount;
        stats.bloodSys[2] /= loopCount;
        stats.bloodDias[2] /= loopCount;
        stats.O2Sat[2] /= loopCount;
        stats.respRate[2] /= loopCount;

        return stats;
    }
}
