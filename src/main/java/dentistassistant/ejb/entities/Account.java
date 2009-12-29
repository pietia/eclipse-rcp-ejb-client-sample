package dentistassistant.ejb.entities;

import org.hibernate.validator.Length;
import org.hibernate.validator.Pattern;

import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "Account.findEmployee", query = "SELECT e FROM Employee e"
        + " WHERE e.account.login = :login AND e.account.password = :password")})
@Entity
public class Account extends AbstractBaseEntity {

    private String login;
    private String password;
    private Employee employee;

    @OneToOne(mappedBy = "account")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Pattern(regex = "[0-9a-zA-Z]{3,12}$", message = "Login - musi składał się z cyfr/liter (3-12 znaków, bez polskich znaków)")
    @Column(nullable = false, length = 12)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Length(min = 6, max = 250, message = "Hasło - (minimum 6 znaków)")
    @Column(nullable = false, length = 250)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
