package storage;

import lombok.Getter;
import parcel.Parcel;
import parcel.Size;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Module {

    @Getter
    private final int id;
    @Getter
    private final Size size;
    private final Map<Integer, Slot> slots;
    private final Map<UUID, Integer> parcelIdToSlotId;

    public Module(int id, Size size, int numberOfSlots) {
        this.id = id;
        this.size = size;
        slots = new HashMap<>(numberOfSlots);
        parcelIdToSlotId = new HashMap<>();
        for (int i = 0; i < numberOfSlots; i++) {
            slots.put(i, new Slot(i, size));
        }
    }

    public boolean hasFreeSlot() {
        return slots.values().stream().anyMatch(Slot::isEmpty);
    }

    public Parcel storeParcel(Parcel parcel) {
        Slot slot = slots.values().stream().filter(Slot::isEmpty).findFirst()
                .orElseThrow(() -> new RuntimeException("There is no empty slot in this module."));
        slot.storeParcel(parcel);
        parcelIdToSlotId.put(parcel.getId(), slot.getId());
        return parcel;
    }

    public Parcel collectParcel(UUID parcelId)
    {
        Integer slotId = parcelIdToSlotId.remove(parcelId);

        if (slotId != null)
        {
            Slot slot = slots.get(slotId);
            return slot.collectParcel();
        }
        else
        {
            throw new RuntimeException("Parcel " + parcelId + " not found in expected slot.");
        }
    }

    public boolean isEmpty() {
        return parcelIdToSlotId.isEmpty();
    }

    public List<Parcel> getAllParcels() {
        return slots.values().stream().filter(slot -> !slot.isEmpty()).map(Slot::getParcel).collect(Collectors.toList());
    }
}
