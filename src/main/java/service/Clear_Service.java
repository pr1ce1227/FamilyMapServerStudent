package service;

import dao.*;
import request_result.Clear_Responce;

public class Clear_Service {

    public Clear_Responce clear(){
        Clear_Responce cr = null;
        Database db = new Database();
        try {
            db.openConnection();
            UserDAO ud = new UserDAO(db.getConnection());
            ud.clear();
            PersonDAO pd = new PersonDAO(db.getConnection());
            pd.clear();
            EventDAO ed = new EventDAO(db.getConnection());
            ed.clear();
            AuthtokenDAO ad = new AuthtokenDAO(db.getConnection());
            ad.clear();
            cr = new Clear_Responce("Clear succeeded.", true);
            db.closeConnection(true);
        }
        catch (DataAccessException da){
            cr = new Clear_Responce("Error: Failed to clear", false);
            db.closeConnection(false);
        }
        return  cr;
    }
}
