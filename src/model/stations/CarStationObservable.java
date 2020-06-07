package model.stations;

public interface CarStationObservable {
    void registerObserver(CarStationObserver carStationObserver);
    void removeObserver(CarStationObserver carStationObserver);
    void notifyCarStationObservers();
}
