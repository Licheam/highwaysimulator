package cars;

import model.highway.CarTrack;

public class VolveCar extends BaseCar {
    public final int ID;
    private static final double MAX_SPEED = 2.0;
    private static final int MAX_PASSENGERS = 40;
    private static final long PULL_OFF_TIME = 120000;

    public VolveCar(CarTrack track, int ID) {
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
