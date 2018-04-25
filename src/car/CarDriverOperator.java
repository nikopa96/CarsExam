package car;

/**
 * Car driver operator
 */
public class CarDriverOperator implements Runnable {

    private CarDriverDatabase carStorage;

    public CarDriverOperator(CarDriverDatabase carStorage) {
        this.carStorage = carStorage;
    }

    private void runAllCarDrivers() throws InterruptedException {
        while (!Thread.interrupted()) {
            Thread thread = new Thread(carStorage.getCarDriver());
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            runAllCarDrivers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
