package dentistassistant.core.controller;

import dentistassistant.core.model.MainApplication;
import dentistassistant.ejb.entities.Account;
import dentistassistant.ejb.entities.Employee;
import dentistassistant.ejb.interfaces.IAccountManagerRemote;
import dentistassistant.ejb.interfaces.IEmployeeManagerRemote;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.util.List;

/**
 * Main controller.
 *
 * @author pietia
 */
public class MainAppController {

    private static final Logger LOG = Logger.getLogger(MainAppController.class);

    /**
     * Tries to log in person and returns object represents logged
     *
     * @param account Account object with login and password encoded using MD5 algorithm
     * @param macs    List of MAC address
     * @return Employee object or null if authentication failed
     * @throws NamingException
     */
    public Employee loginTry(final Account account, final List<String> macs)
            throws NamingException {

        if (account == null)
            throw new IllegalArgumentException("account mustn't be null");
        if (macs == null || macs.isEmpty())
            throw new IllegalArgumentException("list of MACs mustn't be null or empty");

        final IAccountManagerRemote manager = (IAccountManagerRemote) getPersistenceContext(IAccountManagerRemote.class);
        Employee emp = manager.loginTry(account, macs);
        if (emp != null) {
            LOG.debug("Employee logged:" + emp.getName() + emp.getSurname());
        }
        return emp;
    }

    /**
     * Returns number of employees in database.
     * Zero means that's probably first start of application (initial session)
     * and account with administrative privileges should be created.
     * Default login/password for initial session is admin/adminadmin.
     *
     * @return number of employees in database
     * @throws NamingException
     */
    public Long countEmployees() throws NamingException {
        IEmployeeManagerRemote manager = (IEmployeeManagerRemote) getPersistenceContext(IEmployeeManagerRemote.class);
        Long count = manager.countEmployees();
        LOG.debug("Employees in database:" + count);
        return count;
    }

    /**
     * Helper method for getting persistence context
     * from MainApplication (singleton class)
     *
     * @param klazz type of EJB remote interface
     * @return EJB session bean implementing EJB remote interface that
     *         name is passed as a parameter
     * @throws NamingException
     */
    protected Object getPersistenceContext(final Class<?> klazz)
            throws NamingException {
        // name passed by this method is server specific so it may differ
        // on JBOSS/GF/etc
        return MainApplication.getInstance().getPersistenceContext(klazz.toString());
    }

}
