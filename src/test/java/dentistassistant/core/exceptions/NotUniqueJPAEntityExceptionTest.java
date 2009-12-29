package dentistassistant.core.exceptions;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class NotUniqueJPAEntityExceptionTest {


    @Test
    public void shouldBeSubclassOfCheckedException() {
        Exception ex = new NotUniqueJPAEntityException();
        assertTrue(!(ex instanceof RuntimeException));
    }


}
