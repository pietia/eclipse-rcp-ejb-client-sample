package dentistassistant.ejb.implementations;

import dentistassistant.ejb.entities.Account;
import dentistassistant.ejb.entities.Employee;
import dentistassistant.ejb.interfaces.IAccountManagerLocal;
import dentistassistant.ejb.interfaces.IAccountManagerRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AccountManagerBean")
public class AccountManagerBean implements IAccountManagerLocal,
        IAccountManagerRemote {

    @PersistenceContext(unitName = "dentistassistant_pu")
    private EntityManager em;

    @Override
    public Employee loginTry(Account account, List<String> macs) {
        //XXX STUB!
        return new Employee();
    }


}
