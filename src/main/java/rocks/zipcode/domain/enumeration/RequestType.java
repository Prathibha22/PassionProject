package rocks.zipcode.domain.enumeration;

/**
 * The RequestType enumeration.
 */
public enum RequestType {
    ABSENT("Absent"),
    EARLYDISMISSAL("Early Dismissal"),
    CAR("Car"),
    BUS("Bus"),
    AFTERSCHOOLCLUB("After School Club");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
