package event;

import lombok.Getter;
import storage.StoragePlace;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Event {
    private final LocalDate time;
    private final StoragePlace storagePlace;
    private final EventType eventType;
    private final UUID parcelId;

    public Event(LocalDate time, StoragePlace storagePlace, EventType eventType, UUID parcelId) {
        this.time = time;
        this.storagePlace = storagePlace;
        this.eventType = eventType;
        this.parcelId = parcelId;
    }
}
