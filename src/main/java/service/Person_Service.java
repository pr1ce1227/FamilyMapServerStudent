package service;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import data.*;

import model.Person;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.UUID;
import request_result.Person_Responce;

/**
 * Used to answer service requests about people
 */
public class Person_Service {
    generate_people gp;

    public Person_Service() throws FileNotFoundException {
      this.gp = new generate_people();
    }

    public void addPeople(){
        gp.generatePerson("m", 3,  "username", 3);
    }

}
