package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request_result.Load_Request;
import request_result.Load_Responce;

public class Load_Service {


    public void Load_Service(){

    }

    public Load_Responce load(Load_Request req){
        Database db = new Database();
        Load_Responce lr = new Load_Responce("Error: failed to load date", false);;
        try {
            PersonDAO pd = new PersonDAO(db.openConnection());
            pd.clear();
            UserDAO ud = new UserDAO(db.getConnection());
            ud.clear();
            EventDAO ed = new EventDAO(db.getConnection());
            ed.clear();
            AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
            ad.clear();

            User[] users = req.getUsers();
            Person[] persons = req.getPersons();
            Event[] events = req.getEvents();

            for(User u : users){
                ud.insert(u);
            }
            for(Person p : persons){
                pd.insert(p);
            }
            for(Event e : events){
                ed.insert(e);
            }

            db.closeConnection(true);

            lr = new Load_Responce("Successfully added " + req.getUsers().length + " users, " + req.getPersons().length + " persons, and " + req.getEvents().length + " events", true);


        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }


        return  lr;
    }
}
