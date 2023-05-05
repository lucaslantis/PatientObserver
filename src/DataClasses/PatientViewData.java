package DataClasses;
/**
 * Data structure to send Patient Data to the PatientView
 */
public class PatientViewData {
    public int heartRate;
    public Pair bloodPressure;
    public float steps;

    /**
     * Default constructor with no parameters
     */
    public PatientViewData(){}

    /**
     * Constructor to initialize HealthProViewData object.
     * @param heartRate Patient heart rate
     * @param steps Patient steps.
     * @param bloodPressure Patient blood pressure
     */
    public PatientViewData(int heartRate, float steps, Pair bloodPressure){
        this.heartRate = heartRate;
        this.steps = steps;
        this.bloodPressure = bloodPressure;
    }
}
