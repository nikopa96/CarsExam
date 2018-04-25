package storage;

import car.Car;
import car.CarDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Waiting car storage.
 */
public class WaitingCarStorage {

    private List<CarDriver> waitingCarListForTireChanging = new ArrayList<>();
    private final Object specialCarLock = new Object();

    private List<CarDriver> waitingCarDriverListEnvironmentCentre = new ArrayList<>();
    private final Object environmentCentreLock = new Object();

    private List<CarDriver> waitingCarDriverForService = new ArrayList<>();
    private final Object carServiceLock = new Object();

    public void addCarToChangeTires(CarDriver carDriver) {
        synchronized (specialCarLock) {
            waitingCarListForTireChanging.add(carDriver);
            specialCarLock.notifyAll();
        }
    }

    public CarDriver getCarToChangeTires() throws InterruptedException {
        synchronized (specialCarLock) {
            while (waitingCarListForTireChanging.isEmpty()) {
                specialCarLock.wait();
            }

            return waitingCarListForTireChanging.remove(0);
        }
    }

    public void addCarToEnvironmentCentre(CarDriver carDriver) {
        synchronized (environmentCentreLock) {
            waitingCarDriverListEnvironmentCentre.add(carDriver);
            environmentCentreLock.notifyAll();
        }
    }

    public List<CarDriver> getCarFromEnvironmentCentre() throws InterruptedException {
        synchronized (environmentCentreLock) {
            while (waitingCarDriverListEnvironmentCentre.isEmpty()) {
                environmentCentreLock.wait();
            }

            final List<CarDriver> carDriverList = waitingCarDriverListEnvironmentCentre;
            waitingCarDriverListEnvironmentCentre = new ArrayList<>();

            return carDriverList;
        }
    }

    public void addCarDriverToService(CarDriver carDriver) {
        synchronized (carServiceLock) {
            waitingCarDriverForService.add(carDriver);
            carServiceLock.notifyAll();
        }
    }

    public CarDriver getCarFromService() throws InterruptedException {
        synchronized (carServiceLock) {
            while (waitingCarDriverForService.isEmpty()) {
                carServiceLock.wait();
            }

            return waitingCarDriverForService.remove(0);
        }
    }
}
