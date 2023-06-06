package service;

import dao.*;
import data.events;
import model.Event;
import request_result.EventAll_Responce;
import request_result.Event_Request;
import request_result.Event_Responce;


public class Event_Service {

    public  Event_Service(){}

    public Event_Responce getEventObject(Event_Request req){
        // Open database
        Database database = new Database();
        Event_Responce eventResponce = null;
        try {
            EventDAO eventDAO = new EventDAO(database.openConnection());

            // Verify event Id exists
            if (eventDAO.find(req.getEventId()) != null){
                Event event = eventDAO.find(req.getEventId());
                AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());

                // Verify username exists
                if(authtokenDAO.find(event.getAssociatedUsername()) != null) {

                    // verify username Authtoken and request authoken match, build object and return
                    if (authtokenDAO.find(event.getAssociatedUsername()).getAuthtoken().equals(req.getToken())) {
                        eventResponce = new Event_Responce(event.getAssociatedUsername(), event.getEventID(),
                                event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(),
                                event.getCity(), event.getEventType(), event.getYear(), true, null);
                    }
                    else {
                        eventResponce = new Event_Responce(null, null, null, null,
                                null, null, null, null, null, false,
                                "Error: Authtoken for this event doesn't belong to user");
                    }
                }
                else {
                    eventResponce = new Event_Responce(null, null, null, null,
                            null, null, null, null, null, false,
                            "Error: Authtoken for this event doesn't belong to user");
                }
            }
            else{
                eventResponce = new Event_Responce(null, null, null, null,
                        null, null, null, null, null, false,
                        "Error: Could not find the event associated with eventID");
            }
            database.closeConnection(true);
        }
        catch (DataAccessException e) {
            database.closeConnection(false);
            throw new RuntimeException(e);
        }

        return eventResponce;
    }

    public EventAll_Responce getFamilyEvents(String token){
        // Open database
        Database database = new Database();
        try {
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.openConnection());
            String userName = authtokenDAO.findUsername(token);

            // Verify username exists
            if(userName != null) {

                // get event and return
                EventDAO eventDAO = new EventDAO(database.getConnection());
                Event[] eventDAOEvents = eventDAO.getEvents(userName);
                database.closeConnection(true);
                events EV =  new events(eventDAOEvents);
                return new EventAll_Responce(EV.getEvents(), null, true);
            }
            else{
                // rollback changes
                database.closeConnection(false);
            }
        }
        catch (DataAccessException e) {
            // rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }

        // Return failed object
        EventAll_Responce eventALlResponse =
                new EventAll_Responce(null, "Error: Authoken wasnt found", false);
        return eventALlResponse;
    }

}
