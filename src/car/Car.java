package car;

import engine.Engine;
import tire.Tire;

/**
 * Car.
 */
public class Car {

    private int number;
    private Engine engine;
    private Tire tire;

    public Car(int number, Engine engine, Tire tire) {
        this.number = number;
        this.engine = engine;
        this.tire = tire;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setTire(Tire tire) {
        this.tire = tire;
    }

    public int getNumber() {
        return number;
    }

    public Engine getEngine() {
        return engine;
    }

    public Tire getTire() {
        return tire;
    }

}
