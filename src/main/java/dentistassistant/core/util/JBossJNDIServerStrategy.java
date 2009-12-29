package dentistassistant.core.util;

import java.util.Properties;

/**
 * Class provides strategy for getting JBOSS specific configuration
 * used for getting InitialContext
 *
 * @author pietia
 */
public class JBossJNDIServerStrategy implements IJNDIServerStrategy {

    public static final String PROVIDER_URL = "java.naming.provider.url";
    public static final String PROVIDER_URL_VAL = "localhost:1099";
    public static final String FACTORY_URL_PKGS = "java.naming.factory.url.pkgs";
    public static final String FACTORY_URL_PKGS_VAL = "org.jboss.naming:org.jnp.interfaces";
    public static final String FACTORY_INITIAL = "java.naming.factory.initial";
    public static final String FACTORY_INITIAL_VAL = "org.jnp.interfaces.NamingContextFactory";

    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_PORT = "1099";


    /**
     * Default configuration for JBOSS 5.x.x
     */
    protected static final Properties CONFIGURATION;

    static {
        CONFIGURATION = new Properties();
        CONFIGURATION.put(FACTORY_INITIAL, FACTORY_INITIAL_VAL);
        CONFIGURATION.put(FACTORY_URL_PKGS, FACTORY_URL_PKGS_VAL);
        CONFIGURATION.put(PROVIDER_URL, PROVIDER_URL_VAL);
    }

    @Override
    public Properties cofigureServerConnection() {
        return CONFIGURATION;
    }

}
