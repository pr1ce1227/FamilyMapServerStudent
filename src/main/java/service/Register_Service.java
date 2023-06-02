package service;

import dao.*;
import model.Authtoken;
import model.Event;

import java.io.FileNotFoundException;
import java.util.UUID;
import model.User;
import model.Person;
import request_result.Register_Request;
import request_result.Register_Responce;

import java.sql.SQLException;

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
        Database db = new Database();
        Register_Responce rep = null;
        Person person = null;
        try {
            generate_people gp = new generate_people();
            person = gp.generatePerson(req.getGender(), 4, req.getUsername(), 4);
            person.setLastName(req.getLastName());
            person.setFirstName(req.getFirstName());
            PersonDAO pd = new PersonDAO(db.openConnection());
            pd.update(person);
            db.closeConnection(true);
        }
        catch (FileNotFoundException | DataAccessException e) {
            throw new RuntimeException(e);
        }

        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), person.getPersonID());
        try {
            UserDAO ud = new UserDAO(db.openConnection());
            ud.insert(user);
            String authToken = UUID.randomUUID().toString();
            rep = new Register_Responce(authToken, req.getUsername(), person.getPersonID(), true, null);
            AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
            Authtoken token = new Authtoken(authToken, req.getUsername());
            ad.insert(token);
            db.closeConnection(true);
        }
        catch (DataAccessException da){
            rep = new Register_Responce(null, null, null, false, "Failed to insert user");
            db.closeConnection(false);
        }
        return rep;
    }
}
