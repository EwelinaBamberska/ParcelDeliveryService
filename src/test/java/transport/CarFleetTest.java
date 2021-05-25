package transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CarFleetTest {

    @Test
    void testCarsAreReused() {
        CarFleet carFleet = CarFleet.getInstance();
        Car car1 = carFleet.acquireCar();
        Car car2 = carFleet.acquireCar();

        assertNotEquals(car1, car2);

        carFleet.releaseCar(car1);

        Car car3 = carFleet.acquireCar();

        assertEquals(car1, car3);
    }

    @Test
    void testGetInstance() {
        CarFleet instance1 = CarFleet.getInstance();
        CarFleet instance2 = CarFleet.getInstance();

        assertEquals(instance1, instance2);
    }
}