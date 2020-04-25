package model.highway;

import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CarStation implements CarStationObservable, TimeObserver {
    private final String name;
    private final CarTrack track;
    private final ArrayList<CarStationObserver> carStationObservers = new ArrayList<>();
    private final Queue<VolveCar> volveCars = new LinkedList<>();
    private final Queue<IvecoCar> ivecoCars = new LinkedList<>();

    public CarStation(CarTrack track, String name) {
        this.track = track;
        this.name = name;
    }

    public String getName() {
        return name;
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
    public String toString() {
        return name + " Station";
    }

    @Override
    public void updateTime() {

    }
}
