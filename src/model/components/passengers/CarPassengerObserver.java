package model.components.passengers;

import model.components.cars.BaseCar;
import model.components.cars.CarObserver;

public interface CarPassengerObserver extends CarObserver {
    void updateCarPassenger(BaseCar car);
}
