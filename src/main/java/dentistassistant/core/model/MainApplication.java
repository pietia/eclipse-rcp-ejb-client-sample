package dentistassistant.core.model;

import dentistassistant.core.ActivatorCore;
import dentistassistant.core.util.JBossJNDIServerStrategy;
import dentistassistant.core.util.JNDIInitialContextConfigurator;
import dentistassistant.ejb.entities.Employee;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton. Main class containing important data (logged user, server address etc)
 *
 * @author pietia
 */
final public class MainApplication {

    private static final Logger LOG;
    private static final Properties INITIAL_CONTEXT_CONFIGURATION;
    private static final String LOG4J_PROPERTIES_FILE = "log4j.properties";
    private static final String IP_LOCALHOST = "127.0.0.1";
    // Name of RCP's property where address is stored
    private static final String PREF_NAME_IP_ADDRESS = "ipAddress";
    private Employee employeeLogged = null;
    private String ipAddress = null;
    private boolean initialSession = false;

    /**
     * Singleton's instance
     */
    private static final MainApplication INSTANCE;

    /**
     * Static initializer where Singleton's instance is created and Log4J is
     * configured
     */
    static {

        INSTANCE = new MainApplication();

        INITIAL_CONTEXT_CONFIGURATION = configureInitialContext();

        configureLog4jLogger();
        LOG = Logger.getLogger(MainApplication.class);
    }

    /**
     * Private application constructor prevents from instantiating this class
     * (Singleton).
     */
    private MainApplication() {
    }

    /**
     * Singleton's "getInstance()" method.
     *
     * @return MainApplication object
     */
    public static synchronized MainApplication getInstance() {
        return INSTANCE;
    }

    /**
     * Returns true when it's initial session. Initial session occurs when there
     * is no data in database.
     *
     * @return true if it's initial session
     */
    public boolean isInitialSession() {
        return initialSession;
    }

    /**
     * Sets if it's initial session or not. Initial session occurs when there is
     * no data in database.
     *
     * @param initialSession
     */
    public void setInitialSession(boolean initialSession) {
        this.initialSession = initialSession;
    }

    /**
     * Method returns true if somebody is logged into system
     *
     * @return true if somebody is logged
     */
    public boolean isLogged() {
        return employeeLogged != null;
    }

    /**
     * Sets selected employee
     *
     * @param account
     */
    public void setLogged(Employee account) {
        employeeLogged = account;
    }

    /**
     * Gets logged account
     *
     * @return logged employee
     */
    public Employee getLogged() {
        return employeeLogged;
    }

    /**
     * Returns EJB remote interface for given name.
     * Preffered way is to use MainAppController.
     *
     * @param remoteManager name of EJB remote interface
     * @return
     * @throws NamingException if there was problems with getting persistence context
     * @see dentistassistant.core.controller.MainAppController
     */
    public Object getPersistenceContext(final String remoteManager) throws NamingException {

        Object ejbRemoteInterface = null;
        try {

            if (ipAddress == null || ipAddress.compareTo("") == 0) {
                ipAddress = IP_LOCALHOST;
            }

            INITIAL_CONTEXT_CONFIGURATION.put(JBossJNDIServerStrategy.FACTORY_INITIAL, constructServerAddress());

            LOG.debug("IP used for getting persistence context: " + ipAddress);
            final Context con = new InitialContext(INITIAL_CONTEXT_CONFIGURATION);

            LOG.debug("JNDI name looked up: " + remoteManager);
            ejbRemoteInterface = con.lookup(remoteManager);

        } catch (NamingException ex) {
            LOG.warn("Problems with getting persistence context:" + ex.getMessage());
            // ex.printStackTrace();
            // bubble it up!  
            throw ex;
        }
        return ejbRemoteInterface;
    }

    /**
     * Helper method that constructs server address
     * from ipAddress and DEFAULT_PORT (1099)
     *
     * @return
     */
    protected String constructServerAddress() {
        return ipAddress + ":" + JBossJNDIServerStrategy.DEFAULT_PORT;
    }

