package DataClasses;

import com.patientobserver.HealthProView;

/**
 * Class representing events the Controller needs to process.
 *
 * Class also contains all the possible data types an event might carry
 */
public class EventData {
    /**
     * Enum listing all possible events the event handler can process
     */
    public enum EventType{
        DATA_FROM_SENSOR,
        USER_REQUEST_LOGIN,
        GET_SUBSCRIBED_LIST,
        GET_STATS_DATA,
        LOG_OUT,
    }

    public EventType eventType;
    public HealthProViewData healthProViewData;
    public PatientViewData patientViewData;
    public UserDataShort userDataShort;
    public UserDataTable userDataTable;
    public Integer userID;

    /**
     * Default constructor with no parameters
     */
    public EventData(){
        healthProViewData = new HealthProViewData();
        patientViewData = new PatientViewData();
        userDataShort = new UserDataShort();
        userDataTable = new UserDataTable();
    }

    /**
     * Constructor that takes an event type
     * @param e the event type
     */
    public EventData(EventType e){
        healthProViewData = new HealthProViewData();
        patientViewData = new PatientViewData();
        userDataShort = new UserDataShort();
        userDataTable = new UserDataTable();
        this.eventType = e;
    }

}
