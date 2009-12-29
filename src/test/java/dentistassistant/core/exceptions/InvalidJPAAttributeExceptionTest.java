package dentistassistant.core.exceptions;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class InvalidJPAAttributeExceptionTest {

    @Test
    public void shouldBeSubclassOfCheckedException() {
        Exception ex = new InvalidJPAAttributeException();
        assertTrue(!(ex instanceof RuntimeException));
    }

}
