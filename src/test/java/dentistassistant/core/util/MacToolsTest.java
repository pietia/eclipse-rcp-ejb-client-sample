package dentistassistant.core.util;

import org.junit.Ignore;
import org.junit.Test;

import java.net.SocketException;


public class MacToolsTest {

    @Ignore(value = "Platform independent test or env. with no ip assigned interfaces needed ")
    @Test(expected = SocketException.class)
    public void shouldRaiseExceptionWhenThereIsNoOneInterfaceWithAssignedIp() throws SocketException {
        MacTools.getInterfaces();
    }
}
