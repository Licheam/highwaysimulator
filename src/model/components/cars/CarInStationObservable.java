package model.components.cars;

import model.components.passengers.CarInStationObserver;

public interface CarInStationObservable {
    void registerObserver(CarInStationObserver inStationObserver);
    void removeObserver(CarInStationObserver inStationObserver);
    void notifyCarInStationObservers(String carStation);
}
