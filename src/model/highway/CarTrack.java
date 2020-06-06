package model.highway;

import cars.BaseCar;
import cars.IvecoCar;
import cars.VolveCar;
import enumerates.CarDirection;
import enumerates.CarType;
import exceptions.TimeErrorException;
import model.timer.TimeModel;
import model.timer.TimeObserver;
import stations.BaseCarStation;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * CarTrack is to present a highway in our simulation.
 * <p>
 * It contains packed cars with location etc., and the stations information.
 * It also preform the simulation of cars.
 * It fetches the time from TimeModel.
 */
public class CarTrack implements TimeObserver {
    private TimeModel timeModel;
    private final ArrayList<CarPackage> cars = new ArrayList<>();
    private long currentTime;
    private final TreeMap<Double, String> stationsDistributions = new TreeMap<>();
    private BaseCarStation carInitialStation, carTerminalStation;

    CarTrack(TimeModel timeModel) {
        this.timeModel = timeModel;
        currentTime = 0;
    }

    public void setTerminalStations(BaseCarStation carStation) {
        if (carStation.getLocation() == 0) {
            carInitialStation = carStation;
        } else {
            carTerminalStation = carStation;
        }
    }

    public void addStation(String stationName, double location) {
        stationsDistributions.put(location, stationName);
    }

    public void dispatchCar(BaseCar car, CarDirection direction, double location) {
        cars.add(new CarPackage(car, direction, location));
    }

    public void returnCar(BaseCarStation carStation, CarPackage car) {
        carStation.returnCar(car.car);
        cars.remove(car);
    }

    private void moveCar(CarPackage car, double timeGap) {
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
            if (car.direction == CarDirection.Forward) {
                Map.Entry<Double, String> nextStation = stationsDistributions.ceilingEntry(car.location + 0.1);
                if (nextStation.getKey() - car.location <= timeGap * car.car.getMaxSpeed()) {
                    timeGap -= (nextStation.getKey() - car.location) / car.car.getMaxSpeed();
                    car.location = nextStation.getKey();
                    car.isPullingOff = true;
                    car.car.notifyCarInStationObservers(nextStation.getValue());
                    if (nextStation.getValue().equals(carTerminalStation.toString())) {
                        returnCar(carTerminalStation, car);
                    } else {
                        moveCar(car, timeGap);
                    }
                } else {
                    car.location += timeGap * car.car.getMaxSpeed();
                }
            }
        }
    }

    private void simulateCars(double timeGap) throws TimeErrorException {
        if (timeGap <= 0) {
            throw new TimeErrorException();
        } else {
            for (CarPackage car : cars) {
                moveCar(car, timeGap);
                car.car.notifyPositionObservers();
            }
        }
    }

    private int getNumberOfVolveCar() {
        int numberOfVolveCar = 0;
        for (CarPackage car : cars) {
            if (car.car instanceof VolveCar) {
                numberOfVolveCar++;
            }
        }
        return numberOfVolveCar;
    }

    private int getNumberOfIvecoCar() {
        int numberOfIvecoCar = 0;
        for (CarPackage car : cars) {
            if (car.car instanceof IvecoCar) {
                numberOfIvecoCar++;
            }
        }
        return numberOfIvecoCar;
    }

    public int[] getIDOfCars(CarType carType) {
        if (carType == CarType.Volve) {
            int i = 0;
            int[] ID = new int[getNumberOfVolveCar()];
            for (CarPackage car : cars) {
                if (car.car instanceof VolveCar) {
                    ID[i++] = ((VolveCar) car.car).ID;
                }
            }
            return ID;
        } else if (carType == CarType.Iveco) {
            int i = 0;
            int[] ID = new int[getNumberOfIvecoCar()];
            for (CarPackage car : cars) {
                if (car.car instanceof IvecoCar) {
                    ID[i++] = ((IvecoCar) car.car).ID;
                }
            }
            return ID;
        } else {
            return new int[0];
        }
    }

    public double[] getLocationOfCars(CarType carType) {
        if (carType == CarType.Volve) {
            int i = 0;
            double[] Locations = new double[getNumberOfIvecoCar()];
            for (CarPackage car : cars) {
                Locations[i++] = car.location;
            }
            return Locations;
        } else if (carType == CarType.Iveco) {
            int i = 0;
            double[] Locations = new double[getNumberOfIvecoCar()];
            for (CarPackage car : cars) {
                Locations[i++] = car.location;
            }
            return Locations;
        } else {
            return new double[0];
        }
    }

    public ArrayList<CarPackage> getCars(CarType carType) {
        return cars;
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
    public double location;
    public boolean isPullingOff;
    public double pullingOffTime;

    public CarPackage(BaseCar car, CarDirection direction, double location) {
        this.car = car;
        this.direction = direction;
        this.location = location;
        isPullingOff = false;
        pullingOffTime = 0;
    }
}