package model.components.cars;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.components.highway.CarPositionObserver;

public interface CarPositionObservable {
    void registerObserver(CarPositionObserver positionObserver);
    void removeObserver(CarPositionObserver positionObserver);
    void notifyPositionObservers(double location, CarDirection direction) throws LocationErrorException;
}