    /**
     * Sets server's address
     *
     * @param ipAddress
     */
    public void setServerAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        setRCPPreference(PREF_NAME_IP_ADDRESS, this.ipAddress);
    }

    /**
     * Returns server's address
     *
     * @return server's address
     */
    public String getServerAddress() {
        ipAddress = getRCPPreference(PREF_NAME_IP_ADDRESS);
        if (ipAddress == null || ipAddress.compareTo("") == 0) {
            ipAddress = IP_LOCALHOST;
            setServerAddress(IP_LOCALHOST);
        }
        return ipAddress;
    }

    /**
     * Helper method that returns preferences stored in RCP workbench store
     *
     * @param preferenceName
     * @return preference value or null if there is no such preference
     */
    private static String getRCPPreference(String preferenceName) {
        return ActivatorCore.getDefault().getPreferenceStore().getString(preferenceName);
    }

    /**
     * Helper method that sets preference RCP workbench store
     *
     * @param preferenceName
     * @param preferenceValue
     */
    private static void setRCPPreference(String preferenceName, String preferenceValue) {
        ActivatorCore.getDefault().getPreferenceStore().setValue(preferenceName, preferenceValue);
    }

    /**
     * Returns configuration used for getting InitialContext object
     *
     * @return Properties object
     */
    private static Properties configureInitialContext() {
        return new JNDIInitialContextConfigurator(new JBossJNDIServerStrategy()).configure();
    }


    /**
     * Returns Properties object contains default configuration for log4j
     *
     * @return Properties object
     */
    private static Properties configureDefaultLog4jLogger() {

        final Properties log4jProperties = new Properties();
        log4jProperties.setProperty("log4j.rootLogger", "info, stdout, R");
        log4jProperties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        log4jProperties.setProperty("log4j.appender.stdout.layout",
                "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.stdout.layout.ConversionPattern",
                "%5p [%t] (%F:%L) - %m%n");
        log4jProperties.setProperty("log4j.appender.R", "org.apache.log4j.RollingFileAppender");
        log4jProperties.setProperty("log4j.appender.R.File", "pomocnik.log");
        log4jProperties.setProperty("log4j.appender.R.MaxFileSize", "100KB");
        log4jProperties.setProperty("log4j.appender.R.MaxBackupIndex", "1");
        log4jProperties.setProperty("log4j.appender.R.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.R.layout.ConversionPattern",
                "%5p [%t] (%F:%L) - %m%n");

        return log4jProperties;
    }

    /**
     * Configures log4j logger. If properties file(log4j configuration) is not found uses
     * default configuration (hardcoded).
     */
    private static void configureLog4jLogger() {

        Properties log4jProperties = new Properties();
        InputStream inStr = null;

        try {
            inStr = MainApplication.class.getResourceAsStream(LOG4J_PROPERTIES_FILE);
            if (inStr == null) {
                // LOG4J_PROPERTIES_FILE not found
                log4jProperties = configureDefaultLog4jLogger();
                PropertyConfigurator.configure(log4jProperties);
                System.err.println(" INFO Using default log4j config (no log4j.properties - properties hardcoded)");
            } else {
                log4jProperties.load(inStr);
                PropertyConfigurator.configure(log4jProperties);
                System.err.println(" INFO Using log4j config from log4j.properties");
            }
        } catch (IOException ex) {
            // loading LOG4J_PROPERTIES_FILE failed
            log4jProperties = configureDefaultLog4jLogger();
            PropertyConfigurator.configure(log4jProperties);
            System.err.println(" INFO Using default log4j config (no log4j.properties - properties hardcoded)");
        } finally {
            if (inStr != null) {
                try {
                    inStr.close();
                } catch (IOException ex) {
                    LOG.warn(ex);
                    //ex.printStackTrace();
                }
            }
        }
    }
}
