package dentistassistant.core.exceptions;

/**
 * Exception that is thrown when JPA entity is not unique
 *
 * @author pietia
 */
public class NotUniqueJPAEntityException extends Exception {

    /**
     * Default constructor.
     */
    public NotUniqueJPAEntityException() {
        super();
    }

    /**
     * Constructor with ability to pass into exception message.
     *
     * @param msg message
     */
    public NotUniqueJPAEntityException(String msg) {
        super(msg);
    }

}
