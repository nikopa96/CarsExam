package car;

import city.Crossroad;
import city.Street;
import engine.ElectricEngine;
import engine.LemonadeEngine;
import service.Checkpoint;
import service.EnvironmentCentre;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Car driver.
 */
public class CarDriver implements Runnable {

    private static final int DRIVE_TIME = 20;

    private Car car;
    private List<Street> streetList;
    private Crossroad currentCrossroad;
    private Street currentStreet;
    private EnvironmentCentre environmentCentre;

    private final Object lock = new Object();
    private int visitedStreets = 0;
    private int refuseFromEnvironmentCentre = 0;
    private int badRoad = 0;

    public CarDriver(Car car, List<Street> streetList, Checkpoint checkpoint) {
        this.car = car;
        this.streetList = streetList;
        this.currentCrossroad = checkpoint.getCrossroad();
        this.environmentCentre = checkpoint.getEnvironmentCentre();

        System.out.println("Car " + car.getNumber() + ": " + checkpoint.toString());
        environmentCentre.registerCarToEnvironmentCentre(car.getNumber(), car);
    }

    Street chooseStreet(List<Street> streetList) throws InterruptedException {
        List<Street> possibleStreetList = streetList.stream()
                .filter(s -> (s.getFirstCrossroad().equals(currentCrossroad) ||
                        s.getSecondCrossroad().equals(currentCrossroad)) && !s.equals(currentStreet)).collect(Collectors.toList());

        Collections.shuffle(possibleStreetList);

        if (!currentCrossroad.equals(possibleStreetList.get(0).getFirstCrossroad())) {
            this.currentCrossroad = possibleStreetList.get(0).getFirstCrossroad();
        } else {
            this.currentCrossroad = possibleStreetList.get(0).getSecondCrossroad();
        }

        this.currentStreet = possibleStreetList.get(0);

        canGiveCarToService();

        return possibleStreetList.get(0);
    }

    private void canGiveCarToService() throws InterruptedException {
        if (currentCrossroad.getCarService() != null) {
            currentCrossroad.getCarService().addCarDriverToService(this);
            lock.wait();
        }
    }

    public boolean canChangeEngine() {
        if (refuseFromEnvironmentCentre == 2) {
            this.refuseFromEnvironmentCentre = 0;
            Random random = new Random();
            return random.nextInt(6) < 1;
        } else {
            return false;
        }
    }

    private void fiveVisitedStreets(Street chosenStreet) {
        this.visitedStreets++;
        if (!chosenStreet.isGoodRoad() && (car.getEngine() instanceof ElectricEngine || car.getEngine() instanceof LemonadeEngine)) this.badRoad++;

        environmentCentre.countPollutionUnit(car);
        System.out.println("Car " + car.getNumber() + " visited " + chosenStreet + " / " + environmentCentre.getCurrentUnit());
    }

    private void sevenVisitedStreets(Street chosenStreet) throws InterruptedException {
        boolean driveCarFlag = environmentCentre.getDrivePermission(car.getEngine());

        System.out.println("Car " + car.getNumber() + " visited " + chosenStreet + " / Permission: " + driveCarFlag);
        this.visitedStreets = 0;

        if (!driveCarFlag) {
            this.refuseFromEnvironmentCentre++;
            System.out.println("Car " + car.getNumber() + " paused");

            environmentCentre.addCarDriverToWaitingList(this);
            lock.wait();
        }
    }

    void drive(List<Street> streetList) throws InterruptedException {
        Street chosenStreet = chooseStreet(streetList);

        if (visitedStreets == 5) {
            fiveVisitedStreets(chosenStreet);
            badRoadMessage();
        } else if (visitedStreets == 7) {
            sevenVisitedStreets(chosenStreet);
        } else {
            this.visitedStreets++;
            if (!chosenStreet.isGoodRoad() && (car.getEngine() instanceof ElectricEngine || car.getEngine() instanceof LemonadeEngine)) this.badRoad++;
            badRoadMessage();

            System.out.println("Car " + car.getNumber() + " visited " + chosenStreet);
        }
    }

    private void badRoadMessage() throws InterruptedException {
        if (badRoad == 3) {
            System.out.println("Car " + car.getNumber() + " wait Special Car to change its tires");
            environmentCentre.addCarDriverToChangeTires(this);
            this.badRoad = 0;
            lock.wait();
        }
    }

    public void notifyLock() {
        synchronized (lock) {
            this.lock.notify();
        }
    }

    void runCarDriver() throws InterruptedException {
        synchronized (lock) {
            while (!Thread.interrupted()) {
                Thread.sleep(DRIVE_TIME);
                drive(streetList);
            }
        }
    }

    @Override
    public void run() {
        try {
            runCarDriver();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Car getCar() {
        return car;
    }

    Crossroad getCurrentCrossroad() {
        return currentCrossroad;
    }

    Street getCurrentStreet() {
        return currentStreet;
    }

    List<Street> getStreetList() {
        return streetList;
    }

    void setCurrentCrossroad(Crossroad currentCrossroad) {
        this.currentCrossroad = currentCrossroad;
    }

    void setCurrentStreet(Street currentStreet) {
        this.currentStreet = currentStreet;
    }
}
