package service;

import dao.*;

import java.io.FileNotFoundException;
import java.util.List;

import model.*;
import request_result.Fill_Responce;
import request_result.Register_Responce;

public class Fill_Service {
    public Fill_Responce fill(String userName, int generations){
        Fill_Responce fr = new Fill_Responce("Error: In fill service", false);

        Database db = new Database();
        try {
            UserDAO ud = new UserDAO(db.openConnection());
            if(ud.find(userName) != null){
                User user =  ud.find(userName);
//                ud.delete(user);
                PersonDAO pd = new PersonDAO(db.getConnection());
                pd.delete(user);
                EventDAO ed = new EventDAO(db.getConnection());
                ed.delete(user);
//                AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
//                ad.delete(userName);
                Register_Responce rep = null;
                Person person = null;
                db.closeConnection(true);
                generate_people gp = new generate_people();
                person = gp.generatePerson(user.getGender(), generations, user.getUsername(), generations);
                pd = new PersonDAO(db.openConnection());
                person.setLastName(user.getLastName());
                person.setFirstName(user.getFirstName());
                pd.update(person);
                fr = new Fill_Responce("Success: removed " + user.getUsername(), true);
                db.closeConnection(true);
                return fr;
            }
            db.closeConnection(false);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        return  fr;}
}
