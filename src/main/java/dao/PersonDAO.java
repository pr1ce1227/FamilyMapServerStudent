package dao;

import java.sql.Connection;
import java.util.HashSet;
import model.Person;
import java.sql.*;
import java.util.Set;

import model.User;

/**
 * Interface for the person objects and the data base
 */
public class PersonDAO {
    /**
     * connectioin object to the database
     */
    private final Connection conn;

    /**
     * Construct DAO by connecting to the database
     * @param conn
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Person person) throws DataAccessException {

        // build person string input out of person object
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Give actual values to the string indexes, helps with injection atacks
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            // Execute the update
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;

        // Find person based on person ID
        String sql = "SELECT * FROM person WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            // if row found build a person object, else return null
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    public void clear() throws DataAccessException {
        // Clear personn table sql statement
        String sql = "DELETE FROM person";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    public int delete(User user) throws DataAccessException {

        // Delete person based on username
        String sql = "DELETE FROM person WHERE associatedUsername = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, user.getUsername());
            // execute the delete statement
            return stmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void update(Person p) throws DataAccessException {

        // Update person based on person object found at personID
        String sql = "UPDATE person SET firstName = ?, lastName = ?, associatedUsername = ?, gender = ?, fatherID = ?, motherID = ?, spouseID = ? WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, p.getFirstName());
            stmt.setString(2, p.getLastName());
            stmt.setString(3, p.getAssociatedUsername());
            stmt.setString(4, p.getGender());
            stmt.setString(5, p.getFatherID());
            stmt.setString(6, p.getMotherID());
            stmt.setString(7, p.getSpouseID());
            stmt.setString(8, p.getPersonID());

            // execute the delete statement
            int num = stmt.executeUpdate();
            if(num == 0){
                throw new DataAccessException("Person not found for update");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * list all of the people currently in the data base
     * @return a list of people objects
     */
    public Person[] getPeople(String username) throws DataAccessException{
        Set<Person> persons = new HashSet<>();

        Person person = null;
        ResultSet rs;

        // get all people with the given username association
        String sql = "SELECT * FROM person WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            // Create an object for each person and add to person Set
            while(rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                persons.add(person);
            }

            // Check if anyone was added, then add to an array to help with json deserialization
            if(persons.isEmpty()){
                throw new DataAccessException("Person not found");
            }
            else{
                Person[] people = new Person[persons.size()];
                int i = 0;
                for(Person p : persons){
                    people[i] = p;
                    ++i;
                }
                return  people;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
