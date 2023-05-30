package request_result;

/**
 * Responce of wether an event was succesffully added
 */
public class Event_Responce {
    /**
     * Username of the user associated with the event
     */
     private String associatedUsername;
    /**
     * id of the event being addded
     */
    private String eventID;
    /**
     * id of the person assciated with the given event
     */
     private String personID;
    /**
     * latitude location in the world where the event occured
     */
    private float latitude;
    /**
     * longitude located of where the event occured
     */
     private float longitude;
    /**
     * country where the event occured
     */
    private String country;
    /**
     * city where the event occured
     */
     private String city;
    /**
     * Type of the event that occured
     */
    private String eventType;
    /**
     * year when the event occured
     */
     private int year;
    /**
     * wether or not the event was succesfully added
     */
    private boolean succes;
    /**
     * Either a success or failur message
     */
     private String message;

    /**
     * Contructing the Responce variables for the success or failur message
     * @param associatedUsername
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param succes
     * @param message
     */
    public Event_Responce(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean succes, String message) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.succes = succes;
        this.message = message;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public boolean isSucces() {
        return succes;
    }
}
