package service;

import dao.*;
import model.Authtoken;
import model.Person;
import model.User;
import request_result.Login_Request;
import request_result.Login_Responce;

import java.util.UUID;

public class Login_Service {
    public Login_Responce login(Login_Request req){
        Login_Responce lr = null;
        Database db = new Database();
        try {

            UserDAO ud = new UserDAO(db.openConnection());
            if(ud.find(req.getUsername()) != null){
               User user =  ud.find(req.getUsername());
               if(user.getPassword().equals(req.getPassword())){
                   String authToken = UUID.randomUUID().toString();
                   AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
                   Authtoken token = ad.find(req.getUsername());
                   if(token == null){
                       token = new Authtoken(authToken, req.getUsername());
                       ad.insert(token);
                   }
                   else {
                       token.setAuthtoken(authToken);
                       ad.update(token);
                   }
                   lr = new Login_Responce(authToken, req.getUsername(), user.getPersonId(), true, null);
                   db.closeConnection(true);
                }
               else{
                   lr = new Login_Responce(null, null, null, false, "Error: Wrong password");
                   db.closeConnection(false);
               }
            }
            else{
                lr = new Login_Responce(null, null, null, false, "Error: Username not found");
                db.closeConnection(false);
            }
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }


        return  lr; }
}
