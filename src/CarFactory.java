public class CarFactory {

    public VolveCar getVolveCar(CarTrack track) {
        return new VolveCar(track);
    }

    public IvecoCar getIvecoCar(CarTrack track) {
        return new IvecoCar(track);
    }
}
