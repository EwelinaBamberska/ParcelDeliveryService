package storage;

import event.Event;
import event.EventType;
import parcel.Parcel;
import visitor.StorageEventVisitor;

import java.time.LocalDate;
import java.util.*;

public class IntermediateStorage implements StoragePlace {

    private final Map<UUID, Parcel> storedParcels;

    public IntermediateStorage() {
        this.storedParcels = new HashMap<>();
    }

    @Override
    public Parcel storeParcel(Parcel parcel) {
        parcel.addEvent(new Event(LocalDate.now(), this, EventType.ARRIVAL, parcel.getId()));
        storedParcels.put(parcel.getId(), parcel);
        return parcel;
    }

    @Override
    public Parcel pickUpParcel(UUID parcelId) {
        getParcel(parcelId).addEvent(new Event(LocalDate.now(), this, EventType.DEPARTURE, parcelId));
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
    public String accept(StorageEventVisitor<String> visitor, Event event) {
        return visitor.getEventInfo(this, event);
    }

    @Override
    public List<Parcel> getAllParcels() {
        return new ArrayList<>(storedParcels.values());
    }

}
