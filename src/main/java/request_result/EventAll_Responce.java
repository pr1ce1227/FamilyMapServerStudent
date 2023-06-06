package request_result;

import model.Event;

public class EventAll_Responce {
    Event[] data;

    String message;
    boolean success;

    public EventAll_Responce(Event[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
