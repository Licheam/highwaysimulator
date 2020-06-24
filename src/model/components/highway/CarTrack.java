package model.components.highway;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import exceptions.TimeErrorException;
import model.components.cars.BaseCar;
import model.components.cars.IvecoCar;
import model.components.cars.VolveCar;
import model.components.stations.BaseCarStation;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wangmengxi
 *
 * CarTrack is the highway.
 *
 * It is Observable for CarTrackObserver.
 * And it simulate the movement of the cars along the way.
 */
public class CarTrack implements TimeObserver, CarTrackObservable {
    private final ArrayList<CarTrackObserver> carTrackObservers = new ArrayList<>();
    private TimeModel timeModel;
    private final ArrayList<CarPackage> cars = new ArrayList<>();
    private final ArrayList<CarPackage> returnedCars = new ArrayList<>();
    private long currentTime;
    private final TreeMap<Double, String> stationsDistributions = new TreeMap<>();
    private BaseCarStation carInitialStation, carTerminalStation;

    public CarTrack(TimeModel timeModel) {
        this.timeModel = timeModel;
        timeModel.registerObserver(this);
        currentTime = timeModel.getStartTime();
    }

    public TreeMap<Double, String> getStationsDistributions() {
        return stationsDistributions;
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
        notifyObservers();
    }

    public void returnCar(BaseCarStation carStation, CarPackage car) {
        carStation.returnCar(car.car);
        returnedCars.add(car);
    }

    public void eraseReturnedCars() {
        if (returnedCars.size()==0) {return;}

        for (CarPackage car : returnedCars) {
            cars.remove(car);
        }
        returnedCars.clear();

        notifyObservers();
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
            } else if (car.direction == CarDirection.Backward) {
                Map.Entry<Double, String> nextStation = stationsDistributions.floorEntry(car.location - 0.1);
                if (car.location - nextStation.getKey() <= timeGap * car.car.getMaxSpeed()) {
                    timeGap -= (car.location - nextStation.getKey()) / car.car.getMaxSpeed();
                    car.location = nextStation.getKey();
                    car.isPullingOff = true;
                    car.car.notifyCarInStationObservers(nextStation.getValue());
                    if (nextStation.getValue().equals(carInitialStation.toString())) {
                        returnCar(carInitialStation, car);
                    } else {
                        moveCar(car, timeGap);
                    }
                } else {
                    car.location -= timeGap * car.car.getMaxSpeed();
                }
            }
        }
    }

    private void simulateCars(double timeGap) throws TimeErrorException {
        if (timeGap < 0) {
            throw new TimeErrorException();
        } else {
            for (CarPackage car : cars) {
                moveCar(car, timeGap);
                try {
                    car.car.notifyPositionObservers(car.location, car.direction);
                } catch (LocationErrorException locationErrorException) {
                    locationErrorException.printStackTrace();
                }
            }
            eraseReturnedCars();
        }
    }

    public int getNumberOfVolveCars() {
        int numberOfVolveCar = 0;
        for (CarPackage car : cars) {
            if (car.car instanceof VolveCar) {
                numberOfVolveCar++;
            }
        }
        return numberOfVolveCar;
    }

    public int getNumberOfIvecoCars() {
        int numberOfIvecoCar = 0;
        for (CarPackage car : cars) {
            if (car.car instanceof IvecoCar) {
                numberOfIvecoCar++;
            }
        }
        return numberOfIvecoCar;
    }

    public Map.Entry<Double, String> getLocationDetails(double location, CarDirection direction)
            throws LocationErrorException {
        Map.Entry<Double, String> lastStation = null;
        if (direction == CarDirection.Forward) {
            lastStation = stationsDistributions.floorEntry(location - 0.1);
        } else if (direction == CarDirection.Backward) {
            lastStation = stationsDistributions.ceilingEntry(location + 0.1);
        }

        if (lastStation == null) {
            throw new LocationErrorException();
        } else {
            return Map.entry(location - lastStation.getKey(), lastStation.getValue());
        }
    }

    @Override
    public void updateTime(TimeModel timeModel) {
        long updatedTime = timeModel.getTime();
        try {
            simulateCars(updatedTime - currentTime);
        } catch (TimeErrorException e) {
            e.printStackTrace();
        } finally {
            currentTime = updatedTime;
        }
    }

    @Override
    public void registerObserver(CarTrackObserver carTrackObserver) {
        carTrackObservers.add(carTrackObserver);
    }

    @Override
    public void removeObserver(CarTrackObserver carTrackObserver) {
        carTrackObservers.remove(carTrackObserver);
    }

    @Override
    public void notifyObservers() {
        for (CarTrackObserver carTrackObserver : carTrackObservers) {
            carTrackObserver.updateCarTrack(this);
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

