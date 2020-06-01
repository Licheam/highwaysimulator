package stations;

import cars.BaseCar;
import cars.IvecoCar;
import cars.VolveCar;
import enumerates.CarDirection;
import enumerates.CarType;
import exceptions.OverDepartException;
import exceptions.TimeErrorException;
import model.highway.CarTrack;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseCarStation implements CarStationObservable, TimeObserver {

    protected static final String DEFAULT_DATE_FORMAT = "HH:mm";
    protected final CarFactory carFactory;
    protected final CarTrack track;
    protected final CarDirection direction;
    protected final int location;
    protected final ArrayList<CarStationObserver> carStationObservers = new ArrayList<>();
    protected final Queue<VolveCar> volveCars = new LinkedList<>();
    protected final Queue<IvecoCar> ivecoCars = new LinkedList<>();
    protected TimeModel timeModel;
    protected long currentTime;


    public BaseCarStation(CarTrack track, CarDirection direction, int location, CarFactory carFactory, TimeModel timeModel) {
        this.track = track;
        this.direction = direction;
        this.location = location;
        this.carFactory = carFactory;
        this.timeModel = timeModel;
        track.addStation(this.toString(), location);
        currentTime = 0;
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

    public BaseCar departCar(CarType carType) throws OverDepartException {
        if (carType == CarType.Volve) {
            return volveCars.poll();
        } else if (carType == CarType.Iveco) {
            return ivecoCars.poll();
        } else {
            throw new OverDepartException();
        }
    }

    protected abstract void simulateCarStation(long timeGap) throws TimeErrorException;

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
}
