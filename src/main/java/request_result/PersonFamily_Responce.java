package request_result;

import model.Person;

public class PersonFamily_Responce {
    Person[] data;
    String message;
    boolean success;

    public PersonFamily_Responce(Person[] data, String message, boolean success) {

        this.data = data;
        this.message = message;
        this.success = success;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
