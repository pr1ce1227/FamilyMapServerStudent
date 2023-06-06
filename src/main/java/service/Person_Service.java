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
        // Open database build response
        Database database = new Database();
        Person_Responce personResponce = null;
        try {
            PersonDAO personDAO = new PersonDAO(database.openConnection());

            // verify person id
            if (personDAO.find(req.getPersonId()) != null){
                Person person = personDAO.find(req.getPersonId());
                AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());

                // Verify username
                if(authtokenDAO.find(person.getAssociatedUsername()) != null) {

                    // Find person with username and verify authtoken
                    if (authtokenDAO.find(person.getAssociatedUsername()).getAuthtoken().equals(req.getToken())) {
                        // Build person
                        personResponce = new Person_Responce(person.getAssociatedUsername(), person.getPersonID(),
                                person.getFirstName(), person.getLastName(), person.getGender(),
                                person.getFatherID(), person.getMotherID(), person.getSpouseID(),
                                null, true);
                    }
                    else{
                        personResponce = new Person_Responce(null, null, null,
                                null, null, null, null, null,
                                "Error: authoken doesn't exist", false );
                    }
                }
                else{
                    personResponce = new Person_Responce(null, null, null,
                            null, null, null, null, null,
                            "Error: authoken doesn't exist", false);
                }
            }
            else{
                personResponce = new Person_Responce(null, null, null,
                        null, null, null, null, null,
                        "Error: authoken doesn't exist", false);
            }
            // COnfirm changes
            database.closeConnection(true);
        }
        catch (DataAccessException e) {
            // Rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }
        return personResponce;
    }

    public PersonFamily_Responce getPersonFamily(String token){
        // Open Database
        Database database = new Database();
        try {
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.openConnection());
            String userName = authtokenDAO.findUsername(token);

            // verify username
            if(userName != null) {
                // Get all people with asosciated username and return
                PersonDAO personDAO = new PersonDAO(database.getConnection());
                Person[] people = personDAO.getPeople(userName);
                database.closeConnection(true);
                return new PersonFamily_Responce(people, null, true);
            }
            // ROllback changes
            database.closeConnection(false);
        }
        catch (DataAccessException e) {
            // Rollback changes
            database.closeConnection(false);
            throw new RuntimeException(e);
        }
        return new PersonFamily_Responce(null,
                "Error: failed to find username associated with authtoken", false);
    }

}
