package model.highway;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.cars.BaseCar;

public interface CarPositionObserver {
    void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException;
}
