import commands.Action;
import commands.StoreParcelCommand;
import mediator.MediatorCourier;
import parcel.Parcel;
import parcel.Size;
import payment.AdditionalService;
import storage.IntermediateStorage;
import storage.LockerPart;
import storage.Module;
import storage.ParcelLocker;
import user_portal.UserPortal;

import javax.print.attribute.standard.Media;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World!"); // Display the string.


        //Initialization
        UserPortal userPortal = new UserPortal();
        IntermediateStorage intermediateStorage = new IntermediateStorage();

        Collection<LockerPart> parcelLockerFirstModule = new ArrayList<>();
        parcelLockerFirstModule.add(new Module(1, Size.LARGE, 5));
        parcelLockerFirstModule.add(new Module(2, Size.MEDIUM, 10));
        parcelLockerFirstModule.add(new Module(3, Size.SMALL, 20));
        ParcelLocker parcelLockerFirst = new ParcelLocker(1, "Lewego 4", parcelLockerFirstModule);

        Collection<LockerPart> parcelLockerSecondModule = new ArrayList<>();
        parcelLockerSecondModule.add(new Module(1, Size.LARGE, 5));
        parcelLockerSecondModule.add(new Module(2, Size.MEDIUM, 10));
        parcelLockerSecondModule.add(new Module(3, Size.SMALL, 20));
        ParcelLocker parcelLockerSecond = new ParcelLocker(1, "Prawego 12", parcelLockerSecondModule);

        //Transit Register
        Parcel parcel = userPortal.registerParcel(parcelLockerFirst, parcelLockerSecond, Size.LARGE, new ArrayList<>());
        boolean isPaid = userPortal.makePayment(parcel.getId(), BigDecimal.valueOf(25.0));


        if(!isPaid) {
            System.out.println("Payment error!");
            return;
        }

        Parcel depositedParcel = new StoreParcelCommand(parcelLockerFirst, Action.Store, parcel).Execute().GetResult();

        MediatorCourier mediatorCourier = new MediatorCourier();
        Parcel parcelInIntermediateStorage = mediatorCourier.MoveParcel(parcelLockerFirst, intermediateStorage, depositedParcel);
        Parcel parcelInReceivingLocker = mediatorCourier.MoveParcel(intermediateStorage, parcelLockerSecond, parcelInIntermediateStorage);

        Parcel parcelDelivered = new StoreParcelCommand(parcelLockerSecond, Action.PickUp, parcelInReceivingLocker).Execute().GetResult();
    }
}
