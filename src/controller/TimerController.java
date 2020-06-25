package controller;

import enumerates.CarDirection;
import model.components.highway.CarTrack;
import model.components.stations.BJCarStation;
import model.components.stations.BaseCarStation;
import model.components.stations.CarFactory;
import model.components.stations.XNCarStation;
import model.timer.TimeModel;
import view.AnimationView;
import view.BasicView;
import view.TerminalView;

public class TimerController implements TimerControllerInterface {
    TimeModel timeModel;
    CarTrack carTrack;
    CarFactory carFactory;
    BaseCarStation xnCarStation;
    BaseCarStation bjCarStation;
    AnimationView animationView;
    BasicView basicView;
    TerminalView terminalView;

    public TimerController(TimeModel timeModel) {
        this.timeModel = timeModel;

        carTrack = new CarTrack(timeModel) {{
            addStation("GZ", 24);
            addStation("CP", 45);
            addStation("WG", 107);
            addStation("XP", 128);
            addStation("XY", 152);
        }};

        carFactory = new CarFactory();
        xnCarStation = new XNCarStation(carTrack, CarDirection.Backward, 174,
                carFactory, timeModel);
        bjCarStation = new BJCarStation(carTrack, CarDirection.Forward, 0,
                carFactory, timeModel);


//        terminalView = new TerminalView(timeModel, carTrack) {{
//            setCarStation(xnCarStation);
//            setCarStation(bjCarStation);
//        }};

        basicView = new BasicView(this, timeModel, carTrack) {{
            setCarStation(xnCarStation);
            setCarStation(bjCarStation);
        }};

        animationView = new AnimationView(carTrack) {{
            setCarStation(xnCarStation);
            setCarStation(bjCarStation);
        }};
    }

    @Override
    public void switchStates() {
        timeModel.switchState();
    }
}
