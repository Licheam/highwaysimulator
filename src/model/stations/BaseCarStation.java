package model.stations;

import enumerates.CarDirection;
import enumerates.CarType;
import exceptions.OverDepartException;
import exceptions.TimeErrorException;
import model.cars.BaseCar;
import model.cars.CarObserver;
import model.cars.IvecoCar;
import model.cars.VolveCar;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.passengers.CarInStationObserver;
import model.passengers.CarPassengerObserver;
import model.passengers.Passenger;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author wangmengxi
 * <p>
 * BaseCarStation is a prototype for all the Terminal Station
 * like BJCarStation and XNCarStation.
 * <p>
 * It is obserable for passengers and cars.
 */
public abstract class BaseCarStation implements CarStationObservable, TimeObserver {

    protected static final String DEFAULT_DATE_FORMAT = "HH:mm";
    protected final CarFactory carFactory;
    protected final CarTrack track;
    protected final CarDirection direction;
    protected final double location;

    protected final ArrayList<CarStationObserver> carStationObservers = new ArrayList<>();
    protected final Queue<VolveCar> volveCars = new LinkedList<>();
    protected final Queue<IvecoCar> ivecoCars = new LinkedList<>();
    protected TimeModel timeModel;
    protected long currentTime;
    protected final Queue<Passenger> passengers = new LinkedList<>();
    protected int passengersArrivedPerMin;

    protected int DEFAULT_NUMBER_OF_VOLVE;
    protected int DEFAULT_NUMBER_OF_IVECO;
    protected String DEFAULT_BEGIN_TIME_OF_VOLVE_FORMAT;
    protected String DEFAULT_BEGIN_TIME_OF_IVECO_FORMAT;
    protected String DEFAULT_END_TIME_OF_VOLVE_FORMAT;
    protected String DEFAULT_END_TIME_OF_IVECO_FORMAT;
    protected long DEFAULT_BEGIN_TIME_OF_VOLVE;
    protected long DEFAULT_BEGIN_TIME_OF_IVECO;
    protected long DEFAULT_END_TIME_OF_VOLVE;
    protected long DEFAULT_END_TIME_OF_IVECO;
    protected long DEFAULT_TIME_GAP_OF_VOLVE;
    protected long DEFAULT_TIME_GAP_OF_IVECO;


    public BaseCarStation(CarTrack track, CarDirection direction, double location,
                          CarFactory carFactory, TimeModel timeModel) {
        this.track = track;
        this.direction = direction;
        this.location = location;
        this.carFactory = carFactory;
        this.timeModel = timeModel;
        timeModel.registerObserver(this);
        track.setTerminalStations(this);
        track.addStation(this.toString(), location);
        currentTime = timeModel.getStartTime();
        passengersArrivedPerMin = 3;
    }

    public double getLocation() {
        return location;
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

    public int[] getIDOfCars(CarType carType) {
        if (carType == CarType.Volve) {
            int i = 0;
            int[] ID = new int[this.getNumberOfCars(CarType.Volve)];
            for (VolveCar volveCar : volveCars) {
                ID[i++] = volveCar.getID();
            }
            return ID;
        } else if (carType == CarType.Iveco) {
            int i = 0;
            int[] ID = new int[this.getNumberOfCars(CarType.Iveco)];
            for (IvecoCar ivecoCar : ivecoCars) {
                ID[i++] = ivecoCar.getID();
            }
            return ID;
        } else {
            return new int[0];
        }
    }

    public void returnCar(BaseCar car) {
        if (car instanceof VolveCar) {
            volveCars.offer((VolveCar) car);
        } else if (car instanceof IvecoCar) {
            ivecoCars.offer((IvecoCar) car);
        }

        notifyCarStationObservers();
    }

    public BaseCar departCar(CarType carType) throws OverDepartException {
        BaseCar carToDepart;

        if (carType == CarType.Volve) {
            carToDepart = volveCars.poll();
        } else if (carType == CarType.Iveco) {
            carToDepart = ivecoCars.poll();
        } else {
            throw new OverDepartException();
        }

        notifyCarStationObservers();

        assert carToDepart != null;
        int passengersToBoard = Math.min(carToDepart.getMaxPassengers(), passengers.size());

        for (int i = 1; i <= passengersToBoard; i++) {
            carToDepart.addPassenger(passengers.poll());
        }
        return carToDepart;
    }

    public int getNumberOfPassengers() {
        return passengers.size();
    }

    protected void simulateCarStation(long timeGap) throws TimeErrorException {
        if (timeGap < 0) {
            throw new TimeErrorException();
        } else {
            if (DEFAULT_BEGIN_TIME_OF_VOLVE <= currentTime + timeGap
                    && currentTime + timeGap <= DEFAULT_END_TIME_OF_VOLVE) {
                if ((currentTime + timeGap - DEFAULT_BEGIN_TIME_OF_VOLVE) % DEFAULT_TIME_GAP_OF_VOLVE == 0) {
                    try {
                        track.dispatchCar(departCar(CarType.Volve), direction, location);
                    } catch (OverDepartException overDepartException) {
                        overDepartException.printStackTrace();
                    }
                }
            }

            if (DEFAULT_BEGIN_TIME_OF_IVECO <= currentTime + timeGap
                    && currentTime + timeGap <= DEFAULT_END_TIME_OF_IVECO) {
                if ((currentTime + timeGap - DEFAULT_BEGIN_TIME_OF_IVECO) % DEFAULT_TIME_GAP_OF_IVECO == 0) {
                    try {
                        track.dispatchCar(departCar(CarType.Iveco), direction, location);
                    } catch (OverDepartException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected void simulatePassengers(long timeGap) throws TimeErrorException {
        if (timeGap < 0) {
            throw new TimeErrorException();
        } else {
            for (int i = 1; i <= (double) timeGap / 60000; i++) {
                Random random = new Random();
                for (int j = 0; j < random.nextInt(passengersArrivedPerMin + 1); j++) {
                    passengers.add(new Passenger(this.toString(), location, track));
                }
            }
            notifyCarStationObservers();
        }
    }

    @Override
    public void updateTime(TimeModel timeModel) {
        long updatedTime = timeModel.getTime();
        try {
            simulatePassengers(updatedTime - currentTime);
            simulateCarStation(updatedTime - currentTime);
        } catch (TimeErrorException e) {
            e.printStackTrace();
        } finally {
            currentTime = updatedTime;
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
    public void registerAllCars(CarObserver carObserver) {
        for (BaseCar car : volveCars) {
            if (carObserver instanceof CarPassengerObserver) {
                car.registerObserver((CarPassengerObserver) carObserver);
            }
            if (carObserver instanceof CarPositionObserver) {
                car.registerObserver((CarPositionObserver) carObserver);
            }
            if (carObserver instanceof CarInStationObserver) {
                car.registerObserver((CarInStationObserver) carObserver);
            }
        }

        for (BaseCar car : ivecoCars) {
            if (carObserver instanceof CarPassengerObserver) {
                car.registerObserver((CarPassengerObserver) carObserver);
            }
            if (carObserver instanceof CarPositionObserver) {
                car.registerObserver((CarPositionObserver) carObserver);
            }
            if (carObserver instanceof CarInStationObserver) {
                car.registerObserver((CarInStationObserver) carObserver);
            }
        }
    }
}
