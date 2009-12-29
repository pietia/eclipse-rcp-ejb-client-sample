package dentistassistant.core.util;

import dentistassistant.core.exceptions.InvalidJPAAttributeException;
import dentistassistant.ejb.entities.Employee;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernateEntityValidatorTest {

    private static Employee employee;


    @BeforeClass
    public static void init() {
        employee = new Employee();
        employee.setId(10);
        employee.setPesel("ToJestPesel"); // wrong property
        employee.setName("Anna");
        employee.setSurname("Nowak");
    }

    @Test(expected = InvalidJPAAttributeException.class)
    public void shouldRaiseExceptionWhenEntityContainsWrongProperty() throws InvalidJPAAttributeException {
        IEntityValidator validator = new HibernateEntityValidator();
        validator.validateEntity(employee);
    }

    @Test
    public void shouldRaiseExceptionContainigErrorMessageWhenEntityContainsWrongProperty() {
        IEntityValidator validator = new HibernateEntityValidator();
        try {
            validator.validateEntity(employee);
        } catch (InvalidJPAAttributeException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(!ex.getMessage().isEmpty());
        }


    }

}
