package mediator;

import commands.Action;
import commands.StoreParcelCommand;
import parcel.Parcel;
import storage.StoragePlace;

public class MediatorCourier {

    public Parcel MoveParcel(StoragePlace storagePlaceFrom, StoragePlace storagePlaceTo, Parcel parcel) {
        Parcel depositedParcelToIntermediateStorage = new StoreParcelCommand(storagePlaceFrom, Action.PickUp, parcel).Execute().GetResult();
        return new StoreParcelCommand(storagePlaceTo, Action.Store, depositedParcelToIntermediateStorage).Execute().GetResult();
    }
}
