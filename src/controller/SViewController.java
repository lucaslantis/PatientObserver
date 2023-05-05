package controller;


import com.patientobserver.AppView;
import com.patientobserver.OAppView;

/**
 * Interface the ViewController class needs to implement to act as a subject for the view observers
 */
public interface SViewController {
    /**
     * Function to add a view to the ViewController
     * @param view the view to be added
     */
    public void addView(OAppView view);

    /**
     * Function to remove a view to the ViewController
     * @param view the view to be removed
     */
    public void removeView(AppView view);

    /**
     * Function to update views. Notifier function.
     */
    public void updateViews();
}


