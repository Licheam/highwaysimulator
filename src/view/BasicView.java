package view;

import controller.TimerControllerInterface;
import enumerates.CarType;
import model.components.highway.CarTrack;
import model.components.highway.CarTrackObserver;
import model.components.stations.*;
import model.timer.TimeModel;
import model.timer.TimeObserver;
import model.timer.TimerStateObserver;

import javax.swing.*;

public class BasicView implements TimeObserver, CarStationObserver, CarTrackObserver, TimerStateObserver {
    private JPanel mainPanel;
    private JLabel timeLabel;
    private JLabel trackLabel;
    private JLabel xnStationLabel;
    private JLabel bjStationLabel;
    private JButton switchButton;
    private TimeModel timeModel;
    private TimerControllerInterface timerController;

    public BasicView() {
    }

    public BasicView(TimerControllerInterface timerController, TimeModel timeModel, CarTrack track) {
        this.timerController = timerController;
        this.timeModel = timeModel;

        switchButton.addActionListener(e -> timerController.switchStates());

        timeModel.registerObserver((TimeObserver) this);
        timeModel.registerObserver((TimerStateObserver) this);
        track.registerObserver(this);


        JFrame frame = new JFrame("Basic View");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void setCarStation(CarStationObservable carStation) {
        carStation.registerObserver(this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic View");
        frame.setContentPane(new BasicView().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void updateTime(TimeModel timeModel) {
        timeLabel.setText("当前时间: " + timeModel.getFormatTime());
    }

    @Override
    public void updateCarStation(BaseCarStation carStation) {
        if (carStation instanceof XNCarStation) {
            xnStationLabel.setText("<html>XN站: <br/>候车人数: " + carStation.getNumberOfPassengers()
                    + "<br/>待发Volve数目: " + carStation.getNumberOfCars(CarType.Volve)
                    + "<br/>待发Iveco数目: " + carStation.getNumberOfCars(CarType.Iveco) + "</html>");
        } else if (carStation instanceof BJCarStation) {
            bjStationLabel.setText("<html>BJ站: <br/>候车人数: " + carStation.getNumberOfPassengers()
                    + "<br/>待发Volve数目: " + carStation.getNumberOfCars(CarType.Volve)
                    + "<br/>待发Iveco数目: " + carStation.getNumberOfCars(CarType.Iveco) + "</html>");
        }
    }

    @Override
    public void updateCarTrack(CarTrack track) {
        trackLabel.setText("<html>当前轨道: "
                + "<br/>Volve数目：" + track.getNumberOfVolveCars()
                + "<br/>Iveco数目：" + track.getNumberOfIvecoCars() + "</html>");
    }

    @Override
    public void updateTimerState() {
        if (timeModel.isRunning()) {
            switchButton.setText("暂停模拟");
        } else if (timeModel.isPaused()) {
            switchButton.setText("继续模拟");
        } else {
            switchButton.setText("开始模拟");
        }
    }

}
