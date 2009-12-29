package dentistassistant.core.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.Properties;

public class JBossJNDIServerStrategyTest {

    @Test
    public void shouldReturnPropertiesObjectNotNull() {
        IJNDIServerStrategy strategy = new JBossJNDIServerStrategy();
        Properties properties = strategy.cofigureServerConnection();
        assertNotNull(properties);
        assertTrue(properties.size() > 0);
    }
}
