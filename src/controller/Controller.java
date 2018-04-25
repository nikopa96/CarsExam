package controller;

import bird.Bird;
import car.*;
import city.City;
import city.Crossroad;
import engine.LemonadeEngine;
import file.JSONWriting;
import file.TextWriting;
import file.WritingToFile;
import service.CarService;
import service.Checkpoint;
import service.EnvironmentCentre;
import storage.WaitingCarStorage;
import tire.MarmaladeTire;

/**
 * Controller.
 */
public class Controller {

    private CarDriverDatabase carStorage = new CarDriverDatabase();
    private City city;

    public void runCarCreators() {
        Thread thread = createCars();
        thread.start();
    }

    public void runCarDriverOperator() {
        Thread thread = createCarDriverOperators();
        thread.start();
    }

    private Thread createCars() {
        CarDriverCreator carCreator = new CarDriverCreator(carStorage, city);
        return new Thread(carCreator);
    }

    private Thread createCarDriverOperators() {
        CarDriverOperator carDriverOperator = new CarDriverOperator(carStorage);
        return new Thread(carDriverOperator);
    }

    public void createCity() {
        WaitingCarStorage waitingCarStorage = new WaitingCarStorage();
        WritingToFile writingToFile = new JSONWriting();

        EnvironmentCentre environmentCentre = new EnvironmentCentre(waitingCarStorage, writingToFile);
        Bird bird = new Bird(environmentCentre);

        city = new City(environmentCentre, bird);

        CarService carService4 = new CarService(4, waitingCarStorage);
        CarService carService6 = new CarService(6, waitingCarStorage);
        CarService carService7 = new CarService(7, waitingCarStorage);
        CarService carService14 = new CarService(14, waitingCarStorage);

        Thread thread1 = new Thread(carService4);
        Thread thread2 = new Thread(carService6);
        Thread thread3 = new Thread(carService7);
        Thread thread4 = new Thread(carService14);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        Crossroad crossroad4 = new Crossroad(4, carService4);
        Crossroad crossroad6 = new Crossroad(6, carService6);
        Crossroad crossroad7 = new Crossroad(7, carService7);
        Crossroad crossroad14 = new Crossroad(14, carService14);

        city.addStreet(new Crossroad(1), new Crossroad(2), "Paldiski mnt", true);
        city.addStreet(new Crossroad(2), new Crossroad(3), "Järveotsa tee", false);
        city.addStreet(new Crossroad(1), crossroad4, "Laargi-Harku tee", true);
        city.addStreet(new Crossroad(3), crossroad4, "Kadaka tn", true);
        city.addStreet(crossroad4, new Crossroad(5), "Vabaduse pst", true);
        city.addStreet(new Crossroad(5), crossroad6, "Raja tn", true);
        city.addStreet(new Crossroad(3), crossroad6, "Akadeemia tee", true);
        city.addStreet(new Crossroad(5), new Crossroad(8), "Pärnu mnt", false);
        city.addStreet(crossroad6, crossroad7, "Sõpruse tee", true);
        city.addStreet(crossroad7, new Crossroad(8), "Tammsaare tee", true);
        city.addStreet(new Crossroad(8), new Crossroad(9), "Järvevana tee", true);
        city.addStreet(crossroad7, new Crossroad(10), "Liivalaia tee", true);
        city.addStreet(new Crossroad(9), new Crossroad(10), "Peterburi tee", true);
        city.addStreet(new Crossroad(10), new Crossroad(11), "J. Smuuli tee", true);
        city.addStreet(crossroad7, crossroad14, "E. Vilde tee", true);
        city.addStreet(new Crossroad(11), crossroad14, "Suur-Ameerika", true);
        city.addStreet(new Crossroad(11), new Crossroad(12), "Laagna tee", true);
        city.addStreet(new Crossroad(12), new Crossroad(13), "Estonia pst", true);
        city.addStreet(new Crossroad(13), crossroad14, "Endla mnt", true);
        city.addStreet(crossroad14, new Crossroad(15), "Mustamäe tee", true);
        city.addStreet(crossroad6, new Crossroad(15), "Ehitajate tee", true);

        CarDriver specialCarDriver = new SpecialCarDriver(new Car(9999, new LemonadeEngine(),
                new MarmaladeTire()), city.getStreetList(), new Checkpoint(environmentCentre), waitingCarStorage);
        Thread specialCarDriverThread = new Thread(specialCarDriver);
        specialCarDriverThread.start();

    }

}
