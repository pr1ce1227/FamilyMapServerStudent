package dao;

import model.User;

import java.sql.Connection;
import java.util.List;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * interface for the users and the database
 */
public class UserDAO {

    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(User user) throws DataAccessException {

        // Insert user into user table based on user object
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Insert actual values in the string
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonId());
            // Execute the insert
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;

        // Find user based on unique  username
        String sql = "SELECT * FROM user WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            // If user found build a user object and return
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
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

    public void clear() throws DataAccessException {
        // Clear all users from user table
        String sql = "DELETE FROM user";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

    public int delete(User us) throws DataAccessException {

        // Delete user base on username
        String sql = "DELETE FROM user WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, us.getUsername());
            // execute the delete statement
            int num = stmt.executeUpdate();

            if(num == 0){
                throw new DataAccessException("User doesn't exist to delte");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("User doesn't exist to delte");
        }
        return 1;
    }
}
