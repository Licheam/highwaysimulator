public class VolveCar extends BaseCar {
    private static int countCar;
    public final int ID;
    public static final double MAX_SPEED = 2.0;
    public static final int MAX_PASSENGERS = 40;

    public VolveCar(CarTrack track) {
        super(track);
        countCar++;
        ID = countCar;
    }
}
