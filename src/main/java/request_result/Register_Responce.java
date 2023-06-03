package request_result;

/**
 * Responce of wether the registration was succesful
 */
public class Register_Responce {
    /**
     * Authorization token of the new user
     */
    private String authtoken;
    /**
     * username of the new user
     */
    private String username;
    /**
     * person ID of the new user
     */
    private String personID;
    /**
     * wether or not the user was created succesfully
     */
    private boolean success;
    private  String message;

    /**
     * initialize the responce variables
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public Register_Responce(String authtoken, String username, String personID, boolean success, String message){
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
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

    public String getMessage(){return  message;}

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
