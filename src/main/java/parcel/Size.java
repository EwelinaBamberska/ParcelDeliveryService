package parcel;

import java.math.BigDecimal;

public enum Size implements Comparable<Size>{
    SMALL(BigDecimal.valueOf(6)),
    MEDIUM(BigDecimal.valueOf(12.5)),
    LARGE(BigDecimal.valueOf(25.0));

    public final BigDecimal cost;

    Size(BigDecimal cost) {
        this.cost = cost;
    }
}
