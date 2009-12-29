package dentistassistant.core.util;


import dentistassistant.core.exceptions.InvalidJPAAttributeException;
import dentistassistant.ejb.entities.AbstractBaseEntity;

/**
 * Interface must be implemented in JPA entity validator class
 *
 * @author pietia
 */
public interface IEntityValidator {
    /**
     * Validates JPA entity
     *
     * @param entity
     * @throws dentistassistant.core.exceptions.InvalidJPAAttributeException
     *          if validation fails
     */
    public void validateEntity(AbstractBaseEntity entity) throws InvalidJPAAttributeException;
}
