package service;

import dao.*;
import model.Authtoken;
import java.io.FileNotFoundException;
import java.util.UUID;
import model.User;
import model.Person;
import request_result.Register_Request;
import request_result.Register_Responce;

/**
 * Used to service new register requests from people 
 */
public class Register_Service {
    /**
     * Takes in a user register request and returns either a success or fail message
     * @param req
     * @return
     */
    public Register_Responce register(Register_Request req)  {
        // Open database
        Database database = new Database();
        Register_Responce registerResponce = null;
        Person person = null;
        try {
            // Generate people and update root person with user
            generate_people generatePeople = new generate_people();
            person = generatePeople.generatePerson(req.getGender(), 4, req.getUsername(), 4, database.openConnection());
            person.setLastName(req.getLastName());
            person.setFirstName(req.getFirstName());
            PersonDAO personDAO = new PersonDAO(database.getConnection());
            personDAO.update(person);
        }
        catch (FileNotFoundException | DataAccessException e) {
            throw new RuntimeException(e);
        }

        // Build User
        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(),
                req.getFirstName(), req.getLastName(), req.getGender(), person.getPersonID());
        try {
            // Add authtoken for new user
            UserDAO userDAO = new UserDAO(database.getConnection());
            userDAO.insert(user);
            String authToken = UUID.randomUUID().toString();
            registerResponce = new Register_Responce(authToken, req.getUsername(), person.getPersonID(), true, null);
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());
            Authtoken token = new Authtoken(authToken, req.getUsername());
            authtokenDAO.insert(token);
            database.closeConnection(true);
        }
        catch (DataAccessException da){
            // Rollback changes
            registerResponce = new Register_Responce(null, null, null,
                    false, "Error: Failed to insert user");
            database.closeConnection(false);
        }
        return registerResponce;
    }
}
