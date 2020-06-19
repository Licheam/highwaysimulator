package model.highway;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.cars.BaseCar;
import model.cars.CarObserver;

public interface CarPositionObserver extends CarObserver {
    void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException;
}
