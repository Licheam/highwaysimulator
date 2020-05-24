package stations;

import cars.BaseCar;
import cars.IvecoCar;
import cars.VolveCar;
import enumerates.CarType;
import model.highway.CarTrack;

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
