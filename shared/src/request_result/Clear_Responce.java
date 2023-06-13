package request_result;

/**
 * Responce object used for constructing the json file responce from attempting to clear the database
 */
public class Clear_Responce{
    /**
     * Message describing wether the clear was successful
     */
    private String message;
    /**
     * true if succesful, false if wasn't able to clear everything
     */
    private boolean success;

    /**
     * Built with a message and success report
     * @param message
     * @param success
     */
    public Clear_Responce(String message, boolean success){
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
