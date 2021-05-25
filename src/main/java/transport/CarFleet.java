package transport;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Car fleet as a pool of objects. */
public class CarFleet {
    private final Map<Car, Boolean> carsAvailable;

    private static CarFleet INSTANCE;

    private CarFleet(int size) {
        this.carsAvailable = IntStream.range(0, size).mapToObj(Car::new)
                .collect(Collectors.toMap(car -> car, car -> true));
    }

    public synchronized Car acquireCar() {
        Optional<Entry<Car, Boolean>> optionalCar = carsAvailable.entrySet().stream().filter(Entry::getValue).findFirst();
        if (optionalCar.isPresent())
        {
            Entry<Car, Boolean> carBooleanEntry = optionalCar.get();
            carBooleanEntry.setValue(false);
            return carBooleanEntry.getKey();
        }

        Car car = new Car(carsAvailable.size());
        carsAvailable.put(car, false);
        return car;
    }

    public synchronized void releaseCar(Car car) {
        carsAvailable.put(car, true);
    }

    /**
     * Singleton getter.
     */
    public static synchronized CarFleet getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarFleet(10);
        }
        return INSTANCE;
    }
}
