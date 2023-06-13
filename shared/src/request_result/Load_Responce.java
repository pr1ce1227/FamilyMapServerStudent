package request_result;

/**
 * Responce of wether or not the load request was accomplished
 */
public class Load_Responce {
    /**
     * Either a success or failur message
     */
    private String message;
    /**
     * boolean of wether or not the load was a success
     */
    private boolean success;

    /**
     * initialize the responce variables
     * @param message
     * @param success
     */
    public Load_Responce(String message, boolean success){
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
