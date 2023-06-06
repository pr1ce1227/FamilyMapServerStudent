package Service_test;

import dao.*;
import data.events;
import data.persons;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request_result.Register_Request;
import service.Clear_Service;
import service.Register_Service;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class Service_test {
    private Database db;
    private Person bestPerson;
    private Person bestPerson2;
    private Person worstPerson;
    private PersonDAO pDao;
    private User user;
    private Event bestEvent;
    private Event worstEvent;
    private EventDAO eDao;

    private Authtoken bestToken;
    private Authtoken bestToken2;
    private Authtoken worstToken;
    private Authtoken token;
    private AuthtokenDAO aDao;

    private User bestUser;
    private User worstUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestPerson = new Person("person123", "Gale", "Caleb",
                "Price", "m", "dad123", "mom123",
                "sp123");

        bestPerson2 = new Person("person123", "Gale", "Rachael",
                "Price", "f", "dad123", "mom123",
                "sp123");

        worstPerson = new Person("person12", "Gale", "Dallin",
                "Price", "m", "dad123", "mom123",
                "sp123");

        bestToken = new Authtoken("1234", "Caleb");

        bestToken2 = new Authtoken("2468", "Caleb");

        worstToken = new Authtoken("12345", "Rachael");

        user = new User("Gale", "Price", "gmail", "shad",
                "Montierth", "m", "12345" );

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        worstEvent = new Event("Walking_123A", "Gale", "Gale12A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestUser = new User("person123", "pr1ce12", "Caleb@gmail.com",
                "caleb", "price", "m", "cal123");

        worstUser = new User("person555", "pr1ce12", "roof@gmail.com",
                "Rachael", "price", "m", "cal123");


        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDao = new PersonDAO(conn);
        aDao = new AuthtokenDAO(conn);
        eDao = new EventDAO(conn);
        uDao = new UserDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void Clear_Service_Pass() throws  DataAccessException{

    }

    @Test
    public void Clear_Service_Pass2() throws  DataAccessException{
        assertNotNull(1);
    }


}