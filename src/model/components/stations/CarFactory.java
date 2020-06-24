package model.components.stations;

import enumerates.CarType;
import model.components.cars.BaseCar;
import model.components.cars.IvecoCar;
import model.components.cars.VolveCar;
import model.components.highway.CarTrack;



/**
 * @author wangmengxi
 *
 * This is the factory of cars.
 * In order to distribute ID for each Cars.
 *
 */
public class CarFactory {
    private int countingVolve = 0;
    private int countingIveco = 0;

    public BaseCar getCar(CarType carType, CarTrack track) {
        if (carType == CarType.Volve) {
            countingVolve++;
            return new VolveCar(track, countingVolve);
        } else if (carType == CarType.Iveco) {
            countingIveco++;
            return new IvecoCar(track, countingIveco);
        }

        return null;
    }

}
