package request_result;

import model.Person;

public class Person_Request {
    String personId;
    String token;

    public Person_Request(String personId, String token) {
        this.personId = personId;
        this.token = token;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
