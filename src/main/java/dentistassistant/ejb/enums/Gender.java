package dentistassistant.ejb.enums;

public enum Gender {
    M("M"), F("K");

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
