package controller;

import DataClasses.EventData;
import DataClasses.HealthProViewData;

import java.util.ArrayList;

/**
 * Auxiliary controller class that controls the Sensor simulators that simulate patients being monitored
 */
public class SensorHandler {
    private ControllerMain controllerMain;
    private ArrayList<SensorSimulator> simList;
    private int id;
    private static SensorHandler sensorHandler = null;

    /**
     * Constructor launching two patient simulators.
     */
    private SensorHandler() {
        simList = new ArrayList<SensorSimulator>();
        simList.add(new SensorSimulator(this, 100000001));
        simList.add(new SensorSimulator(this, 100000002));
        simList.get(0).startSimulation();
        simList.get(1).startSimulation();
    }

    /**
     * Function acting as a global point of access for the SensorHandler singleton
     * @return the SensorHandler singleton
     */
    public static SensorHandler getInstance(){
         if(sensorHandler == null){
             sensorHandler = new SensorHandler();
         }
         return sensorHandler;
    }

    /**
     * Function acting as a global point of access for the SensorHandler singleton used for first time initialization
     * @param controllerMain takes a reference (object) to the ControllerMain that made it
     * @return the SensorHandler singleton
     */
    public static SensorHandler getInstance(ControllerMain controllerMain){
        if(sensorHandler == null){
            sensorHandler = new SensorHandler();
            sensorHandler.controllerMain = controllerMain;
        }
        return sensorHandler;
    }

    /**
     * Function that adds an event to the event handler as events come in from simulators
     * @param data data from a simulator
     */
    public synchronized void updateData(HealthProViewData data){
        EventData eventData = new EventData();
        eventData.eventType = EventData.EventType.DATA_FROM_SENSOR;
        eventData.healthProViewData = data;
        controllerMain.addEvent(eventData);
    }

    /**
     * Function to kill all running simulators
     */
    public void killAllSims(){
         for(SensorSimulator x : simList){
             x.stopSimulation();
         }
    }

    /**
     * Function killing the SensorHandler singleton
     */
    public void die(){
         sensorHandler = null;
    }
}
