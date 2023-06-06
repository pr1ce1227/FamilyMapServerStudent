package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    /**
     * connection to the database stored in memory
     */
    private Connection conn;

    /**
     * open the connection to the database
     * @return
     * @throws DataAccessException
     */
    public Connection openConnection() throws DataAccessException {
        try {
            // The Structure for this Connection is driver:language:path
            // The path assumes you start in the root of your project unless given a full file path
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * return object that is connected to the database
     * @return
     * @throws DataAccessException
     */
    public Connection getConnection() throws DataAccessException {
        if (conn == null) {
            return openConnection();
        }
        else {
            return conn;
        }
    }

    /**
     * close the database connection
     * @param commit if true the changes will be commited, if false the database will roll back to previous state
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                // This will commit the changes to the database
                conn.commit();
            }
            else {
                // If we find out something went wrong, pass a false into closeConnection and this
                // will rollback any changes we made during this connection
                conn.rollback();
            }
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

