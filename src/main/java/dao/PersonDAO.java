package dao;

import java.sql.Connection;
import java.util.List;

import model.Event;
import model.Person;
import java.sql.*;

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
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherId());
            stmt.setString(7, person.getMotherId());
            stmt.setString(8, person.getSpouseId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }

    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    public void delete(Person p) throws DataAccessException {
        String sql = "DELETE FROM person WHERE personID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, p.getPersonID());
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Person p) throws DataAccessException {
        String sql = "UPDATE person SET firstName = ?, lastName = ?, associatedUsername = ?, gender = ?, fatherID = ?, motherID = ?, spouseID = ? WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, p.getFirstName());
            stmt.setString(2, p.getLastName());
            stmt.setString(3, p.getAssociatedUsername());
            stmt.setString(4, p.getGender());
            stmt.setString(5, p.getFatherId());
            stmt.setString(6, p.getMotherId());
            stmt.setString(7, p.getSpouseId());
            stmt.setString(8, p.getPersonID());
            // execute the delete statement
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * list all of the people currently in the data base
     * @return a list of people objects
     */
    public List<Person> getPeople(){return  null;}


}
