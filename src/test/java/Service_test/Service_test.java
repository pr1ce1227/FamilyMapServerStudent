package Service_test;

import dao.*;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request_result.*;
import service.*;
import service.Clear_Service;
import service.Register_Service;
import java.io.FileNotFoundException;

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

        bestUser = new User("Gale", "pr1ce12", "Caleb@gmail.com",
                "Caleb", "Price", "m", "person123");

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
                "Montierth", "m", "person123" );

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        worstEvent = new Event("Walking_123A", "Gale", "Gale12A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);



        worstUser = new User("person555", "pr1ce12", "roof@gmail.com",
                "Rachael", "price", "m", "cal123");

        // Clear all the tables
        db.openConnection();
        pDao = new PersonDAO(db.getConnection());
        pDao.clear();
        aDao = new AuthtokenDAO(db.getConnection());
        aDao.clear();;
        eDao = new EventDAO(db.getConnection());
        eDao.clear();
        uDao = new UserDAO(db.getConnection());
        uDao.clear();;
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void getFamilyEvents_Pass() throws DataAccessException{
        Register_Request Rreq = new Register_Request(bestUser.getUsername(), bestUser.getPassword(),
                bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());
        db.closeConnection(true);
        Register_Service rs = new Register_Service();
        rs.register(Rreq);
        db.openConnection();
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken token = ad.find(bestUser.getUsername());
        db.closeConnection(true);
        Event_Service Es = new Event_Service();
        EventAll_Responce ear =  Es.getFamilyEvents(token.getAuthtoken());
        db.openConnection();

        assertEquals(ear.getData().length, 91);
        assertTrue(ear.isSuccess());
        assertEquals(ear.getData()[0].getAssociatedUsername(), bestUser.getUsername());
    }

    @Test
    public void getFamilyEvents_Fail() throws DataAccessException{
        Register_Request Rreq = new Register_Request(bestUser.getUsername(), bestUser.getPassword(),
                bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());
        db.closeConnection(true);
        Register_Service rs = new Register_Service();
        rs.register(Rreq);
        db.openConnection();
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken token = ad.find(worstUser.getUsername());
        Event_Service Es = new Event_Service();
        db.closeConnection(true);
        EventAll_Responce ear =  Es.getFamilyEvents("random");
        db.openConnection();

        assertNull(ear.getData());
        assertFalse(ear.isSuccess());
    }

    @Test
    public void getEventObject_Pass() throws DataAccessException{
        EventDAO ed = new EventDAO(db.getConnection());
        ed.insert(bestEvent);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Event_Service Es = new Event_Service();
        Event_Request er = new Event_Request(bestEvent.getEventID(), "12345");
        Event_Responce Er = Es.getEventObject(er);
        db.openConnection();
        assertEquals(true, Er.isSucces());
        assertEquals(bestEvent.getEventID(), Er.getEventID());
        assertEquals(bestUser.getUsername(), Er.getAssociatedUsername());
    }

    @Test
    public void getEventObject_Fail() throws DataAccessException{
        EventDAO ed = new EventDAO(db.getConnection());
        ed.insert(bestEvent);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Event_Service Es = new Event_Service();
        Event_Request er = new Event_Request(bestEvent.getEventID(), "Random");
        Event_Responce Er = Es.getEventObject(er);
        db.openConnection();
        assertEquals(false, Er.isSucces());
        assertNull(Er.getEventID());
        assertNull(Er.getEventID());
    }

    @Test
    public void fill_Pass() throws DataAccessException, FileNotFoundException {
        PersonDAO pd = new PersonDAO(db.getConnection());
        pd.insert(bestPerson);
        UserDAO ud = new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Person_Service ps = new Person_Service();
        int preSize = ps.getPersonFamily("12345").getData().length;
        Fill_Service fs = new Fill_Service();
        Fill_Responce fr = fs.fill(bestUser.getUsername(), 4);
        db.openConnection();
        int postSize = ps.getPersonFamily("12345").getData().length;
        assertNotEquals(preSize, postSize);
        assertEquals(postSize, 31);
        assertEquals(true, fr.isSuccess());
    }

    @Test
    public void fill_fail() throws DataAccessException, FileNotFoundException {
        PersonDAO pd = new PersonDAO(db.getConnection());
        pd.insert(bestPerson);
        UserDAO ud = new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Person_Service ps = new Person_Service();
        int preSize = ps.getPersonFamily("12345").getData().length;
        Fill_Service fs = new Fill_Service();
        Fill_Responce fr = fs.fill(worstUser.getUsername(), 4);
        db.openConnection();
        int postSize = ps.getPersonFamily("12345").getData().length;
        assertEquals(preSize, postSize);
        assertEquals(postSize, 1);
        assertEquals(false, fr.isSuccess());
    }

    @Test
    public void login_Pass() throws DataAccessException  {
       UserDAO ud = new UserDAO(db.getConnection());
       ud.insert(bestUser);
       db.closeConnection(true);
       Login_Request lr = new Login_Request(bestUser.getUsername(), bestUser.getPassword());
       Login_Service ls = new Login_Service();
       Login_Responce Lresp = ls.login(lr);
       db.openConnection();
       assertEquals(bestUser.getPersonId(), Lresp.getPersonID());
       assertEquals(true, Lresp.getSuccess());
    }

    @Test
    public void login_fail() throws DataAccessException  {
        UserDAO ud = new UserDAO(db.getConnection());
        ud.insert(bestUser);
        db.closeConnection(true);
        Login_Request lr = new Login_Request(worstUser.getUsername(), worstUser.getPassword());
        Login_Service ls = new Login_Service();
        Login_Responce Lresp = ls.login(lr);
        db.openConnection();
        assertNotEquals(bestUser.getPersonId(), Lresp.getPersonID());
        assertEquals(false, Lresp.getSuccess());
    }

    @Test
    public void Register_Pass() throws DataAccessException  {
            UserDAO ud = new UserDAO(db.getConnection());
            assertNull(ud.find(bestUser.getUsername()));
            Register_Request Rreq = new Register_Request(bestUser.getUsername(), bestUser.getPassword(),
                    bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());
            db.closeConnection(true);
            Register_Service rs = new Register_Service();
            rs.register(Rreq);
            db.openConnection();
            ud = new UserDAO(db.getConnection());
            assertNotEquals(null, ud.find(bestUser.getUsername()));
            assertEquals(ud.find(bestUser.getUsername()).getPassword(), Rreq.getPassword());
    }

    @Test
    public void Register_Fail() throws DataAccessException  {
        UserDAO ud = new UserDAO(db.getConnection());
        assertNull(ud.find(bestUser.getUsername()));
        Register_Request Rreq = new Register_Request(bestUser.getUsername(), bestUser.getPassword(),
                bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());
        db.closeConnection(true);
        Register_Service rs = new Register_Service();
        rs.register(Rreq);
        Register_Responce rp = rs.register(Rreq);
        assertEquals(false, rp.getSuccess());
        db.openConnection();
    }

    @Test
    public void getPersonFamily_Pass() throws DataAccessException, FileNotFoundException {
        UserDAO ud = new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Fill_Service fill = new Fill_Service();
        fill.fill(bestUser.getUsername(), 4);
        Person_Service ps = new Person_Service();
        PersonFamily_Responce pfr = ps.getPersonFamily(tok.getAuthtoken());
        db.openConnection();
        int members = 31;
        assertEquals(members, pfr.getData().length);
        assertEquals(bestUser.getUsername(), pfr.getData()[members-1].getAssociatedUsername());
    }

    @Test
    public void getPersonFamily_fail() throws DataAccessException, FileNotFoundException {
        UserDAO ud = new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        db.closeConnection(true);
        Fill_Service fill = new Fill_Service();
        fill.fill(bestUser.getUsername(), 4);
        Person_Service ps = new Person_Service();
        PersonFamily_Responce pfr = ps.getPersonFamily("random");
        db.openConnection();
        int members = 31;
        assertEquals(null, pfr.getData());
        assertEquals(false, pfr.getSuccess());
    }

    @Test
    public void load_Pass() throws DataAccessException  {
        Person[] persons = {bestPerson, worstPerson};
        Event[] events = {bestEvent, worstEvent};
        User[] users = {bestUser, worstUser};
        Load_Request lr = new Load_Request(users, persons, events);
        Load_Service ls = new Load_Service();
        db.closeConnection(true);
        Load_Responce Lresp = ls.load(lr);
        db.openConnection();
        PersonDAO pd = new PersonDAO(db.getConnection());
        UserDAO ud = new UserDAO(db.getConnection());
        EventDAO ed = new EventDAO(db.getConnection());
        assertNotNull(pd.find(bestPerson.getPersonID()));
        assertNotNull(ud.find(worstUser.getUsername()));
        assertNotNull(ed.find(worstEvent.getEventID()));
        assertEquals(true, Lresp.isSuccess());
    }
    @Test
    public void load_Fail() throws DataAccessException  {
        // Hard to make this fail due to it clearing the database and only adding perfect request body.....
        // Only errors I can think of are bad input from the user but hard to emulate here as we can't pass in bad input

        // Test a second load instead
        Person[] persons = {bestPerson};
        Event[] events = {worstEvent};
        User[] users = {bestUser, worstUser};
        Load_Request lr = new Load_Request(users, persons, events);
        Load_Service ls = new Load_Service();
        db.closeConnection(true);
        Load_Responce Lresp = ls.load(lr);
        db.openConnection();
        PersonDAO pd = new PersonDAO(db.getConnection());
        UserDAO ud = new UserDAO(db.getConnection());
        EventDAO ed = new EventDAO(db.getConnection());
        assertNotNull(pd.find(bestPerson.getPersonID()));
        assertNotNull(ud.find(worstUser.getUsername()));
        assertNotNull(ed.find(worstEvent.getEventID()));
        assertEquals(true, Lresp.isSuccess());
    }

    @Test
    public void getPersonObject_Pass() throws DataAccessException, FileNotFoundException {
        PersonDAO pd=new PersonDAO(db.getConnection());
        pd.insert(bestPerson);
        UserDAO ud= new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad =new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        Authtoken token = ad.find(bestUser.getUsername());
        db.closeConnection(true);
        Person_Request pr= new Person_Request(bestPerson.getPersonID(),token.getAuthtoken());
        Person_Service ps= new Person_Service();
        db.openConnection();
        Person_Responce Pres = ps.getPersonObject(pr);
        assertEquals(Pres.getAssociatedUsername(),bestUser.getUsername());
        assertEquals(Pres.getPersonID(),bestPerson.getPersonID());
    }

    @Test
    public void getPersonObject_fail() throws DataAccessException, FileNotFoundException {
        PersonDAO pd=new PersonDAO(db.getConnection());
        pd.insert(bestPerson);
        UserDAO ud= new UserDAO(db.getConnection());
        ud.insert(bestUser);
        AuthtokenDAO ad =new AuthtokenDAO(db.getConnection());
        Authtoken tok = new Authtoken("12345", bestUser.getUsername());
        ad.insert(tok);
        Authtoken token = ad.find(bestUser.getUsername());
        db.closeConnection(true);
        Person_Request pr= new Person_Request(worstPerson.getPersonID(),token.getAuthtoken());
        Person_Service ps= new Person_Service();
        db.openConnection();
        Person_Responce Pres = ps.getPersonObject(pr);
        assertEquals(null, Pres.getAssociatedUsername());
        assertEquals(false, Pres.getSuccess());
    }


    @Test
    public void Clear_Service_Pass() throws  DataAccessException{
        db.closeConnection(true);
        Register_Request Rreq = new Register_Request("pr1ce12", "password", "gmail",
                "Caleb", "Price", "m");
        Register_Service RS = new Register_Service();
        RS.register(Rreq);
        UserDAO ud = new UserDAO(db.openConnection());
        assertNotNull(ud.find("pr1ce12"));
        db.closeConnection(false);
        Clear_Service clear = new Clear_Service();
        clear.clear();
        ud = new UserDAO(db.openConnection());
        assertNull(ud.find("pr1ce12"));
        //assertThrows(DataAccessException.class, () ->  uDao.find("pr1ce12"));
    }

    @Test
    public void Clear_Service_Pass2() throws  DataAccessException{
        db.closeConnection(true);
        Register_Request Rreq = new Register_Request("pr1ce12", "password", "gmail",
                "Caleb", "Price", "m");
        Register_Request Rreq2 = new Register_Request("dallin", "password", "gmail",
                "Caleb", "Price", "m");
        Register_Service RS = new Register_Service();
        RS.register(Rreq);
        UserDAO ud = new UserDAO(db.openConnection());
        assertNotNull(ud.find("pr1ce12"));
        db.closeConnection(false);
        Clear_Service clear = new Clear_Service();
        clear.clear();
        ud = new UserDAO(db.openConnection());
        assertNull(ud.find("dallin"));
    }


}