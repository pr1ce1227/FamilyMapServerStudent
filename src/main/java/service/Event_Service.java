package service;

import dao.*;
import data.events;
import data.persons;
import model.Event;
import model.Person;
import request_result.EventAll_Responce;
import request_result.Event_Request;
import request_result.Event_Responce;
import request_result.Person_Responce;

public class Event_Service {

    public  Event_Service(){}

    public Event_Responce getEventObject(Event_Request req){
        Database db = new Database();
        Event_Responce er = null;
        try {
            EventDAO ed = new EventDAO(db.openConnection());
            if (ed.find(req.getEventId()) != null){
                Event event = ed.find(req.getEventId());
                AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
                if(ad.find(event.getAssociatedUsername()) != null) {
                    if (ad.find(event.getAssociatedUsername()).getAuthtoken().equals(req.getToken())) {
                        er = new Event_Responce(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), true, null);
                    }
                    else {
                        er = new Event_Responce(null, null, null, null, null, null, null, null, null, false, "Error: Authtoken for this event doesn't belong to user");
                    }
                }
                else {
                    er = new Event_Responce(null, null, null, null, null, null, null, null, null, false, "Error: Authtoken for this event doesn't belong to user");
                }
            }
            else{
                er = new Event_Responce(null, null, null, null, null, null, null, null, null, false, "Error: Could not find the event associated with eventID");
            }
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }

        return er;
    }

    public EventAll_Responce getFamilyEvents(String token){
        Database db = new Database();
        try {
            AuthtokenDAO ad = new AuthtokenDAO(db.openConnection());
            String userName = ad.findUsername(token);

            if(userName != null) {
                EventDAO ed = new EventDAO(db.getConnection());
                Event[] ev = ed.getEvents(userName);
                db.closeConnection(true);
                events EV =  new events(ev);

                return new EventAll_Responce(EV.getEvents(), null, true);
            }
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }
        db.closeConnection(false);
        EventAll_Responce ea = new EventAll_Responce(null, "Error: Authoken wasnt found", false);
        return ea;
    }

}
