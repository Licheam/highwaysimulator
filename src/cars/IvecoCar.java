package cars;

import model.highway.CarTrack;

public class IvecoCar extends BaseCar {
    public final int ID;

    public IvecoCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
        MAX_SPEED = 1.4;
        MAX_PASSENGERS = 21;
        PULL_OFF_TIME = 120000;
    }
}
