package DataClasses;

/**
 * Class containing the user data of the user
 */
public class UserDataShort {
    /**
     * User type enum for all possible types of users
     */
    public enum UserType{
        HEALTH_PRO,
        REGULAR_USR
    }
    public String fName;
    public String mName;
    public String lName;
    public UserType userType;
    public boolean authorized;
    public int userID;

    /**
     * Default constructor with no parameters
     */
    public UserDataShort(){}

    /**
     * Constructor setting the authorization of user data
     * @param authorized authorization confirmation
     */
    public UserDataShort(boolean authorized){ this.authorized = authorized; }

    /**
     * Constructor to set all User data in object
     * @param fName first name
     * @param mName middle name
     * @param lName last name
     * @param userType user type
     * @param authorized authorization confirmation
     */
    public UserDataShort(String fName, String mName, String lName, UserType userType, boolean authorized){
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.userType = userType;
        this.authorized = authorized;
    }
    public boolean getAuthorization() { return  authorized;}

    /**
     * Constructor that takes in a user data table
     * @param data
     */
    public UserDataShort(UserDataTable data){
        this.fName = data.fName;
        this.lName = data.lName;
        this.mName = data.mName;
        this.userType = data.userType;
        this.userID = data.userID;
    }

    /**
     * getter function to get the user type
     * @return the user type enum
     */
    public UserType getUserType() {
        return userType;
    }

}
