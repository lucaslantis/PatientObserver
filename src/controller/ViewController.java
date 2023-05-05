package controller;
import DataClasses.*;
import com.patientobserver.AppView;
import com.patientobserver.OAppView;
import controller.ControllerMain;
import controller.SViewController;

import java.util.ArrayList;
import java.util.List;

/**
 * Auxiliary controller class to handle views.
 */
public class ViewController implements SViewController {

    private ArrayList<OAppView> viewList;
    private ControllerMain controllermain;
    private HealthProViewData data;
    private static ViewController viewController = null;
    private ArrayList<OAppView> activelyViewingList;
    private ArrayList<Integer> subStore;

    /**
     * Constructor to initialize the ViewController
     * @param controllerMain the MainController object this view controller is attached to.
     */
    private ViewController(ControllerMain controllerMain){
        this.controllermain = controllerMain;
        activelyViewingList = new ArrayList<OAppView>();
        viewList = new ArrayList<OAppView>();
    }

    /**
     * Global point of access function for the ViewController object.
     *
     * Should be called the first time to create a singleton and attach it to the Main Controller object
     * @param controllerMain the Main Controller object this ViewController needs to be attached to
     * @return the ViewController Singleton
     */
    public static ViewController getInstance(ControllerMain controllerMain){
        if(viewController == null){
            viewController = new ViewController(controllerMain);
        }
        return viewController;
    }

    /**
     * Function to add a view as a list of observer
     * @param view the view to be added
     */
    @Override
    public void addView(OAppView view) {
        if (!viewList.contains(view))
            viewList.add(view);
    }

    /**
     * Function to update views. Notifier function.
     */
    @Override
    public void updateViews() {
        for(int i = 0; i < activelyViewingList.size(); i++){
            activelyViewingList.get(i).updateView();
        }
        activelyViewingList.clear();
    }
    /**
     * Function to remove a view to the ViewController
     * @param view the view to be removed
     */
    @Override
    public void removeView(AppView view) {
        if (!viewList.contains(view))
            viewList.remove(view);
    }

    /**
     * Function to get vital sign data
     * @return vital sign data
     */
    public HealthProViewData getHPData(){
        //HealthProViewData data = new HealthProViewData(70, 100, 30, new Pair(120, 80));
        return data;
    }

    /**
     *FUnction to get the list of subscriptions for the user of this view
     * @return list of subscribers
     */
    public ArrayList<Integer> getSubs(){
        return subStore;
    }

    /**
     * FUnction to update active views with data
     * @param data vital sign data
     */
    public void updateData(HealthProViewData data) {
        try{
            for(int i = 0; i < viewList.size(); i++){
                Integer viewerID = viewList.get(i).getViewingID();
                if(viewerID.equals(data.userId)){
                    activelyViewingList.add(viewList.get(i));
                }
            }
            this.data = data;
            updateViews();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Function to submit an event to the event handler thread running
     * @param event the request the event handler needs to do
     */
    public void addEvent(EventData event){
        controllermain.addEvent(event);
    }

    /**
     * Function to update subscriptions for all views that asked for them
     * @param subs the list of subscribed patients.
     */
    public void updateSubs(Pair<Integer, ArrayList<UserDataShort>> subs){
        for(int i = 0; i < viewList.size(); i++) {
            if(viewList.get(i).getUserID() == subs.x){
                viewList.get(i).updateSubs(subs.y);
            }
        }
    }

    /**
     * Function to update statistics in views (the statistics tab)
     * @param stats statistics calculated for the patient being monitored
     * @param requesterID the ID of the user that requested this information
     */
    public void updateStats(HealthProStats stats, Integer requesterID){
        for(int i = 0; i < viewList.size(); i++) {
            if(viewList.get(i).getUserID().equals(requesterID)){
                viewList.get(i).updateStats(stats);
            }
        }
    }

    /**
     * Function to kill the ViewController singleton when logging out
     */
    public void die(){
        viewController = null;
    }
}
