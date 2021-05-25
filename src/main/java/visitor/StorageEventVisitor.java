package visitor;

import event.Event;
import storage.ExternalStorage;
import storage.IntermediateStorage;
import storage.ParcelLocker;

public interface StorageEventVisitor<T> {

    T getEventInfo(ParcelLocker parcelLocker, Event event);
    T getEventInfo(IntermediateStorage parcelLocker, Event event);
    T getEventInfo(ExternalStorage parcelLocker, Event event);
}
