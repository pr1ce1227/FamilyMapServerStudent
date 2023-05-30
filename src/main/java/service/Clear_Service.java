package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import dao.UserDAO;
import request_result.Clear_Responce;

public class Clear_Service {

    public Clear_Responce clear(){
        Clear_Responce cr = null;
        Database db = new Database();
        try {

            UserDAO ud = new UserDAO(db.openConnection());
            ud.clear();
            PersonDAO pd = new PersonDAO(db.getConnection());
            pd.clear();
            cr = new Clear_Responce(null, true);
            db.closeConnection(true);
        }
        catch (DataAccessException da){
            cr = new Clear_Responce("Failed to clear", false);
            db.closeConnection(false);
        }
        return  cr;
    }
}
