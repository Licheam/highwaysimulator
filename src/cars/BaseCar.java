package cars;

import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.timer.TimeObserver;
import passengers.CarPassengerObserver;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseCar implements CarObservable, TimeObserver {
    private CarTrack track;
    private final ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    private final ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();
    private double speed = 0;
    private final int DISTANCE_BJ = 0;
    private final int DISTANCE_CP = 0;
    private final int DISTANCE_GZ = 0;
    private final int DISTANCE_WG = 0;
    private final int DISTANCE_XP = 0;
    private final int DISTANCE_XY = 0;
    private final int DISTANCE_XN = 0;
    private final Map<String, Integer> stationDistance = new TreeMap<>(){{
        stationDistance.put("BJ", DISTANCE_BJ);
        stationDistance.put("GZ", DISTANCE_GZ);
        stationDistance.put("CP", DISTANCE_CP);
        stationDistance.put("WG", DISTANCE_WG);
        stationDistance.put("XP", DISTANCE_XP);
        stationDistance.put("XY", DISTANCE_XY);
        stationDistance.put("XN", DISTANCE_XN);
    }};
    public abstract double getMaxSpeed();
    public abstract int getMaxPassengers();
    public abstract long getPullOffTime();

    public BaseCar(CarTrack track) {
        this.track = track;
    }

    public CarTrack getTrack() {
        return track;
    }

    public void setTrack(CarTrack track) {
        this.track = track;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public final void registerObserver(CarPositionObserver positionObserver) {
        positionObservers.add(positionObserver);
    }

    @Override
    public final void removeObserver(CarPositionObserver positionObserver) {
        positionObservers.remove(positionObserver);
    }

    @Override
    public final void notifyPositionObservers() {
        for (CarPositionObserver positionObserver : positionObservers) {
            positionObserver.updateCarPosition(this);
        }
    }

    @Override
    public final void registerObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public final void removeObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public final void notifyPassengerObservers() {
        for (CarPassengerObserver passengerObserver : passengerObservers) {
            passengerObserver.updateCarPassenger(this);
        }
    }

    @Override
    public void updateTime() {

    }
}

