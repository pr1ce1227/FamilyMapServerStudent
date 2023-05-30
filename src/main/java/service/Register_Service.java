package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import dao.UserDAO;
import model.Event;
import java.util.UUID;
import model.User;
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
    public Register_Responce register(Register_Request req) {
        Database db = new Database();
        Register_Responce rep = null;
        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), "12345");
        try {
            UserDAO ud = new UserDAO(db.openConnection());
            ud.insert(user);
            String authToken = UUID.randomUUID().toString();
            rep = new Register_Responce(authToken, req.getUsername(), "12345", true, null);
            db.closeConnection(true);
        }
        catch (DataAccessException da){
            rep = new Register_Responce(null, null, null, false, "Failed to insert user");
            db.closeConnection(false);
        }

        return rep;
    }
}
