package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request_result.Load_Request;
import request_result.Load_Responce;

public class Load_Service {
    public void Load_Service(){}

    public Load_Responce load(Load_Request req){

        // Open database, build response
        Database database = new Database();
        Load_Responce loadResponce = null;

        try {
            // Clear all tables
            PersonDAO personDAO = new PersonDAO(database.openConnection());
            personDAO.clear();
            UserDAO userDAO = new UserDAO(database.getConnection());
            userDAO.clear();
            EventDAO eventDAO = new EventDAO(database.getConnection());
            eventDAO.clear();
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());
            authtokenDAO.clear();

            // Build array for users persons and events found in request
            User[] users = req.getUsers();
            Person[] persons = req.getPersons();
            Event[] events = req.getEvents();

            // Add all users
            for(User u : users){
                userDAO.insert(u);
            }

            // Add all people
            for(Person p : persons){
                personDAO.insert(p);
            }

            // Add all events
            for(Event e : events){
                eventDAO.insert(e);
            }

            database.closeConnection(true);

            // Build reponse
            loadResponce = new Load_Responce("Successfully added " + req.getUsers().length + " users, " +
                    req.getPersons().length + " persons, and " + req.getEvents().length + " events", true);


        }
        catch (DataAccessException e) {
            // rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }

        return  loadResponce;
    }
}
