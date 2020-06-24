package model.components.stations;

import model.components.cars.CarObserver;

public interface CarStationObservable {
    void registerObserver(CarStationObserver carStationObserver);
    void removeObserver(CarStationObserver carStationObserver);
    void notifyCarStationObservers();
    void registerAllCars(CarObserver carObserver);
}
