package cars;

import model.highway.CarPositionObserver;
import passengers.CarInStationObserver;
import passengers.CarPassengerObserver;

public interface CarObservable {
    void registerObserver(CarPositionObserver positionObserver);
    void removeObserver(CarPositionObserver positionObserver);
    void notifyPositionObservers();
    void registerObserver(CarPassengerObserver passengerObserver);
    void removeObserver(CarPassengerObserver passengerObserver);
    void notifyPassengerObservers();
    void registerObserver(CarInStationObserver carInStationObserver);
    void removeObserver(CarInStationObserver carInStationObserver);
    void notifyCarInStationObservers();
}
