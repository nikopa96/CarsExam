import controller.Controller;

/**
 * Main.
 */
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.createCity();
        controller.runCarCreators();
        controller.runCarDriverOperator();
    }

}
