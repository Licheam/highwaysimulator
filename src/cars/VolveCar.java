package cars;

import model.highway.CarTrack;

public class VolveCar extends BaseCar {
    public final int ID;

    public VolveCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
        MAX_SPEED = 2.0;
        MAX_PASSENGERS = 40;
        PULL_OFF_TIME = 120000;
    }
}

