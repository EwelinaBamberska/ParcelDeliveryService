package storage;

import parcel.Parcel;

import java.util.*;

public class IntermediateStorage implements MultiplePackagesStoragePlace {

    private final Map<UUID, Parcel> storedParcels;

    public IntermediateStorage() {
        this.storedParcels = new HashMap<>();
    }

    @Override
    public Parcel storeParcel(Parcel parcel) {
        return storedParcels.put(parcel.getId(), parcel);
    }

    @Override
    public Parcel pickUpParcel(UUID parcelId) {
        return storedParcels.remove(parcelId);
    }

    @Override
    public Parcel getParcel(UUID parcelId) {
        return storedParcels.get(parcelId);
    }

    @Override
    public boolean isEmpty() {
        return storedParcels.isEmpty();
    }

    @Override
    public List<Parcel> getAllParcels() {
        return new ArrayList<>(storedParcels.values());
    }
}
