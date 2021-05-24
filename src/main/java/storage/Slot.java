package storage;

import lombok.Getter;
import parcel.Parcel;
import parcel.Size;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Slot implements LockerPart {

    @Getter
    private final int id;
    @Getter
    private final Size size;
    @Getter
    private Parcel parcel;

    public Slot(int id, Size size) {
        this.id = id;
        this.size = size;
    }

    public Parcel storeParcel(Parcel parcel) {
        if (isEmpty()) {
            if (parcel.getSize().compareTo(size) <= 0)
            {
                this.parcel = parcel;
                return parcel;
            }
            else
            {
                throw new RuntimeException("Slot is too small for this package.");
            }
        }
        else {
            throw new RuntimeException("Slot is already occupied.");
        }
    }

    public boolean isEmpty() {
        return parcel == null;
    }

    public Parcel pickUpParcel() {
        Parcel parcelCopy = parcel;
        parcel = null;
        return parcelCopy;
    }

    @Override
    public boolean hasFreeSlot() {
        return isEmpty();
    }

    @Override
    public Parcel pickUpParcel(UUID parcelId) {
        return pickUpParcel();
    }

    @Override
    public List<Parcel> getAllParcels() {
        return Arrays.asList(pickUpParcel());
    }
}
