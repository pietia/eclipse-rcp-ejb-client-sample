package dentistassistant.core.util;

import dentistassistant.core.exceptions.InvalidJPAAttributeException;
import dentistassistant.ejb.entities.AbstractBaseEntity;
import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;


/**
 * Class used to validate JPA entities using Hibernate Validator.
 *
 * @author pietia
 */
public class HibernateEntityValidator implements IEntityValidator {

    private static final Logger LOG = Logger.getLogger(HibernateEntityValidator.class);

    /**
     * Validates JPA entity using Hibernate Entity.
     *
     * @param entity
     * @throws dentistassistant.core.exceptions.InvalidJPAAttributeException
     *          if validation fails
     */
    @SuppressWarnings(value = "unchecked")
    public void validateEntity(final AbstractBaseEntity entity) throws InvalidJPAAttributeException {

        final ClassValidator validator = new ClassValidator(entity.getClass());
        InvalidValue[] invalidValues = null;

        try {
            invalidValues = validator.getInvalidValues(entity);
        } catch (Exception ex) {
            // LOG.fatal("Exception in Validator:" + ex.getMessage());
            throw new RuntimeException(ex);
        }

        String message = buildMessageString(invalidValues);

        if (invalidValues.length > 0) {
            // LOG.debug("Invalid JPA Attribute: " + message);
            throw new InvalidJPAAttributeException(message);
        }
    }

    /**
     * Helper method for building error message.
     *
     * @param invalidValues
     * @return String object contains error message
     */
    private static String buildMessageString(InvalidValue[] invalidValues) {
        StringBuilder strb = new StringBuilder();
        for (InvalidValue iv : invalidValues) {
            strb.append("\n").append(iv.getMessage());
        }
        return strb.toString();
    }

}
