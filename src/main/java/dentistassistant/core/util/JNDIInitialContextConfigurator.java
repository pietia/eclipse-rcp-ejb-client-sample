package dentistassistant.core.util;

import java.util.Properties;

/**
 * Class provides configuration method {@link JNDIInitialContextConfigurator#configure()}
 * that returns configuration specific for application server.
 *
 * @author pietia
 */
final public class JNDIInitialContextConfigurator implements IInitialContextConfigurator {

    /**
     * Strategy used for application server (i.e. JBoss,GF,Geronimo)
     */
    private final IJNDIServerStrategy strategy;


    /**
     * Parametrized constructor applying strategy specific for application
     * server
     *
     * @param strategy Strategy object that will used
     */
    public JNDIInitialContextConfigurator(final IJNDIServerStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @see dentistassistant.core.util.IInitialContextConfigurator#configure()
     * @see dentistassistant.core.util.IJNDIServerStrategy#cofigureServerConnection()
     */
    @Override
    public Properties configure() {
        return strategy.cofigureServerConnection();
    }
}
