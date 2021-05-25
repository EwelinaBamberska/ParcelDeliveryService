package visitor;

import event.Event;
import event.EventType;
import storage.ExternalStorage;
import storage.IntermediateStorage;
import storage.ParcelLocker;

public class EventReport implements StorageEventVisitor<String> {
    @Override
    public String getEventInfo(ParcelLocker parcelLocker, Event event) {
        return "Parcel " + getEventType(event.getEventType()) + " parcel locker at " + event.getTime() + ".\n";
    }

    @Override
    public String getEventInfo(IntermediateStorage parcelLocker, Event event) {
        return "Parcel " + getEventType(event.getEventType()) + " intermediate storage at " + event.getTime() + ".\n";
    }

    @Override
    public String getEventInfo(ExternalStorage parcelLocker, Event event) {
        return "Parcel " + getEventType(event.getEventType()) + " external storage at " + event.getTime() + ".\n";
    }

    private String getEventType(EventType type)
    {
        String eventType = "";
        switch (type)
        {
            case ARRIVAL:
                eventType = "has arrived to";
                break;
            case DEPARTURE:
                eventType = "has been departured from";
                break;
            case DESTROY:
                eventType = "has been destroyed in";
                break;
            default:
                break;
        }
        return eventType;
    }
}
