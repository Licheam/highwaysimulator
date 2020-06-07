package model.highway;

import model.cars.BaseCar;

public interface CarPositionObserver {
    void updateCarPosition(BaseCar car);
}
