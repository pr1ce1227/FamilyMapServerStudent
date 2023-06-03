package dao;

import model.Authtoken;
import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for the Authtoken objects and the sql database
 */
public class AuthtokenDAO {
    /**
     * database connection variable
     */
    private final Connection conn;

    /**
     * Constructed by establishing a cnnectiong to the SQL database
     * @param conn the connection variable to the database
     */
    public AuthtokenDAO(Connection conn) {this.conn = conn;
    }

    /**
     * Used to insert tokens into the database one row at a time
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(Authtoken authtoken) throws DataAccessException{
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * check to see if token is already in database
     * @param userName
     * @return
     * @throws DataAccessException
     */
    public Authtoken find(String userName) throws DataAccessException{
        Authtoken token;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    public String findUsername(String token) throws DataAccessException{
        ResultSet rs;
        Authtoken at;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                at = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return at.getUsername();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * list all of the tokens currently in the database
     * @return
     */
    public List<Authtoken> getTokens(){return  null;}

    /**
     * clear all of the tokens in the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtoken table");
        }
    }

    /**
     * delet a token in the database base on the user
     * @param user
     * @throws DataAccessException
     */
    public void delete(String user) throws DataAccessException{

        String sql = "DELETE FROM Authtoken WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, user);
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Authtoken token) throws DataAccessException {
        String sql = "UPDATE Authtoken SET authtoken = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, token.getAuthtoken());
            stmt.setString(2, token.getUsername());
            // execute the delete statement
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
