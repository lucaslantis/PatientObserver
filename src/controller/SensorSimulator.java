package controller;

import DataClasses.HealthProViewData;
import DataClasses.Pair;
import DataClasses.UserDataShort;

/**
 * Class running a sensor simulator thread (Basestation simulators)
 */
public class SensorSimulator {
    private SensorHandler sensorHandler;
    private boolean simulate = true;
    private Integer userID;
    private int currentCount = 0;
    int[] heartbeat = {80, 81, 80, 80, 79, 79, 80, 80, 81, 82, 81, 83, 87, 90, 96, 102, 109, 110, 120, 122, 122, 123, 122, 125, 122, 113, 105, 95, 90, 87, 83, 82, 80};
    int[] bpSys = {120, 120, 118, 120, 122, 120, 121, 122, 121, 121, 122, 121, 122, 121, 122, 125, 129, 135, 140, 145, 150, 155, 159, 165, 155, 130, 131, 130, 125, 122, 121, 120, 120};
    int[] bpDias = {80, 82, 81, 80, 82, 83, 80, 81, 80, 79, 79, 79, 82, 80, 80, 81, 81, 81, 83, 83, 83, 83, 82, 82, 82, 79, 79, 78, 78, 78, 78, 78, 78};

    /**
     * Constructor setting up a simulator
     * @param sensorHandler the sensorHandler this simulator is attached to
     * @param userID the user id this sensor is attached to
     */
    public SensorSimulator(SensorHandler sensorHandler, Integer userID){
        this.sensorHandler = sensorHandler;
        this.userID = userID;
    }

    /**
     * Function to start the patient simulator thread
     */
    public void startSimulation(){
        simulate = true;
        act();
    }

    /**
     * Function to stop simulation thread
     */
    public void stopSimulation(){
        simulate = false;
    }

    /**
     * Function housing the patient simulator thread
     */
    public void act(){
        Thread simulator = new Thread(new Runnable() {
            public void run() {
                // starting at 1 and counting by 2 is always odd
                try{
                    while(simulate){
                        Thread.sleep(1000);
                        System.out.println("User ID " + userID.toString() + " Alive Still");
                        HealthProViewData data = new HealthProViewData(heartbeat[currentCount], 100, (int)((Math.random() * (20 - 30)) + 30), new Pair(bpSys[currentCount], bpDias[currentCount]), userID);
                        /*HealthProViewData data = new HealthProViewData(
                                (int) ((Math.random() * (200 - 50)) + 50),
                                100,
                                (int) ((Math.random() * (20 - 30)) + 30),
                                new Pair(120, 80),
                                userID
                        );*/
                        sensorHandler.updateData(data);
                        if(currentCount < 32){
                            currentCount++;
                        }
                        else {
                            currentCount = 0;
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        });
        simulator.start();
    }
}
