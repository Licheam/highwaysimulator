package model.highway;

public class BJCarStation extends BaseCarStation {

    private static final int DEFAULT_NUMBER_OF_VOLVE = 4;
    private static final int DEFAULT_NUMBER_OF_IVECO = 15;

    public BJCarStation(CarTrack track, CarDirection direction, int location, CarFactory carFactory) {
        super(track, direction, location, carFactory);
        for (int i = 1; i <= DEFAULT_NUMBER_OF_VOLVE; i++) {
            volveCars.add((VolveCar) carFactory.getCar(CarType.Volve, track));
        }
        for (int i = 1; i <= DEFAULT_NUMBER_OF_IVECO; i++) {
            ivecoCars.add((IvecoCar) carFactory.getCar(CarType.Iveco, track));
        }
    }


}
