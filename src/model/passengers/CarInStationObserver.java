package model.passengers;

import model.cars.BaseCar;
import model.cars.CarObserver;

public interface CarInStationObserver extends CarObserver {
    void updateCarInStation(BaseCar car, String carStation);
}
