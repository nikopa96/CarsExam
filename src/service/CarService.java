package service;

import car.Car;
import car.CarDriver;
import engine.Engine;
import engine.LemonadeEngine;
import storage.WaitingCarStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Car service
 */
public class CarService implements Runnable {

    private static final int WAITING_TIME = 50;

    private int crossroadUniqueNumber;
    private WaitingCarStorage waitingCarStorage;

    private int carNumber;

    public CarService(int crossroadUniqueNumber, WaitingCarStorage waitingCarStorage) {
        this.crossroadUniqueNumber = crossroadUniqueNumber;
        this.waitingCarStorage = waitingCarStorage;
    }

    @Override
    public String toString() {
        return "Service on crossroad №" + crossroadUniqueNumber + " has visited by Car #" + carNumber;
    }

    private void changeEngine(Car car) {
        String oldEngine = car.getEngine().getInformation();
        car.setEngine(new LemonadeEngine());
        System.out.println("Car #" + car.getNumber() + " old engine: " + oldEngine + " / Car #" + car.getNumber() + " new engine: " + car.getEngine().getInformation());
    }

    public void addCarDriverToService(CarDriver carDriver) {
        waitingCarStorage.addCarDriverToService(carDriver);
    }

    private void serviceCar() throws InterruptedException {
        while (!Thread.interrupted()) {
            Thread.sleep(WAITING_TIME);
            CarDriver carDriver = waitingCarStorage.getCarFromService();

            this.carNumber = carDriver.getCar().getNumber();
            Stream.of(this).forEach(System.out::println);

            if (carDriver.canChangeEngine()) {
                changeEngine(carDriver.getCar());
            }

            carDriver.notifyLock();
        }
    }

    @Override
    public void run() {
        try {
            serviceCar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
