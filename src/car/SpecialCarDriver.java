package car;

import city.Crossroad;
import city.Street;
import engine.ElectricEngine;
import engine.LemonadeEngine;
import service.Checkpoint;
import storage.WaitingCarStorage;
import tire.MarmaladeTire;
import tire.RubberTire;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Special car driver.
 */
public class SpecialCarDriver extends CarDriver {

    private static final int DRIVE_TIME = 20;

    private WaitingCarStorage waitingCarStorage;

    public SpecialCarDriver(Car car, List<Street> streetList, Checkpoint checkpoint, WaitingCarStorage waitingCarStorage) {
        super(car, streetList, checkpoint);
        this.waitingCarStorage = waitingCarStorage;
    }

    @Override
    Street chooseStreet(List<Street> streetList) throws InterruptedException {
        Crossroad currentCrossroad = getCurrentCrossroad();

        List<Street> possibleStreetList = streetList.stream()
                .filter(s -> (s.getFirstCrossroad().equals(currentCrossroad) ||
                        s.getSecondCrossroad().equals(currentCrossroad)) && !s.equals(getCurrentStreet())).collect(Collectors.toList());

        Collections.shuffle(possibleStreetList);

        if (!currentCrossroad.equals(possibleStreetList.get(0).getFirstCrossroad())) {
            setCurrentCrossroad(possibleStreetList.get(0).getFirstCrossroad());
        } else {
            setCurrentCrossroad(possibleStreetList.get(0).getSecondCrossroad());
        }

        setCurrentStreet(possibleStreetList.get(0));

        return possibleStreetList.get(0);
    }

    @Override
    void drive(List<Street> streetList) throws InterruptedException {
        Street chosenStreet = chooseStreet(streetList);
        System.out.println("SPECIAL CAR DRIVER visited " + chosenStreet);
    }

    @Override
    void runCarDriver() throws InterruptedException {
        while (!Thread.interrupted()) {
            CarDriver carDriver = waitingCarStorage.getCarToChangeTires();

            while (!getCurrentCrossroad().equals(carDriver.getCurrentCrossroad())) {
                Thread.sleep(DRIVE_TIME);
                drive(getStreetList());
                System.out.println("SPECIAL CAR DRIVER searches Car #" + carDriver.getCar().getNumber());
            }

            if (carDriver.getCar().getEngine() instanceof ElectricEngine) {
                carDriver.getCar().setTire(new MarmaladeTire());
            } else if (carDriver.getCar().getEngine() instanceof LemonadeEngine) {
                carDriver.getCar().setTire(new MarmaladeTire());
            } else {
                carDriver.getCar().setTire(new RubberTire());
            }

            carDriver.notifyLock();
            System.out.println("Car " + carDriver.getCar().getNumber() + " now has " + carDriver.getCar().getTire().getInformation());
        }
    }
}
