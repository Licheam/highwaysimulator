package model.cars;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.highway.CarPositionObserver;
import model.passengers.CarInStationObserver;
import model.passengers.CarPassengerObserver;

public interface CarObservable {
    void registerObserver(CarPositionObserver positionObserver);
    void removeObserver(CarPositionObserver positionObserver);
    void notifyPositionObservers(double location, CarDirection direction) throws LocationErrorException;
    void registerObserver(CarPassengerObserver passengerObserver);
    void removeObserver(CarPassengerObserver passengerObserver);
    void notifyPassengerObservers();
    void registerObserver(CarInStationObserver inStationObserver);
    void removeObserver(CarInStationObserver inStationObserver);
    void notifyCarInStationObservers(String carStation);
}
