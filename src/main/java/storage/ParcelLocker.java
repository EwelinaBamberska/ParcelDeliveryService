package storage;

import event.Event;
import event.EventType;
import lombok.Getter;
import parcel.Parcel;
import parcel.Size;

import java.time.LocalDate;
import java.util.*;

public class ParcelLocker {

    @Getter
    private final int id;
    private String physicalAddress;
    private final List<Event> history;
    private final List<Module> modules;

    public ParcelLocker(int id, String physicalAddress, Collection<Module> modules) {
        this.id = id;
        this.physicalAddress = physicalAddress;
        this.history = new LinkedList<>();
        this.modules = new ArrayList<>(modules);
    }

    public List<Event> getHistory() {
        return history;
    }

    public Integer calculateExpectedOccupancy(Date date) {

        return null;
    }

    public List<Slot> calculateAvailability(Date date) {

        return null;
    }

    public UUID depositParcel(Parcel parcel) {
        Slot slot = findSlotForParcelSize(parcel.getSize());
        slot.storeParcel(parcel);
        Event event = new Event(LocalDate.now(), slot, EventType.ARRIVAL, parcel.getId());
        history.add(event);
        parcel.addEvent(event);

        return parcel.getId();
    }

    private Slot findSlotForParcelSize(Size size) {
        return modules.stream()
                .filter(module -> module.getSize().equals(size))
                .flatMap(module -> module.getSlots().stream())
                .sorted(Comparator.comparing(Slot::getSize))
                .filter(Slot::isEmpty)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no empty slot for given size"));
    }

    public Parcel collectParcel(int id) {

        return null;
    }

    public List<Parcel> getParcelWithOvertime() {

        return null;
    }
}
