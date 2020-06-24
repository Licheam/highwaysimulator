package model.components.passengers;

import model.components.cars.BaseCar;
import model.components.cars.CarObserver;

public interface CarInStationObserver extends CarObserver {
    void updateCarInStation(BaseCar car, String carStation);
}
