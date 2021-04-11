package storage;

import parcel.Parcel;

public interface StoragePlace {

    Parcel storeParcel(Parcel parcel);
    boolean isEmpty();
}
