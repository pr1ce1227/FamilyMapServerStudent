package request_result;

/**
 * Build a request to log in
 */
public class Login_Request {
    /**
     * username of the user requesting to log in
     */
    private String username;
    /**
     * password associated with the username
     */
    private String password;

    /**
     * initialize the log in variables
     * @param username
     * @param password
     */
    public Login_Request(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
