package request_result;

import model.*;

/**
 * load the database with information
 */
public class Load_Request {
    /**
     * array of new users to be added
     */
    public User[] users;
    /**
     * array of new people to be added
     */
    public Person[] persons;
    /**
     * array of new events to be added
     */
    public Event[] events;

    /**
     * Initialize the request parameters
     * @param users
     * @param persons
     * @param events
     */
    public Load_Request(User[] users, Person[] persons, Event[] events){
        this.events = events;
        this.persons = persons;
        this.users = users;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
