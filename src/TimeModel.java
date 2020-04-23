import java.sql.Time;
import java.util.ArrayList;

public class TimeModel implements TimeObservable {
    private Time time;
    ArrayList<TimeObserver> timeObservers = new ArrayList<>();

    public void start() {}

    public void start(Time time) {}

    public void pause() {}

    public void reset() {}

    public Time getTime() {
        return time;    }

    @Override
    public void registerTimeObserver(TimeObserver timeObserver) {

    }

    @Override
    public void removeTimeObserver(TimeObserver timeObserver) {

    }

    @Override
    public void notifyTimeObservers() {

    }
}
