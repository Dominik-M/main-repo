package server;

/**
 *
 * @author Dundun
 */
public class Constants {

    /**
     * Default Port number
     */
    public static final int PORT = 52056;

    /**
     * Command prefixes
     */
    public static final String REQUEST = "#R", ACK = "#A", ERROR = "#E", COMMAND = "#C";

    /**
     * Request codes
     */
    public static final int REQUEST_NAME = 1, REQUEST_QUIT = 2, REQUEST_PRINT = 3;

    /**
     * Error codes
     */
    public static final int ERROR_UNKNOWN_COMMAND = 1;

    /**
     * Command codes
     */
    public static final int C_USERLIST = 1;
}
