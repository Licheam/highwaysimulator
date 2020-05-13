package model.highway;

import model.timer.TimeObserver;

import java.util.ArrayList;

public class CarTrack implements TimeObserver {
    ArrayList<CarPackage> cars = new ArrayList<>();

    public void dispatchCar(BaseCar car, CarDirection direction, int location) {
        cars.add(new CarPackage(car, direction, location));
    }

    @Override
    public void updateTime() {

    }
}

class CarPackage {
    public BaseCar car;
    public CarDirection direction;
    public int location;

    public CarPackage(BaseCar car, CarDirection direction, int location) {
        this.car = car;
        this.direction = direction;
        this.location = location;
    }
}