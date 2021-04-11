package storage;

import parcel.Parcel;

import java.util.List;
import java.util.UUID;

public interface MultiplePackagesStoragePlace extends StoragePlace {

    /** Takes parcel out of storage place - it will no longer be accessible. */
    Parcel pickUpParcel(UUID parcelId);

    /** Retrieves parcel without removing it from storage. */
    Parcel getParcel(UUID parcelId);

    List<Parcel> getAllParcels();
}
