package service;

import dao.*;
import java.io.FileNotFoundException;
import model.*;
import request_result.Fill_Responce;
import request_result.Register_Responce;

public class Fill_Service {
    public Fill_Responce fill(String userName, int generations){
        // Default response
        Fill_Responce fillResponce = new Fill_Responce("Error: In fill service", false);

        // Open database
        Database database = new Database();
        try {
            UserDAO userDAO = new UserDAO(database.openConnection());

            // Check if username exists
            if(userDAO.find(userName) != null){
                // Remove all person with associated username
                User user =  userDAO.find(userName);
                PersonDAO personDAO = new PersonDAO(database.getConnection());
                personDAO.delete(user);

                // Remove all event with associated username
                EventDAO eventDAO = new EventDAO(database.getConnection());
                eventDAO.delete(user);

                Register_Responce rep = null;
                Person person = null;

                // Generate 4 generations of people for user
                generate_people generatePeople = new generate_people();
                person = generatePeople.generatePerson(user.getGender(), generations, user.getUsername(),
                        generations, database.getConnection());

                // update base person with users information
                personDAO = new PersonDAO(database.getConnection());
                person.setLastName(user.getLastName());
                person.setFirstName(user.getFirstName());
                personDAO.update(person);

                // Get number of peple added
                int numPeople = personDAO.getPeople(person.getAssociatedUsername()).length;
                eventDAO = new EventDAO(database.getConnection());

                // Get number of events added
                int numEvents = eventDAO.getEvents(person.getAssociatedUsername()).length;

                // Build repsonse
                fillResponce = new Fill_Responce("Successfully added " + numPeople + " persons and "
                        + numEvents + " events to the database.", true);

                // Commit changes and return
                database.closeConnection(true);
                return fillResponce;
            }
            // Rollback changes
            database.closeConnection(false);
        }
        catch (DataAccessException | FileNotFoundException e) {
            // Rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }

        return  fillResponce;}
}
