package dentistassistant.ejb.interfaces;

import dentistassistant.ejb.entities.Account;
import dentistassistant.ejb.entities.Employee;

import java.util.List;

public interface IAccountManager {
    public Employee loginTry(Account account, List<String> macs);
}
