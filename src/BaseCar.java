import java.util.ArrayList;

public abstract class BaseCar implements CarObservable, TimeObserver {
    private CarTrack track;
    private final ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    private final ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();
    private double speed = 0;

    public BaseCar(CarTrack track) {
        this.track = track;
    }

    public CarTrack getTrack() {
        return track;
    }

    public void setTrack(CarTrack track) {
        this.track = track;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public final void registerObserver(CarPositionObserver positionObserver) {
        positionObservers.add(positionObserver);
    }

    @Override
    public final void removeObserver(CarPositionObserver positionObserver) {
        positionObservers.remove(positionObserver);
    }

    @Override
    public final void notifyPositionObservers() {
        for (CarPositionObserver positionObserver : positionObservers) {
            positionObserver.updateCarPosition(this);
        }
    }

    @Override
    public final void registerObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public final void removeObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public final void notifyPassengerObservers() {
        for (CarPassengerObserver passengerObserver : passengerObservers) {
            passengerObserver.updateCarPassenger(this);
        }
    }

    @Override
    public void updateTime() {

    }
}

