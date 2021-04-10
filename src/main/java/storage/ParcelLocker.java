package storage;

import event.Event;
import parcel.Parcel;

import java.util.Date;
import java.util.List;

public class ParcelLocker {

    private Integer id;
    private String physicalAddress;
    private List<Event> last7DaysHistory;
    private List<Module> modules;

    public List<Event> getHistory() {
        return last7DaysHistory;
    }

    public Integer calculateExpectedOccupancy(Date date) {

        return null;
    }

    public List<Slot> calculateAvailability(Date date) {

        return null;
    }

    public Integer depositParcel(Parcel parcel) {

        return null;
    }

    public Parcel collectParcel(Integer id) {

        return null;
    }

    public List<Parcel> getParcelWithOvertime() {

        return null;
    }
}
