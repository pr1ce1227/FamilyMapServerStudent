package Dao_test;

import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

    public class AuthtokenDAOTest {
        private Database db;
        private Authtoken bestToken;
        private Authtoken bestToken2;
        private Authtoken worstToken;
        private Authtoken token;
        private AuthtokenDAO aDao;
        private User user;

        @BeforeEach
        public void setUp() throws DataAccessException {
            // Here we can set up any classes or variables we will need for each test
            // lets create a new instance of the Database class
            db = new Database();
            // and a new event with random data
            bestToken = new Authtoken("1234", "Caleb");
            bestToken2 = new Authtoken("2468", "Caleb");
            worstToken = new Authtoken("12345", "Rachael");

            token = new Authtoken("123456", "Dallin");

            user = new User("Caleb", "Price", "gmail", "shad",
                    "Montierth", "m", "12345" );

            // Here, we'll open the connection in preparation for the test case to use it
            Connection conn = db.getConnection();
            //Then we pass that connection to the EventDAO, so it can access the database.
            aDao = new AuthtokenDAO(conn);
            //Let's clear the database as well so any lingering data doesn't affect our tests
            aDao.clear();
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
            aDao.insert(bestToken);
            Authtoken compareTest = aDao.find(bestToken.getUsername());
            assertEquals(bestToken, compareTest);
        }

        @Test
        public void findFail() throws  DataAccessException{
            Authtoken compareTest = aDao.find(bestToken.getUsername());
            assertNull(compareTest);
        }

        @Test
        public void insertPass() throws DataAccessException {
            // Start by inserting an event into the database.
            aDao.insert(bestToken);
            // Let's use a find method to get the event that we just put in back out.
            Authtoken compareTest = aDao.find(bestToken.getUsername());

            assertNotNull(compareTest);

            assertEquals(bestToken, compareTest);
        }

        @Test
        public void insertFail() throws DataAccessException {

            aDao.insert(bestToken);
            assertThrows(DataAccessException.class, () -> aDao.insert(bestToken));
        }

        @Test
        public void deletePass() throws DataAccessException {
            // Insert into database
            aDao.insert(bestToken);

            // Verify in database
            Authtoken compareTest = aDao.find(bestToken.getUsername());
            assertNotNull(compareTest);
            assertEquals(1, aDao.delete(bestToken.getUsername()));
        }

        @Test
        public void deleteFail() throws DataAccessException {
            // Don't insert item and try to delete
            // Verify nothing was deleted
            assertThrows(DataAccessException.class, () -> aDao.delete("rando"));
        }



        @Test
        public void updatePass() throws DataAccessException {
            aDao.insert(bestToken);
            aDao.update(bestToken2);
            assertEquals(bestToken2, aDao.find(bestToken.getUsername()));
        }

        @Test
        public void updateFail() throws DataAccessException {
            Authtoken rando = new Authtoken("Stuf", "Crazy");
            // giver person that doesn't exist

            assertThrows(DataAccessException.class, () -> aDao.update(rando));
        }

        @Test
        public void clearSuccess1() throws DataAccessException {
            aDao.insert(bestToken);
            Authtoken compareTest3 = aDao.find(bestToken.getUsername());
            assertNotNull(compareTest3);
            assertEquals(bestToken, compareTest3);

            aDao.clear();
            Authtoken compareTest2 = aDao.find(bestToken.getUsername());
            assertNull(compareTest2);
        }

        @Test
        public void clearSuccess2() throws DataAccessException {
            aDao.insert(bestToken);
            aDao.insert(worstToken);
            Authtoken compareTest3 = aDao.find(bestToken.getUsername());
            assertNotNull(compareTest3);
            assertEquals(bestToken, compareTest3);

            aDao.clear();
            Authtoken compareTest2 = aDao.find(bestToken.getUsername());
            assertNull(compareTest2);
        }

        @Test
        public void findUsernamePass() throws  DataAccessException{
            aDao.insert(bestToken);
            String compareTest = aDao.findUsername(bestToken.getAuthtoken());
            assertEquals(bestToken.getUsername(), compareTest);
        }

        @Test
        public void findUsernameFail() throws  DataAccessException{
            String compareTest = aDao.findUsername(bestToken.getUsername());
            assertNull(compareTest);
        }
    }

