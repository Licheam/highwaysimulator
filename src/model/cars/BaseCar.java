package model.cars;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.passengers.CarInStationObserver;
import model.passengers.CarPassengerObserver;
import model.passengers.Passenger;

import java.util.ArrayList;

public abstract class BaseCar implements CarObservable {
    protected int ID;
    private CarTrack track;
    private final ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    private final ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();
    private final ArrayList<CarInStationObserver> inStationObservers = new ArrayList<>();
    private double speed = 0;
    private String currentLocation;
    private final ArrayList<Passenger> passengers = new ArrayList<>();

    protected double MAX_SPEED = 0;
    protected int MAX_PASSENGERS = 0;
    protected long PULL_OFF_TIME = 0;

    public BaseCar(CarTrack track) {
        this.track = track;
    }

    public final int getID() {
        return ID;
    }

    public final double getMaxSpeed() {
        return MAX_SPEED / 60000;
    }

    public final int getMaxPassengers() {
        return MAX_PASSENGERS;
    }

    public final long getPullOffTime() {
        return PULL_OFF_TIME;
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

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getLocation() {
        return currentLocation;
    }

    public int getNumberOfPassengers() {
        return passengers.size();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        notifyPassengerObservers();
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        notifyPassengerObservers();
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
    public final void notifyPositionObservers(double location, CarDirection direction) throws LocationErrorException {
        for (CarPositionObserver positionObserver : positionObservers) {
            positionObserver.updateCarPosition(this, location, direction);
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
    public void registerObserver(CarInStationObserver inStationObserver) {
        inStationObservers.add(inStationObserver);
    }

    @Override
    public void removeObserver(CarInStationObserver inStationObserver) {
        inStationObservers.remove(inStationObserver);
    }

    @Override
    public void notifyCarInStationObservers(String carStation) {
        for (CarInStationObserver inStationObserver : inStationObservers) {
            inStationObserver.updateCarInStation(this, carStation);
        }
    }
}


