package model;

import java.util.Objects;

/**
 * AuthToken is a class that stores an authorization token with its accompanying username
 */

public class Authtoken {
    /**
    * The token used to authenticate a user
    */
    private String authtoken;

    /**
     * The username that is associated with the given authentication token
     */
    private String username;

    /**
     * Takens in the given token and username and creates an authtoken object
     * @param authtoken authorization token
     * @param username user tied to this token
     */
    public Authtoken(String authtoken, String username){
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param o object being compared to current authToken
     * @return boolean returns wether or not the objects are equal based on value of token and username
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authtoken1 = (Authtoken) o;
        return Objects.equals(authtoken, authtoken1.authtoken) && Objects.equals(username, authtoken1.username);
    }
}
