package controller;

import DataClasses.EventData;
import DataClasses.HealthProStats;
import com.patientobserver.DataHandler;
import com.patientobserver.DataSim;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The main controller class that facilitates requests being routed to the Model and the View
 */
public class ControllerMain {
    private ViewController viewController;
    private SensorHandler sensorHandler;
    private static ControllerMain controllerMain = null;
    private Queue<EventData> eventDataQueue;
    private DataHandler dataHandler;
    private boolean eventHandlerRunning = true;
    private SendMail sendmail1;

    /**
     * Constructor that creates all the auxiliary controller classes and launches the event handler thread
     */
    private ControllerMain() {
        viewController = ViewController.getInstance(this);
        sensorHandler = SensorHandler.getInstance(this);
        dataHandler = new DataSim();
        eventDataQueue = new LinkedList<EventData>();
        sendmail1= new SendMail();
        eventHandler();
    }

    /**
     * FUnction to get the ViewController Object
     * @return ViewController stored in ControllerMain class
     */
    public ViewController getViewController(){
        return viewController;
    }

    /**
     * Event Handler function that houses the Event Handler thread.
     *
     * Processed events in the eventDataQueue in order of arrival
     */
    private void eventHandler(){
        Thread eventHandlerThread = new Thread(new Runnable() {
            public void run() {
                // starting at 1 and counting by 2 is always odd
                while(eventHandlerRunning) {
                    try {
                        Thread.sleep(2);
                        if(!eventDataQueue.isEmpty()){
                            EventData currentEvent = eventDataQueue.peek();
                            switch (currentEvent.eventType) {
                                case DATA_FROM_SENSOR: {
                                    EventData eventData = eventDataQueue.poll();
                                    viewController.updateData(eventData.healthProViewData);
                                    dataHandler.addData(eventData.healthProViewData);
                                    break;
                                }
                                case USER_REQUEST_LOGIN: {
                                    break;
                                }
                                case GET_SUBSCRIBED_LIST: {
                                    EventData eventData = eventDataQueue.poll();
                                    viewController.updateSubs(dataHandler.getSubsData(eventData.userID));
                                    break;
                                }
                                case LOG_OUT: {
                                    sensorHandler.killAllSims();
                                    sensorHandler.die();
                                    viewController.die();
                                    sensorHandler = null;
                                    viewController = null;
                                    controllerMain = null;
                                    eventHandlerRunning = false;
                                    break;
                                }
                                case GET_STATS_DATA: {
                                    EventData eventData = eventDataQueue.poll();
                                    HealthProStats stats = dataHandler.getStatData(eventData.userID);
                                    viewController.updateStats(stats, eventData.userDataShort.userID);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
        eventHandlerThread.start();
    }

    /**
     * Global point of access for the ControllerMain singleton instance created
     * @return ControllerMain singleton
     */
    public static ControllerMain getInstance(){
        if(controllerMain == null){
            controllerMain = new ControllerMain();
        }
        return controllerMain;
    }

    /**
     * Synchronized thread safe function to add events to the event queue
     * @param event the event to be added to the eventDataQueue
     */
    public synchronized void addEvent(EventData event){
        eventDataQueue.add(event);
    }
}
