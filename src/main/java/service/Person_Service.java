package service;
import dao.*;
;
import java.io.FileNotFoundException;

import model.Person;
import request_result.PersonFamily_Responce;
import request_result.Person_Request;
import request_result.Person_Responce;

/**
 * Used to answer service requests about people
 */
public class Person_Service {

    public Person_Service() throws FileNotFoundException {

    }

    public Person_Responce getPersonObject(Person_Request req){
        Database db = new Database();
        Person_Responce pr = null;
        try {
            PersonDAO pd = new PersonDAO(db.openConnection());
            if (pd.find(req.getPersonId()) != null){
                Person person = pd.find(req.getPersonId());
                AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
                if(ad.find(person.getAssociatedUsername()) != null) {
                    if (ad.find(person.getAssociatedUsername()).getAuthtoken().equals(req.getToken())) {
                        pr = new Person_Responce(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), null, true);
                    }
                    else{
                        pr = new Person_Responce(null, null, null, null, null, null, null, null,"Error: authoken doesn't exist", false );
                    }
                }
                else{
                    pr = new Person_Responce(null, null, null, null, null, null, null, null, "Error: authoken doesn't exist", false);
                }
            }
            else{
                pr = new Person_Responce(null, null, null, null, null, null, null, null, "Error: authoken doesn't exist", false);
            }
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }

        return pr;
    }

    public PersonFamily_Responce getPersonFamily(String token){
        Database db = new Database();
        try {
            AuthtokenDAO ad = new AuthtokenDAO(db.openConnection());
            String userName = ad.findUsername(token);

            if(userName != null) {
                PersonDAO pd = new PersonDAO(db.getConnection());
                Person[] people = pd.getPeople(userName);
                db.closeConnection(true);
                return new PersonFamily_Responce(people, null, true);
            }
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw new RuntimeException(e);
        }

        return new PersonFamily_Responce(null, "Error: failed to find username associated with authtoken", false);
    }

}
