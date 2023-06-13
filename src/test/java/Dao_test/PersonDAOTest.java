package Dao_test;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import data.persons;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private Person bestPerson2;
    private Person worstPerson;
    private PersonDAO pDao;
    private User user;

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

        user = new User("Gale", "Price", "gmail", "shad",
                "Montierth", "m", "12345" );

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDao = new PersonDAO(conn);
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
    public void findPass() throws  DataAccessException{
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws  DataAccessException{
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNull(compareTest);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        pDao.insert(bestPerson);
        // Let's use a find method to get the event that we just put in back out.
        Person compareTest = pDao.find(bestPerson.getPersonID());

        assertNotNull(compareTest);

        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {

        pDao.insert(bestPerson);


        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void deletePass() throws DataAccessException {
        // Insert into database
        pDao.insert(bestPerson);

        // Verify in database
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(1, pDao.delete(user));
    }

    @Test
    public void deleteFail() throws DataAccessException {
        // Don't insert item and try to delete
        // Verify nothing was deleted
        assertEquals(0, pDao.delete(user));
    }

    @Test
    public void getEventsPass() throws DataAccessException {
        // Insert into database
        pDao.insert(bestPerson);
        pDao.insert(worstPerson);
        Person[] pp = {worstPerson,bestPerson};
        persons Persons = new persons(pp);
        persons ps2 = new persons(pDao.getPeople(bestPerson.getAssociatedUsername()));
        assertNotNull(ps2);
        assertEquals(Persons, ps2);
    }

    @Test
    public void getEventsFail() throws DataAccessException {
        // Insert into database
        pDao.insert(bestPerson);
        pDao.insert(worstPerson);
        Person[] pp = {bestPerson,worstPerson};
        persons Persons = new persons(pp);

        // Look for event that doens't exist
        assertThrows(DataAccessException.class, () -> pDao.getPeople("random"));
    }

    @Test
    public void updatePass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.update(bestPerson2);
        assertEquals(bestPerson2, pDao.find(bestPerson.getPersonID()));
    }

    @Test
    public void updateFail() throws DataAccessException {
        Person rando = new Person();
        // giver person that doesn't exist

        assertThrows(DataAccessException.class, () -> pDao.update(rando));
    }

    @Test
    public void clearSuccess1() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest3 = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest3);
        assertEquals(bestPerson, compareTest3);

        pDao.clear();
        Person compareTest2 = pDao.find(bestPerson.getPersonID());
        assertNull(compareTest2);
    }

    @Test
    public void clearSuccess2() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(worstPerson);
        Person compareTest3 = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest3);
        assertEquals(bestPerson, compareTest3);

        pDao.clear();
        Person compareTest2 = pDao.find(bestPerson.getPersonID());
        assertNull(compareTest2);
    }
}