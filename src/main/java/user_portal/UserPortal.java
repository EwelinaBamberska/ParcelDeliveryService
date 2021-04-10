package user_portal;

import parcel.Size;
import event.Event;
import parcel.Parcel;
import payment.AdditionalService;
import payment.Payment;
import storage.ParcelLocker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserPortal {

    private final List<Parcel> parcels = new ArrayList<Parcel>();

    public UUID registerParcel(
        ParcelLocker senderLocker,
        ParcelLocker receivingLocker,
        Size size,
        List<AdditionalService> additionalServices
    ) {
        Parcel parcel = new Parcel(size, senderLocker, receivingLocker, additionalServices);
        parcels.add(parcel);
        return parcel.getId();
    }

    public List<Parcel> getAllParcels() {
        return parcels;
    }

    public Boolean makePayment(String parcelId, BigDecimal paidValue) {
        return getParelById(parcelId).makePayment(paidValue);
    }

    public List<Event> getParcelEvents(String parcelId) {
        return getParelById(parcelId).getEvents();
    }

    private Parcel getParelById(String parcelId) {
        return parcels.stream().filter(p -> p.getId().equals(parcelId)).findFirst().get();
    }
}
