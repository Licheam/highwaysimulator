import java.util.ArrayList;

public class VolveCar extends BaseCar {

    ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();

    @Override
    public void registerObserver(CarPositionObserver positionObserver) {
        positionObservers.add(positionObserver);
    }

    @Override
    public void removeObserver(CarPositionObserver positionObserver) {
        positionObservers.remove(positionObserver);
    }

    @Override
    public void notifyPositionObservers() {
        for (CarPositionObserver positionObserver : positionObservers) {
            positionObserver.updateCarPosition(this);
        }
    }

    @Override
    public void registerObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public void removeObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public void notifyPassengerObservers() {
        for (CarPassengerObserver passengerObserver : passengerObservers) {
            passengerObserver.updateCarPassenger(this);
        }
    }


}
