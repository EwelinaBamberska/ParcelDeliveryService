package mediator;

import commands.Action;
import commands.StoreParcelCommand;
import parcel.Parcel;
import storage.StoragePlace;
import transport.Car;
import transport.CarFleet;

public class MediatorCourier {

    public Parcel MoveParcel(StoragePlace storagePlaceFrom, StoragePlace storagePlaceTo, Parcel parcel) {
        CarFleet carFleet = CarFleet.getInstance();
        Car car = carFleet.acquireCar();

        Parcel depositedParcelToIntermediateStorage = new StoreParcelCommand(storagePlaceFrom, Action.PickUp, parcel).Execute().GetResult();
        car.getStorage().storeParcel(depositedParcelToIntermediateStorage);
        Parcel upParcel = car.getStorage().pickUpParcel(depositedParcelToIntermediateStorage.getId());

        carFleet.releaseCar(car);

        return new StoreParcelCommand(storagePlaceTo, Action.Store, upParcel).Execute().GetResult();
    }
}
