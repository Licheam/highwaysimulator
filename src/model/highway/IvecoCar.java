package model.highway;

public class IvecoCar extends BaseCar {
    public final int ID;
    public static final double MAX_SPEED = 2.0;
    public static final int MAX_PASSENGERS = 40;

    public IvecoCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
    }
}
