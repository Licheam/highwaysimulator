package view;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.components.cars.BaseCar;
import model.components.highway.CarPositionObserver;
import model.components.highway.CarTrack;
import model.components.passengers.CarPassengerObserver;
import model.components.stations.CarStationObservable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wangmengxi
 * <p>
 * AnimationView can display the animation of the simulation.
 */
public class AnimationView implements CarPositionObserver {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 250;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 110;
    private static final int BUTTON_HEIGHT = 30;
    private final JFrame frame;

    private final TreeMap<Double, String> stationsDistributions;
    private final Map<BaseCar, CarButton> cars = new HashMap<>();

    class CarButton {
        private JButton button;
        private BaseCar car;
        private CarFrame frame;

        public CarButton(BaseCar car) {
            this.car = car;
            button = new JButton("第" + car.getID() + "号" + car.toString());
            button.addActionListener(new ButtonListener());
        }

        public JButton getButton() {
            return button;
        }

        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame == null) {
                    frame = new CarFrame(car);
                } else {
                    frame.reset();
                }
            }
        }
    }

    public AnimationView(CarTrack track) {
        frame = new JFrame("Animation View");
        frame.setBounds(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stationsDistributions = track.getStationsDistributions();
        double stationLength = stationsDistributions.lastKey() - stationsDistributions.firstKey();
        for (Map.Entry<Double, String> station : stationsDistributions.entrySet()) {
            JLabel stationLabel = new JLabel(station.getValue());
            stationLabel.setBounds((int) (FRAME_WIDTH / 40 + FRAME_WIDTH * 9.0 / 10.0 * (station.getKey() / stationLength))
                    , FRAME_HEIGHT / 10 + (int) (FRAME_HEIGHT * 4.0 / 5.0 * 2.0 / 3.0), LABEL_WIDTH, LABEL_HEIGHT);
            frame.getContentPane().add(stationLabel);
        }
        frame.setVisible(true);
    }

    public void setCarStation(CarStationObservable carStation) {
        carStation.registerAllCars(this);
    }

    @Override
    public void updateCarPosition(BaseCar car, double location, CarDirection direction) {
        Map.Entry<BaseCar, CarButton> carEntry;
        if (cars.containsKey(car)) {
            carEntry = Map.entry(car, cars.get(car));
        } else {
            carEntry = Map.entry(car, new CarButton(car));
            frame.getContentPane().add(carEntry.getValue().getButton());
        }

        double stationLength = stationsDistributions.lastKey() - stationsDistributions.firstKey();
        if (direction == CarDirection.Forward) {
            carEntry.getValue().getButton().setBounds(FRAME_WIDTH / 40 + (int) (FRAME_WIDTH * 9.0 / 10.0 * (location / stationLength))
                    , FRAME_HEIGHT / 10 + (int) (FRAME_HEIGHT * 4.0 / 5.0 * 1.0 / 3.0), BUTTON_WIDTH, BUTTON_HEIGHT);
        } else if (direction == CarDirection.Backward) {
            carEntry.getValue().getButton().setBounds(FRAME_WIDTH / 40 + (int) (FRAME_WIDTH * 9.0 / 10.0 * (location / stationLength))
                    , FRAME_HEIGHT / 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        cars.put(carEntry.getKey(), carEntry.getValue());
    }

    /**
     * CarFrame is a JFrame to display the detail information of a single car.
     */
    class CarFrame extends JFrame implements CarPassengerObserver, CarPositionObserver {
        private static final int FRAME_WIDTH = 200;
        private static final int FRAME_HEIGHT = 100;

        BaseCar car;
        JLabel identityLabel;
        JLabel positionLabel;
        JLabel passengerLabel;

        public CarFrame(BaseCar car) {
            this.car = car;
            setTitle("Detailed View");
            reset();
        }

        public void reset() {
            if (positionLabel == null) {
                positionLabel = new JLabel();
                getContentPane().add(BorderLayout.CENTER, positionLabel);
            }
            positionLabel.setText("当前位置: ");

            if (passengerLabel == null) {
                passengerLabel = new JLabel();
                getContentPane().add(BorderLayout.SOUTH, passengerLabel);
            }
            passengerLabel.setText("当前乘客个数: " + car.getNumberOfPassengers());

            if (identityLabel == null) {
                identityLabel = new JLabel();
                getContentPane().add(BorderLayout.NORTH, identityLabel);
            }
            identityLabel.setText("第" + car.getID() + "号" + car.toString());

            car.registerObserver((CarPositionObserver) this);
            car.registerObserver((CarPassengerObserver) this);

            addWindowListener(new FrameTerminater());

            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setVisible(true);
        }

        @Override
        public void updateCarPassenger(BaseCar car) {
            passengerLabel.setText("当前乘客个数: " + car.getNumberOfPassengers());
        }

        @Override
        public void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException {
            Map.Entry<Double, String> locationDetails = car.getTrack().getLocationDetails(location, direction);
            if (locationDetails.getKey() > 0) {
                positionLabel.setText("当前位置: " + locationDetails.getValue() + "站以东 "
                        + (((int) (Math.abs(locationDetails.getKey()) * 100)) / 100) + "公里");
            } else if (locationDetails.getKey() < 0) {
                positionLabel.setText("当前位置: " + locationDetails.getValue() + "站以西 "
                        + (((int) (Math.abs(locationDetails.getKey()) * 100)) / 100) + "公里");
            } else {
                positionLabel.setText("当前位置: " + locationDetails.getValue() + "站中");
            }
        }

        class FrameTerminater extends WindowAdapter {

            @Override
            public void windowClosing(WindowEvent e) {
                CarFrame.this.car.removeObserver((CarPositionObserver) CarFrame.this);
                CarFrame.this.car.removeObserver((CarPassengerObserver) CarFrame.this);
                CarFrame.this.dispose();
            }
        }
    }
}