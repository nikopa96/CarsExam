package city;

import bird.Bird;
import car.Car;
import service.EnvironmentCentre;

import java.util.*;

/**
 * City.
 */
public class City {

    private EnvironmentCentre environmentCentre;
    private Bird bird;

    private List<Street> streetList = new ArrayList<>();

    public City(EnvironmentCentre environmentCentre, Bird bird) {
        this.environmentCentre = environmentCentre;
        this.bird = bird;
    }

    public void addStreet(Crossroad firstCrossroad, Crossroad secondCrossroad, String name, boolean goodRoad) {
        streetList.add(new Street(firstCrossroad, secondCrossroad, name, goodRoad));
    }

    public List<Street> getStreetList() {
        return streetList;
    }

    public EnvironmentCentre getEnvironmentCentre() {
        return environmentCentre;
    }

    public Bird getBird() {
        return bird;
    }

}
