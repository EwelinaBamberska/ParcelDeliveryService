package user_portal;

import event.Event;
import parcel.Parcel;
import parcel.Size;
import payment.AdditionalService;
import storage.ParcelLocker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserPortal {

    private final List<Parcel> parcels;

    public UserPortal() {
        this.parcels = new ArrayList<>();
    }

    public Parcel registerParcel(
        ParcelLocker senderLocker,
        ParcelLocker receivingLocker,
        Size size,
        List<AdditionalService> additionalServices
    ) {
        Parcel parcel = new Parcel(size, senderLocker, receivingLocker, additionalServices);
        parcels.add(parcel);
        return parcel;
    }

    public List<Parcel> getAllParcels() {
        return parcels;
    }

    public boolean makePayment(UUID parcelId, BigDecimal paidValue) {
        return getParcelById(parcelId).makePayment(paidValue);
    }

    public List<Event> getParcelEvents(UUID parcelId) {
        return getParcelById(parcelId).getEvents();
    }

    private Parcel getParcelById(UUID parcelId) {
        return parcels.stream()
                .filter(p -> parcelId.equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("There is no parcel with id " + parcelId));
    }
}
