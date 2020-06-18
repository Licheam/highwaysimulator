import enumerates.CarDirection;
import model.highway.CarTrack;
import model.stations.BJCarStation;
import model.stations.CarFactory;
import model.stations.XNCarStation;
import model.timer.TimeModel;
import view.TerminalView;

public class SimulationDriver {

    public static void main(String[] args) {
        TimeModel timeModel = new TimeModel();
        TerminalView terminalView = new TerminalView();
        CarTrack carTrack = new CarTrack(timeModel) {{
            addStation("BJ", 0);
            addStation("GZ", 24);
            addStation("CP", 45);
            addStation("WG", 107);
            addStation("XP", 128);
            addStation("XY", 152);
            addStation("XN", 174);
        }};

        timeModel.registerObserver(terminalView);
        timeModel.registerObserver(carTrack);
        carTrack.registerObserver(terminalView);

        CarFactory carFactory = new CarFactory();
        XNCarStation XNCarStation = new XNCarStation(carTrack, CarDirection.Backward, 174,
                carFactory, timeModel);
        BJCarStation BJCarStation = new BJCarStation(carTrack, CarDirection.Forward, 0,
                carFactory, timeModel);

        timeModel.start();
        timeModel.notifyTimeObservers();
    }
}
