package dao;

import model.Authtoken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public AuthtokenDAO(Connection conn) {this.conn = conn;}

    /**
     * Used to insert tokens into the database one row at a time
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(Authtoken authtoken) throws DataAccessException{

        // Insert row int Authtoken table
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";

        // Load in the actual values and update the database
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
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

        // Find authtoken based on username
        String sql = "SELECT * FROM Authtoken WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Update queery with values then execute
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            // Grab the value if one exists
            if (rs.next()) {
                token = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            }
            else {
                return null;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    public String findUsername(String token) throws DataAccessException{
        ResultSet rs;
        Authtoken at;

        // Find username based on authtoken
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";

        // Update with values and execute the queery
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();

            // If value found return else return null
            if (rs.next()) {
                at = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return at.getUsername();
            }
            else {
                return null;
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * clear all of the tokens in the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {

        // Clear all the authtoken table
        String sql = "DELETE FROM Authtoken";

        // Execute the clear
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtoken table");
        }
    }

    /**
     * delet a token in the database base on the user
     * @param user
     * @throws DataAccessException
     */
    public int delete(String user) throws DataAccessException{

        // Delete authtoken based on username
        String sql = "DELETE FROM Authtoken WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, user);
            // execute the delete statement
            int num = stmt.executeUpdate();
            if(num == 0){
                throw new DataAccessException("User not found to delete authtoken");
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    public void update(Authtoken token) throws DataAccessException {

        // Update authoken row based on username
        String sql = "UPDATE Authtoken SET authtoken = ? WHERE username = ?";

        // Update entire row based on the new object
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, token.getAuthtoken());
            stmt.setString(2, token.getUsername());
            // execute the delete statement
            int num = stmt.executeUpdate();

            if(num == 0){
                throw new DataAccessException("failed to update authtoken");
            }
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
