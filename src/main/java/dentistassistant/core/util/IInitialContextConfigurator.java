package dentistassistant.core.util;

import javax.naming.InitialContext;
import java.util.Properties;

/**
 * Interface must be implemented in class
 *
 * @author pietia
 */
public interface IInitialContextConfigurator {
    /**
     * Returns Properties object with configuration used for getting InitialContext
     *
     * @return Properties object
     * @see InitialContext
     */
    public Properties configure();
}
