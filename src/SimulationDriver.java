import enumerates.CarDirection;
import model.highway.CarTrack;
import model.stations.BJCarStation;
import model.stations.BaseCarStation;
import model.stations.CarFactory;
import model.stations.XNCarStation;
import model.timer.TimeModel;
import view.TerminalView;

public class SimulationDriver {

    public static void main(String[] args) {
        TimeModel timeModel = new TimeModel();

        CarTrack carTrack = new CarTrack(timeModel) {{
            addStation("BJ", 0);
            addStation("GZ", 24);
            addStation("CP", 45);
            addStation("WG", 107);
            addStation("XP", 128);
            addStation("XY", 152);
            addStation("XN", 174);
        }};

        CarFactory carFactory = new CarFactory();
        BaseCarStation XNCarStation = new XNCarStation(carTrack, CarDirection.Backward, 174,
                carFactory, timeModel);
        BaseCarStation BJCarStation = new BJCarStation(carTrack, CarDirection.Forward, 0,
                carFactory, timeModel);
        TerminalView terminalView = new TerminalView(timeModel, carTrack) {{
            setCarStation(XNCarStation);
            setCarStation(BJCarStation);
        }};

        timeModel.start();
    }
}
