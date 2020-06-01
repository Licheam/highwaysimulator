package model.highway;

import cars.BaseCar;
import enumerates.CarDirection;
import exceptions.TimeErrorException;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * CarTrack is to present a highway in our simulation.
 *
 * It contains packed cars with location etc., and the stations information.
 * It also preform the simulation of cars.
 * It fetches the time from TimeModel.
 */
public class CarTrack implements TimeObserver {
    private final TimeModel timeModel;
    private final ArrayList<CarPackage> cars = new ArrayList<>();
    private long currentTime;
    private final TreeMap<Integer, String> stationsDistributions = new TreeMap<>();

    CarTrack(TimeModel timeModel) {
        this.timeModel = timeModel;
        currentTime = 0;
    }

    public void addStation(String stationName, int location) {
        stationsDistributions.put(location, stationName);
    }

    public void dispatchCar(BaseCar car, CarDirection direction, int location) {
        cars.add(new CarPackage(car, direction, location));
    }

    private void moveCar(CarPackage car, long timeGap) {
        if (car.isPullingOff) {
            if (timeGap >= car.car.getPullOffTime() - car.pullingOffTime) {
                timeGap -= car.car.getPullOffTime() - car.pullingOffTime;
                car.isPullingOff = false;
                car.pullingOffTime = 0;
                if (timeGap > 0) {
                    moveCar(car, timeGap);
                }
            } else {
                car.pullingOffTime += timeGap;
            }
        } else {
            Map.Entry<Integer, String> nextStation = stationsDistributions.ceilingEntry(car.location + 1);
            if (nextStation == null) {

            } else {

            }
        }
    }

    private void simulateCars(long timeGap) throws TimeErrorException {
        if (timeGap <= 0) {
            throw new TimeErrorException();
        } else {
            for (CarPackage car : cars) {
                moveCar(car, timeGap);
            }
        }
    }

    @Override
    public void updateTime() {
        long updatedTime = timeModel.getTime();
        try {
            simulateCars(updatedTime - currentTime);
        } catch (TimeErrorException e) {
            e.printStackTrace();
        } finally {
            currentTime = updatedTime;
        }
    }
}

/**
 * A Package that includes the specific information of a car on track.
 * Specially created for CarTrack.
 */
class CarPackage {
    public BaseCar car;
    public CarDirection direction;
    public int location;
    public boolean isPullingOff;
    public long pullingOffTime;

    public CarPackage(BaseCar car, CarDirection direction, int location) {
        this.car = car;
        this.direction = direction;
        this.location = location;
        isPullingOff = false;
        pullingOffTime = 0;
    }
}