package dentistassistant.core.util;

import java.util.Properties;

/**
 * Interface must be implemented in class providing strategy
 * for getting server specific configuration used for getting
 * InitialContext
 *
 * @author pietia
 */
public interface IJNDIServerStrategy {
    /**
     * Returns Properties object contains server specific configuration
     * for getting InitialContext
     *
     * @return Properties object
     */
    public Properties cofigureServerConnection();
}
