package model.highway;

import enumerates.CarDirection;
import model.cars.BaseCar;

/**
 * A Package that includes the specific information of a car on track.
 * Specially created for CarTrack.
 */
public class CarPackage {
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
