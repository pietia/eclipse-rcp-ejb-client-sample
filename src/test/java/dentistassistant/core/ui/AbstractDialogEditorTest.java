package dentistassistant.core.ui;

import dentistassistant.core.exceptions.NotUniqueJPAEntityException;
import dentistassistant.ejb.entities.Employee;
import org.eclipse.swt.widgets.Shell;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AbstractDialogEditorTest {

    private static Shell shell; // = new Shell();
    private static List<Employee> employees;
    private static Employee employee;
    private static Class<? extends AbstractDialogEditor.AbstractValidatorMethod<Employee>> VALIDATOR_CLASS;

    //fake validator - test correct validator in higher plugin
    private static class EmployeeValidator extends AbstractDialogEditor.AbstractValidatorMethod<Employee> {
        public boolean validate() {
            //XXX STUB!
            return true;
        }

        public void validateUnique() throws NotUniqueJPAEntityException {
            //XXX STUB!
        }
    }


    @BeforeClass
    public static void init() {
        employees = Arrays.asList(new Employee(), new Employee());

        VALIDATOR_CLASS = EmployeeValidator.class;

    }

    @Before
    public void beforeEach() {
        employee = new Employee();
        employee.setId(10);
        employee.setName("Anna");
        employee.setSurname("Nowak");
    }

    @Ignore(value = "Needs Eclipse RCP env.")
    @Test
    public void shouldSetAndReturnCorrectEntity() {

        AbstractDialogEditor<Employee> employeeEditor = new AbstractDialogEditor<Employee>(shell, VALIDATOR_CLASS,
                employees.iterator(), AbstractDialogEditor.DialogMode.EDIT, employee) {
            protected Employee getData() {
                this.VALUE.setName("Ala");
                this.VALUE.setSurname("Makota");
                return this.VALUE;
            }

            ;
        };

        assertTrue(employee == employeeEditor.getValue());
    }

}
