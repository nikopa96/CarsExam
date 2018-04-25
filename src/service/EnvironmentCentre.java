package service;

import car.Car;
import car.CarDriver;
import engine.DieselEngine;
import engine.Engine;
import engine.GasolineEngine;
import engine.LemonadeEngine;
import file.WritingToFile;
import storage.WaitingCarStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Environment service.
 */
public class EnvironmentCentre implements Runnable {

    private static final double MAX_UNITS_FOR_DIESEL_ENGINE = 400;
    private static final double MAX_UNITS_FOR_GASOLINE_ENGINE = 500;
    private static final int WAITING_TIME = 2000;
    private static final int INTERNAL_COMBUSTION_ENGINE_CARS = 70;
    private static final int ALL_CARS_IN_DATABASE = 201;

    private WaitingCarStorage waitingCarStorage;
    private WritingToFile writingToFile;

    private Map<Integer, Car> carDatabaseForEnvironmentCheck = new HashMap<>();
    private double currentUnit = 0;
    private boolean writingToFileFlag = true;

    public EnvironmentCentre(WaitingCarStorage waitingCarStorage, WritingToFile writingToFile) {
        this.waitingCarStorage = waitingCarStorage;
        this.writingToFile = writingToFile;
    }

    public void countPollutionUnit(Car car) {
        if (car.getEngine() instanceof DieselEngine) {
            currentUnit += 3;
        } else if (car.getEngine() instanceof GasolineEngine) {
            currentUnit += 2;
        } else if (car.getEngine() instanceof LemonadeEngine) {
            currentUnit += 0.5;
        } else {
            currentUnit += 0.1;
        }
    }

    public synchronized boolean getDrivePermission(Engine engine) {
        if (currentUnit > MAX_UNITS_FOR_DIESEL_ENGINE && currentUnit < MAX_UNITS_FOR_GASOLINE_ENGINE) {
            return !(engine instanceof DieselEngine);
        } else
            return !(currentUnit >= MAX_UNITS_FOR_GASOLINE_ENGINE) || !(engine instanceof DieselEngine) && !(engine instanceof GasolineEngine);
    }

    private void setNewCurrentUnit() {
        List internalCombustionEngineCars = carDatabaseForEnvironmentCheck.entrySet().stream()
                .filter(s -> s.getValue().getEngine() instanceof DieselEngine || s.getValue().getEngine() instanceof GasolineEngine).collect(Collectors.toList());

        if (internalCombustionEngineCars.size() >= INTERNAL_COMBUSTION_ENGINE_CARS) {
            this.currentUnit = currentUnit * 0.4;
        } else {
            this.currentUnit = 0;
        }
    }

    private void runEnvironmentCentre() throws InterruptedException, IOException {
        while (!Thread.interrupted()) {
            if (carDatabaseForEnvironmentCheck.size() == ALL_CARS_IN_DATABASE && writingToFileFlag) {
                writingToFile.writeInformationToFile(carDatabaseForEnvironmentCheck);
                this.writingToFileFlag = false;
            }

            Thread.sleep(WAITING_TIME);
            setNewCurrentUnit();

            waitingCarStorage.getCarFromEnvironmentCentre().forEach(s -> {
                System.out.println("Car " + s.getCar().getNumber() + " resumed");
                s.notifyLock();
            });
        }
    }

    @Override
    public void run() {
        try {
            runEnvironmentCentre();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized double getCurrentUnit() {
        return currentUnit;
    }

    public void registerCarToEnvironmentCentre(int carNumber, Car car) {
        carDatabaseForEnvironmentCheck.put(carNumber, car);
    }

    public void addCarDriverToWaitingList(CarDriver carDriver) {
        waitingCarStorage.addCarToEnvironmentCentre(carDriver);
    }

    public void addCarDriverToChangeTires(CarDriver carDriver) {
        waitingCarStorage.addCarToChangeTires(carDriver);
    }
}
