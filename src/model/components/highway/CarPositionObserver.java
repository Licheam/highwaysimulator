package model.components.highway;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.components.cars.BaseCar;
import model.components.cars.CarObserver;

public interface CarPositionObserver extends CarObserver {
    void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException;
}
