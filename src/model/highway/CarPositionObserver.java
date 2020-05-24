package model.highway;

import cars.BaseCar;

public interface CarPositionObserver {
    void updateCarPosition(BaseCar car);
}
