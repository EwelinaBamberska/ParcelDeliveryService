package transport;

import lombok.Getter;
import storage.IntermediateStorage;

/**
 * Car used to transport parcels between storages.
 */
public class Car {
    /** The storage space in the back of the car. */
    @Getter
    private final IntermediateStorage storage;

    @Getter
    private final int id;

    public Car(int id) {
        this.id = id;
        this.storage = new IntermediateStorage();
    }
}
