package service;

import dao.*;
import request_result.Clear_Responce;

public class Clear_Service {

    public Clear_Responce clear(){
        Clear_Responce clearResponce = null;
        Database database = new Database();
        try {
            // Open Dao for each table and clear
            database.openConnection();
            UserDAO ud = new UserDAO(database.getConnection());
            ud.clear();
            PersonDAO personDAO = new PersonDAO(database.getConnection());
            personDAO.clear();
            EventDAO eventDAO = new EventDAO(database.getConnection());
            eventDAO.clear();
            AuthtokenDAO authtokenDAO = new AuthtokenDAO(database.getConnection());
            authtokenDAO.clear();

            // Report success and close the connection
            clearResponce = new Clear_Responce("Clear succeeded.", true);
            database.closeConnection(true);
        }
        catch (DataAccessException da){
            clearResponce = new Clear_Responce("Error: Failed to clear", false);
            // Rollback the changes
            database.closeConnection(false);
        }
        return  clearResponce;
    }
}
