package model.highway;

import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseCarStation implements CarStationObservable, TimeObserver {
    private final CarFactory carFactory;
    private final CarTrack track;
    private final CarDirection direction;
    private final int location;
    private final ArrayList<CarStationObserver> carStationObservers = new ArrayList<>();
    protected final Queue<VolveCar> volveCars = new LinkedList<>();
    protected final Queue<IvecoCar> ivecoCars = new LinkedList<>();

    public BaseCarStation(CarTrack track, CarDirection direction, int location, CarFactory carFactory) {
        this.track = track;
        this.direction = direction;
        this.location = location;
        this.carFactory = carFactory;
    }

    public CarTrack getTrack() {
        return track;
    }

    public int getNumberOfCars(CarType carType) {
        if (carType == CarType.Volve) {
            return volveCars.size();
        } else if (carType == CarType.Iveco) {
            return ivecoCars.size();
        } else {
            return 0;
        }
    }

    public void returnCar(BaseCar car) {
        if (car instanceof VolveCar) {
            volveCars.offer((VolveCar)car);
        } else if (car instanceof IvecoCar) {
            ivecoCars.offer((IvecoCar)car);
        }
    }

    public BaseCar departCar(CarType carType) {
        if (carType == CarType.Volve) {
            return volveCars.poll();
        } else if (carType == CarType.Iveco) {
            return ivecoCars.poll();
        } else {
            return null;
        }
    }

    @Override
    public void registerObserver(CarStationObserver carStationObserver) {
        carStationObservers.add(carStationObserver);
    }

    @Override
    public void removeObserver(CarStationObserver carStationObserver) {
        carStationObservers.remove(carStationObserver);
    }

    @Override
    public void notifyCarStationObservers() {
        for (CarStationObserver carStationObserver : carStationObservers) {
            carStationObserver.updateCarStation(this);
        }
    }

    @Override
    public void updateTime() {

    }
}
