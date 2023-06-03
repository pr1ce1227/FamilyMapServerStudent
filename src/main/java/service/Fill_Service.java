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
                PersonDAO pd = new PersonDAO(db.getConnection());
                pd.delete(user);
                EventDAO ed = new EventDAO(db.getConnection());
                ed.delete(user);
                Register_Responce rep = null;
                Person person = null;
                generate_people gp = new generate_people();

                person = gp.generatePerson(user.getGender(), generations, user.getUsername(), generations, db.getConnection());

                // ******************************************** only use one connection
                pd = new PersonDAO(db.getConnection());
                person.setLastName(user.getLastName());
                person.setFirstName(user.getFirstName());
                pd.update(person);
                int numPeople = pd.getPeople(person.getAssociatedUsername()).length;
                ed = new EventDAO(db.getConnection());
                int numEvents = ed.getEvents(person.getAssociatedUsername()).length;
                fr = new Fill_Responce("Successfully added " + numPeople + " persons and " + numEvents + " events to the database.", true);
                db.closeConnection(true);
                return fr;
            }
            db.closeConnection(false);
        }
        catch (DataAccessException | FileNotFoundException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }


        return  fr;}
}
