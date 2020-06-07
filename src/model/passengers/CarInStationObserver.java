package model.passengers;

import model.cars.BaseCar;

public interface CarInStationObserver {
    void updateCarInStation(BaseCar car, String carStation);
}
