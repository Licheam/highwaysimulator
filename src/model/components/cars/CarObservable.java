package model.components.cars;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.components.highway.CarPositionObserver;
import model.components.passengers.CarInStationObserver;
import model.components.passengers.CarPassengerObserver;

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
