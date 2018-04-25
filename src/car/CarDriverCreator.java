package car;

import city.City;
import city.Street;
import engine.DieselEngine;
import engine.ElectricEngine;
import engine.GasolineEngine;
import engine.LemonadeEngine;
import service.Checkpoint;
import service.EnvironmentCentre;
import tire.RubberTire;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Car creator.
 */
public class CarDriverCreator implements Runnable {

    private static final int CARS_WITH_ELECTRIC_ENGINE = 10;
    private static final int CARS_WITH_LEMONADE_ENGINE = 10;
    private static final int CARS_WITH_DIESEL_ENGINE = 90;
    private static final int CARS_WITH_GASOLINE_ENGINE = 90;

    private CarDriverDatabase carStorage;
    private List<Street> streetList;
    private EnvironmentCentre environmentCentre;

    private AtomicInteger carNumber = new AtomicInteger(1);

    public CarDriverCreator(CarDriverDatabase carStorage, City city) {
        this.carStorage = carStorage;
        this.streetList = city.getStreetList();
        this.environmentCentre = city.getEnvironmentCentre();

        Thread environmentCentreThread = new Thread(environmentCentre);
        environmentCentreThread.start();

        Thread birdThread = new Thread(city.getBird());
        birdThread.start();
    }

    private void createCarsWithElectricEngine() {
        for (int i = 0; i < CARS_WITH_ELECTRIC_ENGINE; i++) {
            Car car = new Car(carNumber.getAndIncrement(), new ElectricEngine(), new RubberTire());
            CarDriver carDriver = new CarDriver(car, streetList, new Checkpoint(environmentCentre));
            carStorage.addCarDriver(carDriver);
        }
    }

    private void createCarsWithLemonadeEngine() {
        for (int i = 0; i < CARS_WITH_LEMONADE_ENGINE; i++) {
            Car car = new Car(carNumber.getAndIncrement(), new LemonadeEngine(), new RubberTire());
            CarDriver carDriver = new CarDriver(car, streetList, new Checkpoint(environmentCentre));
            carStorage.addCarDriver(carDriver);
        }
    }

    private void createCarsWithDieselEngine() {
        for (int i = 0; i < CARS_WITH_DIESEL_ENGINE; i++) {
            Car car = new Car(carNumber.getAndIncrement(), new DieselEngine(), new RubberTire());
            CarDriver carDriver = new CarDriver(car, streetList, new Checkpoint(environmentCentre));
            carStorage.addCarDriver(carDriver);
        }
    }

    private void createCarsWithGasolineEngine() {
        for (int i = 0; i < CARS_WITH_GASOLINE_ENGINE; i++) {
            Car car = new Car(carNumber.getAndIncrement(), new GasolineEngine(), new RubberTire());
            CarDriver carDriver = new CarDriver(car, streetList, new Checkpoint(environmentCentre));
            carStorage.addCarDriver(carDriver);
        }
    }

    @Override
    public void run() {
        createCarsWithElectricEngine();
        createCarsWithLemonadeEngine();
        createCarsWithDieselEngine();
        createCarsWithGasolineEngine();
    }
}
