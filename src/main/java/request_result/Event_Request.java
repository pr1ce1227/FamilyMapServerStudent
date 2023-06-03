package request_result;

public class Event_Request {
    String EventId;
    String token;

    public Event_Request(String eventId, String token) {
        EventId = eventId;
        this.token = token;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
