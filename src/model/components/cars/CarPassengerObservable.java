package model.components.cars;

import model.components.passengers.CarPassengerObserver;

public interface CarPassengerObservable {
    void registerObserver(CarPassengerObserver passengerObserver);
    void removeObserver(CarPassengerObserver passengerObserver);
    void notifyPassengerObservers();
}
