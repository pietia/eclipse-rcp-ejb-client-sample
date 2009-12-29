package dentistassistant.core.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.Properties;

public class JNDIInitialContextConfiguratorTest {


    @Test
    public void shouldReturnPropertiesObjectNotNull() {
        IJNDIServerStrategy strategy = new JBossJNDIServerStrategy();
        IInitialContextConfigurator initialContextConf = new JNDIInitialContextConfigurator(strategy);
        Properties properties = initialContextConf.configure();
        assertNotNull(properties);
        assertTrue(properties.size() > 0);
    }

}
