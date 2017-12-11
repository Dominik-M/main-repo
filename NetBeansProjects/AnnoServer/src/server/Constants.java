package server;

/**
 *
 * @author Dundun
 */
public class Constants {
    /*
     * Command prefixes
     */
    public static final String REQUEST = "#R", ACK = "#A", ERROR = "#E";
    
    /**
     * Request codes
     */
    public static final int REQUEST_NAME = 1,REQUEST_QUIT = 2, REQUEST_PRINT = 3;
    
    /*
     * Error codes
     */
    public static final int ERROR_UNKNOWN_COMMAND = 1;
}
