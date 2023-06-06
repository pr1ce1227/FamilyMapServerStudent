package service;

import dao.*;
import model.Authtoken;
import model.User;
import request_result.Login_Request;
import request_result.Login_Responce;

import java.util.UUID;

public class Login_Service {
    public Login_Responce login(Login_Request req) {

        // Build response and open database
        Login_Responce loginResponce = null;
        Database database = new Database();
        try {
            UserDAO userDAO = new UserDAO(database.openConnection());

            // Verify user exists
            if (userDAO.find(req.getUsername()) != null) {
                User user = userDAO.find(req.getUsername());

                // Verify password and request password match
                if (user.getPassword().equals(req.getPassword())) {
                    // Generate authToken
                    String authToken = UUID.randomUUID().toString();
                    AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());
                    Authtoken token = authtokenDAO.find(req.getUsername());

                    // Insert authtoken for user
                    if (token == null) {
                        token = new Authtoken(authToken, req.getUsername());
                        authtokenDAO.insert(token);
                    } else {
                        token.setAuthtoken(authToken);
                        authtokenDAO.update(token);
                    }

                    loginResponce = new Login_Responce(authToken, req.getUsername(), user.getPersonId(), true, null);
                    database.closeConnection(true);
                } else {
                    // rollback changes
                    loginResponce = new Login_Responce(null, null, null, false, "Error: Wrong password");
                    database.closeConnection(false);
                }
            } else {
                // Rollback changes
                loginResponce = new Login_Responce(null, null, null, false, "Error: Username not found");
                database.closeConnection(false);
            }
        } catch (DataAccessException e) {
            // Rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }

        return loginResponce;
    }
}
