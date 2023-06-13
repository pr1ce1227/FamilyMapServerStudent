package request_result;

/**
 * build the responce to the attempt to log in
 */
public class Login_Responce
{
    /**
     * If log in successful this is how the user will be authenticated moving forward
     */
    private String authtoken;
    /**
     * username of the user thats been aproved
     */
    private String username;
    /**
     * Person who is linked to the user
     */
    private String personID;
    /**
     * Wether or not the user succesfully logged in
     */
    private boolean success;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * initialize the loggin responce variables
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public Login_Responce(String authtoken, String  username, String  personID, boolean success, String message){
        this.authtoken = authtoken;
        this. username = username;
        this. personID = personID;
        this.success = success;
        this.message = message;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean getSuccess() {
        return success;
    }

}
