package storage;

import event.Event;
import parcel.Parcel;
import visitor.StorageEventVisitor;

import java.util.List;
import java.util.UUID;

public interface StoragePlace {

    /** Takes parcel out of storage place - it will no longer be accessible. */
    Parcel pickUpParcel(UUID parcelId);

    /** Retrieves parcel without removing it from storage. */
    Parcel getParcel(UUID parcelId);

    List<Parcel> getAllParcels();

    Parcel storeParcel(Parcel parcel);

    boolean isEmpty();

    String accept(StorageEventVisitor<String> visitor, Event event);
}
