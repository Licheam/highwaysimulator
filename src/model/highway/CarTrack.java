package model.highway;

import cars.BaseCar;
import enumerates.CarDirection;
import exceptions.TimeErrorException;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CarTrack implements TimeObserver {
    private final TimeModel timeModel;
    private final ArrayList<CarPackage> cars = new ArrayList<>();
    private long currentTime;
    private final static int DISTANCE_BJ = 0;
    private final static int DISTANCE_CP = 24;
    private final static int DISTANCE_GZ = 45;
    private final static int DISTANCE_WG = 107;
    private final static int DISTANCE_XP = 128;
    private final static int DISTANCE_XY = 152;
    private final static int DISTANCE_XN = 174;
    private final Map<Integer, String> stationDistance = new TreeMap<>(){{
        stationDistance.put(DISTANCE_BJ, "BJ");
        stationDistance.put(DISTANCE_GZ, "GZ");
        stationDistance.put(DISTANCE_CP, "CP");
        stationDistance.put(DISTANCE_WG, "WG");
        stationDistance.put(DISTANCE_XP, "XP");
        stationDistance.put(DISTANCE_XY, "XY");
        stationDistance.put(DISTANCE_XN, "XN");
    }};

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