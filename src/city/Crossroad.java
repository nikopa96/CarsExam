package city;

import service.CarService;
import service.Checkpoint;

/**
 * Crossroad.
 */
public class Crossroad {

    private int uniqueLabel;
    private CarService carService;

    public Crossroad(int uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    public Crossroad(int uniqueLabel, CarService carService) {
        this.uniqueLabel = uniqueLabel;
        this.carService = carService;
    }

    public CarService getCarService() {
        return carService;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Crossroad)) return false;

        Crossroad crossroad = (Crossroad) object;
        return uniqueLabel == crossroad.uniqueLabel;
    }

    @Override
    public int hashCode() {
        return uniqueLabel;
    }

    public int getUniqueLabel() {
        return uniqueLabel;
    }

}
