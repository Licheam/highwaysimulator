package passengers;

import cars.BaseCar;

public interface CarInStationObserver {
    void updateCarInStation(BaseCar car, String carStation);
}
