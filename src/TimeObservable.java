public interface TimeObservable {
    void registerObserver(TimeObserver timeObserver);
    void removeObserver(TimeObserver timeObserver);
    void notifyTimeObservers();
}
