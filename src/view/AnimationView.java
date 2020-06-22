package view;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.cars.BaseCar;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.stations.*;
import model.timer.TimeModel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AnimationView implements CarPositionObserver {
    private static final int FRAME_WIDTH = 2000;
    private static final int FRAME_HEIGHT = 500;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;
    private final JFrame frame;

    private final TreeMap<Double, String> stationsDistributions;
    private final Map<BaseCar, CarButton> cars = new HashMap<>();

    class CarButton {
        private JButton button;
        private BaseCar car;

        public CarButton(BaseCar car) {
            this.car = car;
            button = new JButton("第" + car.getID() + "号" + car.toString());
        }

        public JButton getButton() {
            return button;
        }
    }

    public AnimationView(CarTrack track) {
        frame = new JFrame("Animation View");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stationsDistributions = track.getStationsDistributions();
        double stationLength = stationsDistributions.lastKey() - stationsDistributions.firstKey();
        for (Map.Entry<Double, String> station : stationsDistributions.entrySet()) {
            JLabel stationLabel = new JLabel(station.getValue());
            stationLabel.setBounds(FRAME_WIDTH / 10 + (int) (FRAME_WIDTH * 4.0 / 5.0 * (station.getKey() / stationLength))
                    , FRAME_HEIGHT / 10 + (int) (FRAME_HEIGHT * 4.0 / 5.0 * 2.0 / 3.0), LABEL_WIDTH, LABEL_HEIGHT);
            System.out.println(stationLabel.getX() + ", " + stationLabel.getY());
            frame.getContentPane().add(stationLabel);
        }
        frame.setVisible(true);
    }

    public void setCarStation(CarStationObservable carStation) {
        carStation.registerAllCars(this);
    }

    public static void main(String[] args) {
        TimeModel timeModel = new TimeModel();

        CarTrack carTrack = new CarTrack(timeModel) {{
            addStation("GZ", 24);
            addStation("CP", 45);
            addStation("WG", 107);
            addStation("XP", 128);
            addStation("XY", 152);
        }};

        CarFactory carFactory = new CarFactory();
        BaseCarStation xnCarStation = new XNCarStation(carTrack, CarDirection.Backward, 174,
                carFactory, timeModel);
        BaseCarStation bjCarStation = new BJCarStation(carTrack, CarDirection.Forward, 0,
                carFactory, timeModel);

        AnimationView animationView = new AnimationView(carTrack);
    }

    @Override
    public void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException {
        if (location < stationsDistributions.firstKey() || location > stationsDistributions.lastKey()) {
            throw new LocationErrorException();
        }

        Map.Entry<BaseCar, CarButton> carEntry;
        if (cars.containsKey(car)) {
            carEntry = Map.entry(car, cars.get(car));
        } else {
            carEntry = Map.entry(car, new CarButton(car));
            frame.getContentPane().add(carEntry.getValue().getButton());
        }

        double stationLength = stationsDistributions.lastKey() - stationsDistributions.firstKey();
        if (direction == CarDirection.Forward) {
            carEntry.getValue().getButton().setBounds(FRAME_WIDTH / 10 + (int) (FRAME_WIDTH * 4.0 / 5.0 * (location / stationLength))
                    , FRAME_HEIGHT / 10 + (int) (FRAME_HEIGHT * 4.0 / 5.0 * 1.0 / 3.0), BUTTON_WIDTH, BUTTON_HEIGHT);
        } else if (direction == CarDirection.Backward) {
            carEntry.getValue().getButton().setBounds(FRAME_WIDTH / 10 + (int) (FRAME_WIDTH * 4.0 / 5.0 * (location / stationLength))
                    , FRAME_HEIGHT / 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        cars.put(carEntry.getKey(), carEntry.getValue());
    }
}

