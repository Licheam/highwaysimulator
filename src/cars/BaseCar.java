package cars;

import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.timer.TimeObserver;
import passengers.CarInStationObserver;
import passengers.CarPassengerObserver;

import java.util.ArrayList;

public abstract class BaseCar implements CarObservable, TimeObserver {
    private CarTrack track;
    private final ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    private final ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();
    private double speed = 0;
    private String currentLocation;

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

    public void setCurrentLocation(String currentLocation){
        this.currentLocation = currentLocation;
    }

    public String getLocation(){
        return currentLocation;
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
        passengerObservers.remove(passengerObserver);
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

    @Override
    public void registerObserver(CarInStationObserver carInStationObserver) {

    }

    @Override
    public void removeObserver(CarInStationObserver carInStationObserver) {

    }

    @Override
    public void notifyCarInStationObservers() {

    }
}


