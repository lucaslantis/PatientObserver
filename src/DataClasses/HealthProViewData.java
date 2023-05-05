package DataClasses;

/**
 * Data structure to send Patient Data to the HealthProView
 */
public class HealthProViewData {
    public int heartRate;
    public Pair bloodPressure;
    public int o2Sat;
    public int respiratoryRate;
    public int userId;
    public String timeStamp;

    /**
     * Default constructor with no parameters
     */
    public HealthProViewData(){}

    /**
     * Constructor to initialize HealthProViewData object.
     * @param heartRate Patient heart rate
     * @param o2Sat Patient oxygen saturation
     * @param respiratoryRate Patient Respiration Rate
     * @param bloodPressure Patient blood pressure
     */
    public HealthProViewData(int heartRate, int o2Sat, int respiratoryRate, Pair bloodPressure){
        this.heartRate = heartRate;
        this.o2Sat = o2Sat;
        this.respiratoryRate = respiratoryRate;
        this.bloodPressure = bloodPressure;
    }

    /**
     * Constructor to initialize HealthProViewData object.
     * @param heartRate Patient heart rate
     * @param o2Sat Patient oxygen saturation
     * @param respiratoryRate Patient Respiration Rate
     * @param bloodPressure Patient blood pressure
     * @param userId the user id the data belongs to
     */
    public HealthProViewData(int heartRate, int o2Sat, int respiratoryRate, Pair bloodPressure, int userId){
        this.heartRate = heartRate;
        this.o2Sat = o2Sat;
        this.respiratoryRate = respiratoryRate;
        this.bloodPressure = bloodPressure;
        this.userId = userId;
    }
}
