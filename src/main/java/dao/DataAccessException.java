package dao;

/**
 * Exception for when something goes wrong when accessing the database
 */
public class DataAccessException extends Exception {
    /**
     * return an error message
     * @param message
     */
    DataAccessException(String message) {
        super(message);
    }
}
