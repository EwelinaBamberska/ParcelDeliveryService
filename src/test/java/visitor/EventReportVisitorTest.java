package visitor;

import event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import parcel.Size;
import storage.*;
import storage.Module;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EventReportVisitorTest {

    private EventReport eventReport;

    @BeforeEach
    public void setUp()
    {
        eventReport = new EventReport();
    }

    @Test
    public void testGetEventInfoParcelLocker(){
        ParcelLocker parcelLocker = new ParcelLocker(0, "Piotrowo 3A, Poznań", Arrays.asList(new Module(1, Size.SMALL, 2), new Slot(3, Size.SMALL)));

        Parcel parcel = new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList());
        parcelLocker.storeParcel(parcel);
        parcelLocker.pickUpParcel(parcel.getId());
        List<Event> events = parcel.getEvents();

        assertFalse(events.isEmpty());

        Event arrivalEvent = events.get(0);
        String eventInfo = arrivalEvent.getStoragePlace().accept(eventReport, arrivalEvent);
        String expectedInfo = "Parcel has arrived to parcel locker at " + arrivalEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);

        Event pickUpEvent = events.get(1);
        eventInfo = pickUpEvent.getStoragePlace().accept(eventReport, pickUpEvent);
        expectedInfo = "Parcel has been departured from parcel locker at " + pickUpEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);
    }

    @Test
    public void testGetEventInfoIntermediateStorage(){
        IntermediateStorage intermediateStorage = new IntermediateStorage();

        Parcel parcel = new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList());
        intermediateStorage.storeParcel(parcel);
        intermediateStorage.pickUpParcel(parcel.getId());
        List<Event> events = parcel.getEvents();

        assertFalse(events.isEmpty());

        Event arrivalEvent = events.get(0);
        String eventInfo = arrivalEvent.getStoragePlace().accept(eventReport, arrivalEvent);
        String expectedInfo = "Parcel has arrived to intermediate storage at " + arrivalEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);

        Event pickUpEvent = events.get(1);
        eventInfo = pickUpEvent.getStoragePlace().accept(eventReport, pickUpEvent);
        expectedInfo = "Parcel has been departured from intermediate storage at " + pickUpEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);
    }

    @Test
    public void testGetEventInfoExternalStorage(){
        ExternalStorage externalStorage = new ExternalStorage();

        Parcel parcel = new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList());
        externalStorage.storeParcel(parcel);
        externalStorage.pickUpParcel(parcel.getId());
        List<Event> events = parcel.getEvents();

        assertFalse(events.isEmpty());

        Event arrivalEvent = events.get(0);
        String eventInfo = arrivalEvent.getStoragePlace().accept(eventReport, arrivalEvent);
        String expectedInfo = "Parcel has arrived to external storage at " + arrivalEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);

        Event pickUpEvent = events.get(1);
        eventInfo = pickUpEvent.getStoragePlace().accept(eventReport, pickUpEvent);
        expectedInfo = "Parcel has been departured from external storage at " + pickUpEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);
    }

    @Test
    public void testGetEventInfoExternalStorageDestroy(){
        ExternalStorage externalStorage = new ExternalStorage();

        Parcel parcel = new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList());
        externalStorage.storeParcel(parcel);
        externalStorage.destroyParcel(parcel.getId());
        List<Event> events = parcel.getEvents();

        assertFalse(events.isEmpty());

        Event destroyEvent = events.get(1);
        String eventInfo = destroyEvent.getStoragePlace().accept(eventReport, destroyEvent);
        String expectedInfo = "Parcel has been destroyed in external storage at " + destroyEvent.getTime() + ".\n";

        assertEquals(expectedInfo, eventInfo);
    }
}
