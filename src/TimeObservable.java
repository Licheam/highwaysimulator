public interface TimeObservable {
    void registerTimeObserver(TimeObserver timeObserver);
    void removeTimeObserver(TimeObserver timeObserver);
    void notifyTimeObservers();
}
