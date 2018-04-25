package car;

import java.util.ArrayList;
import java.util.List;

/**
 * Car storage.
 */
public class CarDriverDatabase {

    private List<CarDriver> carDriverList = new ArrayList<>();
    private final Object lock = new Object();

    public void addCarDriver(CarDriver car) {
        synchronized (lock) {
            carDriverList.add(car);
            lock.notifyAll();
        }
    }

    public CarDriver getCarDriver() throws InterruptedException {
        synchronized (lock) {
            while (carDriverList.isEmpty()) {
                lock.wait();
            }

            return carDriverList.remove(0);
        }
    }

}
