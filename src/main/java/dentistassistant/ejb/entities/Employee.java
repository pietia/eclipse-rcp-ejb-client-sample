package dentistassistant.ejb.entities;

import dentistassistant.ejb.enums.EmployeeStatus;
import dentistassistant.ejb.enums.EmployeeType;
import org.hibernate.validator.NotNull;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Employee.countByPesel", query = "SELECT count(e) FROM Employee e " +
                "WHERE e.pesel = :pesel"),
        @NamedQuery(name = "Employee.countAll", query = "SELECT count(e) FROM Employee e")})
@Entity
public class Employee extends Person {


    private EmployeeType employeeType;
    private Account account;
    private EmployeeStatus employeeStatus;

    @NotNull(message = "Typ pracownika - nie może być pusty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @NotNull(message = "Status pracownika - nie może być pusty")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    //Used in GUI clients
    @Override
    public int hashCode() {
        int result = employeeType != null ? employeeType.hashCode() : 0;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (employeeStatus != null ? employeeStatus.hashCode() : 0);
        return result;
    }

    //Used in GUI clients
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Employee)) return false;

        final Employee employee = (Employee) other;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (account != null ? !account.equals(employee.account) : employee.account != null) return false;
        if (employeeStatus != employee.employeeStatus) return false;
        if (employeeType != employee.employeeType) return false;

        return true;
    }

    @Transient
    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

}
