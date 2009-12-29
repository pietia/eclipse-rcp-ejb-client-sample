package dentistassistant.ejb.enums;

public enum EmployeeStatus {
    ACTIVE("Zatrudniony"), DELAYED("Historia");

    private String name;

    private EmployeeStatus(String status) {
        this.name = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
