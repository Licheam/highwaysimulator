import controller.TimerController;
import controller.TimerControllerInterface;
import model.timer.TimeModel;

/**
 * @author wangmengxi
 *
 * Simulation Driver is the starter for everything.
 */
public class SimulationDriver {

    public static void main(String[] args) {
        TimeModel timeModel = new TimeModel();
        TimerControllerInterface controller = new TimerController(timeModel);
    }
}
