package bird;

import service.EnvironmentCentre;

/**
 * Bird.
 */
public class Bird implements Runnable {

    private static final int SINGING_TIME = 4000;

    private EnvironmentCentre environmentCentre;

    public Bird(EnvironmentCentre environmentCentre) {
        this.environmentCentre = environmentCentre;
    }

    private void sing() throws InterruptedException {
        while (!Thread.interrupted()) {
            Thread.sleep(SINGING_TIME);

            if (environmentCentre.getCurrentUnit() < 400) {
                System.out.println("~~~~~~~~~~~~~~~~    PUHAS ÕHK ON PUHAS ÕHK ON RÕÕMUS LINNU ELU!    ~~~~~~~~~~~~~~~~");
            } else {
                System.out.println("~~~~~~~~~~~~~~~~    INIMENE TARK, INIMENE TARK – SAASTET TÄIS ON LINNAPARK    ~~~~~~~~~~~~~~~~");
            }
        }
    }

    @Override
    public void run() {
        try {
            sing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
