package dentistassistant.core.exceptions;

/**
 * Exception that is thrown when validation fails
 *
 * @author pietia
 */
public class InvalidJPAAttributeException extends Exception {

    /**
     * Default constructor.
     */
    public InvalidJPAAttributeException() {
        super();
    }

    /**
     * Constructor with ability to pass into exception message.
     *
     * @param msg message
     */
    public InvalidJPAAttributeException(String msg) {
        super(msg);
    }

}
