package model.timer;

public interface TimerStateObservable {
    void registerObserver(TimerStateObserver timerStatesObserver);
    void removeObserver(TimerStateObserver timerStateObserver);
    void notifyTimerStateObservers();
}
