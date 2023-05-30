package dao;

import model.Authtoken;

import java.sql.Connection;
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
    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Used to insert tokens into the database one row at a time
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(Authtoken authtoken) throws DataAccessException{}

    /**
     * check to see if token is already in database
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Authtoken find(String personID) throws DataAccessException{return null;}

    /**
     * list all of the tokens currently in the database
     * @return
     */
    public List<Authtoken> getTokens(){return  null;}

    /**
     * clear all of the tokens in the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {}

    /**
     * delet a token in the database base on the user
     * @param user
     * @throws DataAccessException
     */
    public void delete(String user) throws DataAccessException{}
}
