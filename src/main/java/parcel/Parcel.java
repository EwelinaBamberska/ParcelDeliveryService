package parcel;

import event.Event;
import lombok.Getter;
import payment.AdditionalService;
import payment.Payment;
import storage.ParcelLocker;
import storage.StoragePlace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Parcel {
    private UUID id;
    private Size size;
    private Instant actualDeliveryTime;
    private Instant guaranteedDeliveryTime;
    private Instant actualPickupTime;
    private StoragePlace storagePlace;
    private ParcelLocker senderLocker;
    private ParcelLocker receiverLocker;
    private Payment payment;
    private List<AdditionalService> additionalServices;
    private List<Event> events;

    public Parcel(
        Size size,
        ParcelLocker senderLocker,
        ParcelLocker receiverLocker,
        List<AdditionalService> additionalServices
    ) {
        id = UUID.randomUUID();
        this.size = size;
        actualDeliveryTime = null;
        guaranteedDeliveryTime = Instant.now().plusSeconds(60 * 60 * 24 * 2);
        actualPickupTime = null;
        storagePlace = null;
        this.senderLocker = senderLocker;
        this.receiverLocker = receiverLocker;
        this.additionalServices = additionalServices;
        events = new ArrayList<Event>();
        payment = new Payment(calculateValue());
    }

    public Boolean makePayment(Double paidValue) {
        return payment.pay(paidValue);
    }

    public Instant estimateDeliveryTime() {
        return null;
    }

    private Double calculateValue() {
        return size.cost + additionalServices.stream().mapToDouble(a -> a.getCost()).sum();
    }
}
