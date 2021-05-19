package user_portal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parcel.Size;
import storage.ParcelLocker;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserPortalTest {

    private UserPortal userPortal;

    @BeforeEach
    public void setUp() {
        userPortal = new UserPortal();
    }

    @Test
    public void testUserPortalHasNoParcelsInTheBeginning() {
        assertTrue(userPortal.getAllParcels().isEmpty());
    }

    @Test
    void testRegisterParcel() {
        UUID firstParcelId = userPortal
                .registerParcel(mock(ParcelLocker.class), mock(ParcelLocker.class), Size.MEDIUM, Collections.emptyList()).getId();

        assertEquals(1, userPortal.getAllParcels().size());
        assertEquals(firstParcelId, userPortal.getAllParcels().get(0).getId());

        userPortal.registerParcel(mock(ParcelLocker.class), mock(ParcelLocker.class), Size.SMALL, Collections.emptyList());
        userPortal.registerParcel(mock(ParcelLocker.class), mock(ParcelLocker.class), Size.MEDIUM, Collections.emptyList());

        assertEquals(3, userPortal.getAllParcels().size());
    }

    @Test
    void testMakePayment() {
        UUID parcelId = userPortal
                .registerParcel(mock(ParcelLocker.class), mock(ParcelLocker.class), Size.MEDIUM, Collections.emptyList()).getId();
        boolean tooLowPayment = userPortal.makePayment(parcelId, Size.MEDIUM.cost.subtract(BigDecimal.valueOf(1)));
        boolean tooBigPayment = userPortal.makePayment(parcelId, Size.MEDIUM.cost.add(BigDecimal.valueOf(1)));
        boolean goodPayment = userPortal.makePayment(parcelId, Size.MEDIUM.cost);

        assertFalse(tooLowPayment);
        assertFalse(tooBigPayment);
        assertTrue(goodPayment);
    }
}