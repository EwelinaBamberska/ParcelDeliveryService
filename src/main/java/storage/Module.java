package storage;

import lombok.Getter;
import parcel.Size;

import java.util.*;

public class Module {

    @Getter
    private final int id;
    @Getter
    private final Size size;
    private final Map<Integer, Slot> slots;

    public Module(int id, Size size, int numberOfSlots) {
        this.id = id;
        this.size = size;
        slots = new HashMap<>(numberOfSlots);
        for (int i=0; i<numberOfSlots; i++)
        {
            slots.put(i, new Slot(i, size));
        }
    }

    public Slot getSlot(int id) {
        return slots.get(id);
    }

    public List<Slot> getSlots() {
        return Collections.unmodifiableList(new ArrayList<>(slots.values()));
    }
}
