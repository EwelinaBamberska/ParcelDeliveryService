package parcel;

import event.Event;
import lombok.Getter;
import payment.AdditionalService;
import payment.Payment;
import storage.ParcelLocker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Parcel {
    private final UUID id;
    private final Size size;
    private final Instant actualDeliveryTime;
    private final Instant guaranteedDeliveryTime;
    private Instant actualPickupTime;
    private final ParcelLocker senderLocker;
    private final ParcelLocker receiverLocker;
    private final Payment payment;
    private final List<AdditionalService> additionalServices;
    private final List<Event> events;

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
        this.senderLocker = senderLocker;
        this.receiverLocker = receiverLocker;
        this.additionalServices = additionalServices;
        events = new ArrayList<>();
        payment = new Payment(calculateValue());
    }

    public boolean makePayment(BigDecimal paidValue) {
        return payment.pay(paidValue);
    }

    public Instant estimateDeliveryTime() {
        return null;
    }

    private BigDecimal calculateValue() {
        return size.cost.add(BigDecimal.valueOf(additionalServices.stream()
                .mapToDouble(AdditionalService::getCost).sum()));
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
