package dentistassistant.core.model;

import dentistassistant.ejb.entities.Employee;
import dentistassistant.ejb.implementations.EmployeeManagerBean;
import dentistassistant.ejb.interfaces.IEmployeeManagerRemote;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import javax.naming.NamingException;


public class MainApplicationTest {

    private static final String DEFAULT_ADDRESS = "127.0.0.1";

    @Test
    public void singletonInstanceShouldNotBeNullObjectAtFirstInvocation() {
        assertNotNull(MainApplication.getInstance());
    }

    @Test
    public void singletonInstanceShouldNotBeNullObjectAtAnyNextInvocation() {
        assertNotNull(MainApplication.getInstance());
    }

    @Ignore(value = "EJB container needed. No go with mocks :(")
    @Test
    public void shouldReturnImplementationOfCorrectEJBRemoteInterface() throws NamingException {
        IEmployeeManagerRemote manager = (IEmployeeManagerRemote)
                MainApplication.getInstance().getPersistenceContext("jndi:/IEmployeeManager/remote");
        assertTrue(manager instanceof EmployeeManagerBean);
    }

    @Test(expected = NamingException.class)
    public void shouldRaiseExceptionWithWrongJNDIName() throws NamingException {
        MainApplication.getInstance().getPersistenceContext("jmdi:/alamakota/remote");
    }

    @Ignore(value = "Needs Eclipse RCP env.")
    @Test
    public void shouldUseDefaultAddress() {
        assertTrue(MainApplication.getInstance().getServerAddress().compareTo(DEFAULT_ADDRESS) == 0);
    }

    @Ignore(value = "Needs Eclipse RCP env.")
    @Test
    public void shouldSetAndReturnThisSameAddress() {
        final String ip = "192.168.1.1";
        MainApplication.getInstance().setServerAddress(ip);
        assertEquals(MainApplication.getInstance().getServerAddress(), ip);
    }

    @Test
    public void shouldSetAndReturnCorrectInformationAboutInitialSession() {
        MainApplication.getInstance().setInitialSession(true);
        assertTrue(MainApplication.getInstance().isInitialSession());
    }

    @Test
    public void shouldSetAndReturnCorrectAccount() {
        Employee employee = new Employee();
        MainApplication.getInstance().setLogged(employee);
        //Yes, we check reference
        assertTrue(employee == MainApplication.getInstance().getLogged());
    }

    @Test
    public void shouldSetAndReturnAccountWithCorrectId() {
        Employee employee = new Employee();
        employee.setId(10);
        MainApplication.getInstance().setLogged(employee);
        assertTrue(employee.getId().equals(MainApplication.getInstance().getLogged().getId()));
    }


}
