package model.highway;

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
