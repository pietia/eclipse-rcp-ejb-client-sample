package dentistassistant.ejb.enums;

public enum EmployeeType {
    RECEPTIONIST("Biuro"), DOCTOR("Lekarz"), ADMIN("Administrator");

    private String txt;

    private EmployeeType(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return txt;
    }

}
