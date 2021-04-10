package storage;

import parcel.Parcel;

public class Slot  implements StoragePlace {

    private Integer id;
    private Module module;
    private Parcel parcel;

    @Override
    public void storagePlace() {

    }

    public Boolean isEmpty() {

        return false;
    }
    public Parcel collectParcel() {

        return null;
    }
}
