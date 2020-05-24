package model.highway;

import cars.BaseCar;
import enumerates.CarDirection;
import exceptions.TimeErrorException;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;

public class CarTrack implements TimeObserver {
    private final TimeModel timeModel;
    private final ArrayList<CarPackage> cars = new ArrayList<>();
    private long currentTime;

    CarTrack(TimeModel timeModel) {
        this.timeModel = timeModel;
        currentTime = 0;
    }

    public void dispatchCar(BaseCar car, CarDirection direction, int location) {
        cars.add(new CarPackage(car, direction, location));
    }

    private void simulateCars(long timeGap) throws TimeErrorException {
        if (timeGap <= 0) {
            throw new TimeErrorException();
        } else {
            for (CarPackage car : cars) {
                if (car.isPullingOff) {
                } else {

                }
            }
        }
    }

    @Override
    public void updateTime() {
        try {
            simulateCars(timeModel.getTime() - currentTime);
        } catch (TimeErrorException e) {
            e.printStackTrace();
        }
    }
}

class CarPackage {
    public BaseCar car;
    public CarDirection direction;
    public int location;
    public boolean isPullingOff;
    public long pullingTime;

    public CarPackage(BaseCar car, CarDirection direction, int location) {
        this.car = car;
        this.direction = direction;
        this.location = location;
        isPullingOff = false;
        pullingTime = 0;
    }
}