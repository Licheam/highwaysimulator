package model.passengers;

import model.cars.BaseCar;
import model.cars.CarObserver;

public interface CarPassengerObserver extends CarObserver {
    void updateCarPassenger(BaseCar car);
}
