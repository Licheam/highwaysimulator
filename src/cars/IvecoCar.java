package cars;

import model.highway.CarTrack;

public class IvecoCar extends BaseCar {
    public final int ID;
    public static final double MAX_SPEED = 2.0;
    public static final int MAX_PASSENGERS = 40;
    public static final long PULL_OFF_TIME = 120000;

    public IvecoCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
    }

    @Override
    public double getMaxSpeed() {
        return MAX_SPEED;
    }

    @Override
    public int getMaxPassengers() {
        return MAX_PASSENGERS;
    }

    @Override
    public long getPullOffTime() {
        return PULL_OFF_TIME;
    }
}
