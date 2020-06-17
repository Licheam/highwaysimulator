import model.highway.CarTrack;
import model.timer.TimeModel;
import view.TerminalView;

public class SimulationDriver {

    public static void main(String[] args) {
        TimeModel timeModel = new TimeModel();
        TerminalView terminalView = new TerminalView();
        CarTrack cartrack = new CarTrack(timeModel);
    }
}
