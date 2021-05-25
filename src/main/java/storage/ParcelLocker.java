package storage;

import event.Event;
import event.EventType;
import lombok.Getter;
import parcel.Parcel;
import parcel.Size;
import visitor.StorageEventVisitor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ParcelLocker implements StoragePlace, LockerPart {

    @Getter
    private final int id;
    private String physicalAddress;
    private final List<Event> history;
    private final Map<Integer, LockerPart> parts;
    private final Map<UUID, Integer> parcelIdToModuleId;


    public ParcelLocker(int id, String physicalAddress, Collection<LockerPart> parts) {
        this.id = id;
        this.physicalAddress = physicalAddress;
        this.parts = new HashMap<>(parts.size());
        for (LockerPart part : parts) {
            this.parts.put(part.getId(), part);
        }

        history = new LinkedList<>();
        parcelIdToModuleId = new HashMap<>();
    }

    public List<Event> getHistory() {
        return history;
    }


    private LockerPart findPlaceForParcelSize(Size size) {
        return parts.values().stream()
                .filter(LockerPart::hasFreeSlot)
                .filter(part -> part.getSize().compareTo(size) >= 0).min(Comparator.comparing(LockerPart::getSize))
                .orElseThrow(() -> new RuntimeException("There is no empty slot for given size"));
    }

    public List<Parcel> getOvertimeParcels() {
        Set<UUID> parcelsInLocker = parcelIdToModuleId.keySet();
        return history.stream()
                .filter(event -> event.getEventType().equals(EventType.ARRIVAL))
                .filter(event -> event.getTime().isBefore(LocalDate.now().minusDays(2)))
                .map(Event::getParcelId)
                .filter(parcelsInLocker::contains)
                .map(this::getParcel)
                .collect(Collectors.toList());
    }

    @Override
    public Parcel pickUpParcel(UUID parcelId) {
        Parcel parcel = parts.get(parcelIdToModuleId.remove(parcelId)).pickUpParcel(parcelId);
        Event event = new Event(LocalDate.now(), this, EventType.DEPARTURE, parcel.getId());

        history.add(event);
        parcel.addEvent(event);

        return parcel;
    }

    @Override
    public Parcel getParcel(UUID parcelId) {
        return parts.get(parcelIdToModuleId.get(parcelId)).pickUpParcel(parcelId);
    }

    @Override
    public List<Parcel> getAllParcels() {
        return parts.values().stream().flatMap(part -> part.getAllParcels().stream()).collect(Collectors.toList());
    }

    @Override
    public Parcel storeParcel(Parcel parcel) {
        LockerPart part = findPlaceForParcelSize(parcel.getSize());
        parcelIdToModuleId.put(parcel.getId(), part.getId());
        Parcel parcelStored = part.storeParcel(parcel);
        Event event = new Event(LocalDate.now(), this, EventType.ARRIVAL, parcel.getId());

        history.add(event);
        parcelStored.addEvent(event);

        return parcelStored;
    }

    @Override
    public boolean isEmpty() {
        return !parts.values().stream().map(LockerPart::hasFreeSlot).collect(Collectors.toList()).contains(false);
    }

    @Override
    public String accept(StorageEventVisitor<String> visitor, Event event) {
        return visitor.getEventInfo(this, event);
    }

    @Override
    public boolean hasFreeSlot() {
        return parts.values().stream().map(LockerPart::hasFreeSlot).collect(Collectors.toList()).contains(true);
    }

    @Override
    public Size getSize() {
        return null;
    }

    public Integer calculateExpectedOccupancy(Date date) {

        return null;
    }

    public List<Slot> calculateAvailability(Date date) {

        return null;
    }
}
