package request_result;

import service.Register_Service;

/**
 * request to create a new user
 */
public class Register_Request {
    /**
     * potential username must be unique
     */
    private String username;
    /**
     * password does not need to be unique
     */
    private String password;
    /**
     * email o fthe user
     */
    private String email;
    /**
     * first name of the user
     */
    private String firstName;
    /**
     * last name of the user
     */
    private String lastName;
    /**
     * gender of the user either male or female
     */
    private String gender;

    /**
     * initialize the register request variables
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Register_Request(String username, String password, String email, String firstName, String lastName, String gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
