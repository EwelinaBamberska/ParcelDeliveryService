package storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import parcel.Size;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelLockerTest {
    private ParcelLocker parcelLocker;

    @BeforeEach
    public void setUp() {
        parcelLocker = new ParcelLocker(0, "Piotrowo 3A, Poznań", Arrays.asList(new Module(1, Size.SMALL, 2), new Slot(3, Size.SMALL)));
    }

    @Test
    void testStoreParcel() {
        assertThrows(RuntimeException.class, () ->
                parcelLocker.storeParcel(new Parcel(Size.MEDIUM, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()))
        );
        parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()));
        parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()));
        parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()));
        assertThrows(RuntimeException.class, () ->
                parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()))
        );
        assertEquals(3, parcelLocker.getAllParcels().size());

    }

    @Test
    void testPickUpParcel() {
        Parcel parcel = new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList());
        parcelLocker.storeParcel(parcel);
        parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()));
        parcelLocker.storeParcel(new Parcel(Size.SMALL, mock(ParcelLocker.class), mock(ParcelLocker.class), Collections.emptyList()));
        assertEquals(3, parcelLocker.getAllParcels().size());

        Parcel pickedUpParcel = parcelLocker.pickUpParcel(parcel.getId());
        assertEquals(parcel, pickedUpParcel);
        assertEquals(2, parcelLocker.getAllParcels().size());
    }
}
