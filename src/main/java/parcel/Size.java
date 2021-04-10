package parcel;

public enum Size {
    SMALL(6.0),
    MEDIUM(12.5),
    LARGE(25.0);

    public final Double cost;

    private Size(Double cost) {
        this.cost = cost;
    }
}
