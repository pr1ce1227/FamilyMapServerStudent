package model;

import java.util.Objects;

/**
 * User class is a person with an account on the server
 */
public class User {
    /**
     * unique username for each person
     */
    private String username;
    /**
     * password for each user, doesn't have to be unique
     */
    private String password;
    /**
     * email for the user
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
     * gender of the user, either male or female
     */
    private String gender;
    /**
     * id of the person who has created the user account
     */
    private String personId;

    /**
     * Construct to set all of the users account information
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personId
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personId){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * Checks to see if 2 users are the same
     * @param o user object being compared to the current object
     * @return True or false, based on if the users are the same, compared by value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(personId, user.personId);
    }
}
