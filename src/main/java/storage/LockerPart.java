package storage;

import parcel.Parcel;
import parcel.Size;

import java.util.List;
import java.util.UUID;

public interface LockerPart {
    int getId();
    boolean hasFreeSlot();
    Size getSize();
    Parcel pickUpParcel(UUID parcelId);
    List<Parcel> getAllParcels();
    Parcel storeParcel(Parcel parcel);
    boolean isEmpty();
}
