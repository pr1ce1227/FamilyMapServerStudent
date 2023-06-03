package request_result;

import service.Fill_Service;

/**
 * Was the databse fill succesfully responce
 */
public class Fill_Responce {
    /**
     * Message explaining success or failure
     */
    private String message;
    /**
     * True or false wether the fill was succesfull
     */
    private boolean success;

    /**
     * Initialize the message and succes boolean
     */
    public Fill_Responce(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
