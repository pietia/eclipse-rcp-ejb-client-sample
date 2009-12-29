package dentistassistant.core.controller;

import dentistassistant.ejb.entities.Account;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainAppControllerTest {

    private static final List<String> MAC_ADDRESSES = Arrays.asList("001cbfaf4109");
    private Account account;
    private static MainAppController controller;

    @BeforeClass
    public static void init() {
        controller = new MainAppController();
    }

    @Before
    public void beforeEach() {
        account = new Account();
        account.setId(10);
        account.setLogin("Ala");
        account.setLogin("Makota");
    }

    //To be honest - should be moved to upper level
    @Test(expected = NamingException.class)
    public void shouldRaiseExceptionWhenServerIsDownWithLoging() throws NamingException {
        controller.loginTry(account, MAC_ADDRESSES);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenListOfMacsIsNull() throws IllegalArgumentException, NamingException {
        controller.loginTry(account, null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenListOfMacsIsEmpty() throws IllegalArgumentException, NamingException {
        List<String> emptyList = Collections.emptyList();
        controller.loginTry(account, emptyList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenAccountObjectIsNull() throws IllegalArgumentException, NamingException {
        controller.loginTry(null, MAC_ADDRESSES);
    }

    //To be honest - this should be moved to upper level
    @Test(expected = NamingException.class)
    public void shouldRaiseExceptionWhenServerIsDownWithCountingEmplyees() throws NamingException {
        controller.countEmployees();
    }

    // In other plugin, where "controller" offer saving empleyees
    @Ignore(value = "Moved to higher level")
    @Test
    public void shouldReturnCorrectNumberOfEmplyees() {
    }

    // In other plugin, where "controller" offer saving empleyees
    @Ignore(value = "Moved to higher level")
    @Test
    public void shouldReturnEmplyeeIfAuthenticationSucceed() {
    }

    // In other plugin, where "controller" offer saving empleyees
    @Ignore(value = "Moved to higher level")
    @Test
    public void shouldReturnNullObjectIfAuthenticationFailed() {
    }


}
