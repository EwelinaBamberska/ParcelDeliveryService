package storage;

import event.Event;
import event.EventType;
import lombok.Getter;
import parcel.Parcel;
import parcel.Size;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ParcelLocker implements StoragePlace {

    @Getter
    private final int id;
    private String physicalAddress;
    private final List<Event> history;
    private final Map<Integer, Module> modules;
    private final Map<UUID, Integer> parcelIdToModuleId;


    public ParcelLocker(int id, String physicalAddress, Collection<Module> modules) {
        this.id = id;
        this.physicalAddress = physicalAddress;
        this.modules = new HashMap<>(modules.size());
        for (Module module : modules) {
            this.modules.put(module.getId(), module);
        }

        history = new LinkedList<>();
        parcelIdToModuleId = new HashMap<>();
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

    private Module findModuleForParcelSize(Size size) {
        return modules.values().stream()
                .filter(Module::hasFreeSlot)
                .filter(module -> module.getSize().compareTo(size) >= 0).min(Comparator.comparing(Module::getSize))
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
        Parcel parcel = modules.get(parcelIdToModuleId.remove(parcelId)).collectParcel(parcelId);
        Event event = new Event(LocalDate.now(), this, EventType.DEPARTURE, parcel.getId());

        history.add(event);
        parcel.addEvent(event);

        return parcel;
    }

    @Override
    public Parcel getParcel(UUID parcelId) {
        return modules.get(parcelIdToModuleId.get(parcelId)).collectParcel(parcelId);
    }

    @Override
    public List<Parcel> getAllParcels() {
        return modules.values().stream().flatMap(module -> module.getAllParcels().stream()).collect(Collectors.toList());
    }

    @Override
    public Parcel storeParcel(Parcel parcel) {
        Module module = findModuleForParcelSize(parcel.getSize());
        parcelIdToModuleId.put(parcel.getId(), module.getId());
        Parcel parcelStored = module.storeParcel(parcel);
        Event event = new Event(LocalDate.now(), this, EventType.ARRIVAL, parcel.getId());

        history.add(event);
        parcelStored.addEvent(event);

        return parcelStored;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
