package dentistassistant.ejb.entities;

import dentistassistant.ejb.enums.Gender;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({@NamedQuery(name = "Person.countByPesel", query = "SELECT count(p) " +
        "FROM Patient AS p WHERE p.pesel = :pesel ")})
public class Person extends AbstractBaseEntity {


    private Gender gender;
    private String name;
    private String surname;
    private String pesel;

    @NotNull(message = "Płeć - wybierz wartość")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Pattern(regex = "[0-9]{11,11}$", message = "Pesel - musi mieć 11 znaków liczbowych")
    @Column(length = 11, unique = true, nullable = false)
    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @NotNull(message = "Imię - nie może być puste")
    @Length(min = 1, max = 250, message = "Imię - nie może być puste")
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Nazwisko - nie może być puste")
    @Length(min = 1, max = 250, message = "Nazwisko - nie może być puste")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
