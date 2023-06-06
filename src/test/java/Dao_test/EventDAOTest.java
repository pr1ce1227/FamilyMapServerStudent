package Dao_test;

import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.events;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event bestEvent;
    private Event worstEvent;
    private EventDAO eDao;

    Connection conn;

    private User user;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();

        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        worstEvent = new Event("Walking_123A", "Gale", "Gale12A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        user = new User("Gale", "Price", "gmail", "shad",
                "Montierth", "m", "12345" );

        // Here, we'll open the connection in preparation for the test case to use it
        conn = db.openConnection();

        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDAO(conn);

        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(true);
    }

    @Test
    public void findPass() throws  DataAccessException{
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws  DataAccessException{
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNull(compareTest);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Insert into database
        eDao.insert(bestEvent);

        // Verify in database
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Attempt to insert same event
        eDao.insert(bestEvent);

        // verify exception is thrown
        assertThrows(DataAccessException.class, () -> eDao.insert(bestEvent));
    }

    @Test
    public void deletePass() throws DataAccessException {
        // Insert into database
        eDao.insert(bestEvent);

        // Verify in database
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(1, eDao.delete(user));
    }

    @Test
    public void deleteFail() throws DataAccessException {
        // Don't insert item and try to delete
        // Verify nothing was deleted
        assertEquals(0, eDao.delete(user));
    }

    @Test
    public void getEventsPass() throws DataAccessException {
        // Insert into database
        eDao.insert(bestEvent);
        eDao.insert(worstEvent);
        Event[] ev = {bestEvent,worstEvent};
        events Events = new events(ev);
        events ev2 = new events(eDao.getEvents(bestEvent.getAssociatedUsername()));
        assertNotNull(ev2);
        assertEquals(Events, ev2);
    }

    @Test
    public void getEventsFail() throws DataAccessException {
        // Insert into database
        eDao.insert(bestEvent);
        eDao.insert(worstEvent);
        Event[] ev = {bestEvent, worstEvent};
        events Events = new events(ev);

        // Look for event that doens't exist
        assertThrows(DataAccessException.class, () -> eDao.getEvents("random"));
    }



    @Test
    public void clearSuccess1() throws DataAccessException {
        eDao.insert(bestEvent);
        Event compareTest3 = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest3);
        assertEquals(bestEvent, compareTest3);

        eDao.clear();
        Event compareTest2 = eDao.find(bestEvent.getEventID());
        assertNull(compareTest2);
    }

    @Test
    public void clearSuccess2() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(worstEvent);
        Event compareTest3 = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest3);
        assertEquals(bestEvent, compareTest3);

        eDao.clear();
        Event compareTest2 = eDao.find(bestEvent.getEventID());
        assertNull(compareTest2);
    }
}
