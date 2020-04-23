public interface CarTrackObservable {
    void registerObserver(CarTrackObserver carTrackObserver);
    void removeObserver(CarTrackObserver carTrackObserver);
    void notifyObservers();
}
