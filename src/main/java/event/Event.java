package event;

import storage.StoragePlace;

import java.time.LocalDate;
import java.util.UUID;

public class Event {
    private LocalDate time;
    private StoragePlace storagePlace;
    private EventType eventType;
    private UUID parcelId;

    public Event(LocalDate time, StoragePlace storagePlace, EventType eventType, UUID parcelId) {
        this.time = time;
        this.storagePlace = storagePlace;
        this.eventType = eventType;
        this.parcelId = parcelId;
    }
}
