package dentistassistant.ejb.implementations;

import dentistassistant.ejb.interfaces.IEmployeeManagerLocal;
import dentistassistant.ejb.interfaces.IEmployeeManagerRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "EmployeeManager")
public class EmployeeManagerBean implements IEmployeeManagerRemote,
        IEmployeeManagerLocal {

    @PersistenceContext(unitName = "dentistassistant_pu")
    private EntityManager em;


    public Long countEmployees() {
        //XXX STUB!
        return 0L;
    }


}
