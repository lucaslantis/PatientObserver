package DataClasses;

public class UserDataTable {
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
    public UserDataShort.UserType userType;
    public String address;
    public String phoneNo;
    public int userID;
    public String email;

    /**
     * Constructor setting the authorization of user data
     */
    public UserDataTable(){ }

    /**
     * Constructor to set all User data in object
     * @param fName first name
     * @param mName middle name
     * @param lName last name
     * @param userType user type
     * @param address the address of user
     * @param phoneNo the phone number of the user
     */
    public UserDataTable(String fName, String mName, String lName, UserDataShort.UserType userType, String address, String phoneNo){
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.userType = userType;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    /**
     * Constructor to set all User data in object
     * @param fName first name
     * @param mName middle name
     * @param lName last name
     * @param userType user type
     * @param address the address of user
     * @param phoneNo the phone number of the user
     * @param email the phone number of the user
     */
    public UserDataTable(String fName, String mName, String lName, UserDataShort.UserType userType, String address, String phoneNo, String email){
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.userType = userType;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
    }
}
