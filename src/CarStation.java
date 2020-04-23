import java.util.ArrayList;

public class CarStation implements CarStationObservable, TimeObserver {
    private final String name;
    private final CarTrack track;
    private final ArrayList<CarStationObserver> carStationObservers = new ArrayList<>();

    public CarStation(CarTrack track, String name) {
        this.track = track;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CarTrack getTrack() {
        return track;
    }

    @Override
    public void registerObserver(CarStationObserver carStationObserver) {
        carStationObservers.add(carStationObserver);
    }

    @Override
    public void removeObserver(CarStationObserver carStationObserver) {
        carStationObservers.remove(carStationObserver);
    }

    @Override
    public void notifyCarStationObservers() {
        for (CarStationObserver carStationObserver : carStationObservers) {
            carStationObserver.updateCarStation(this);
        }
    }

    @Override
    public String toString() {
        return name + " Station";
    }

    @Override
    public void updateTime() {

    }
}
