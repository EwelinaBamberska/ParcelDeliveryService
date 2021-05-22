package commands;

import parcel.Parcel;
import storage.StoragePlace;

public class StoreParcelCommand implements ICommandResult<Parcel, StoreParcelCommand> {
    private final StoragePlace storagePlace;
    private final Action action;
    private final Parcel parcel;
    private Parcel result;

    public StoreParcelCommand(StoragePlace storagePlace, Action action, Parcel parcel) {
        this.storagePlace = storagePlace;
        this.action = action;
        this.parcel = parcel;
    }

    @Override
    public StoreParcelCommand Execute() {
        switch(action) {
            case Store:
                result = storagePlace.storeParcel(parcel);
                break;
            case PickUp:
                result = storagePlace.pickUpParcel(parcel.getId());
                break;
            default:
                result = null;
        }

        return this;
    }

    @Override
    public Parcel GetResult() {
        return result;
    }
}
