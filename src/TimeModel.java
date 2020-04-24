import java.sql.Time;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class TimeModel implements TimeObservable {
    private Time time;
    private boolean check = true;
    ArrayList<TimeObserver> timeObservers = new ArrayList<>();

    public void start() {
        //creat a default time instance
        start(0, 0, 0);
    }

    public void start(int hour, int minutes, int second) {
        //creat a time instance by static method
        this.time.setTime(second*1000 + minutes*60*1000 + hour*60*60*1000);
    }

    public void pause() {
        // sleep 1 second
        check = false;
        getTime();
    }

    public void reset() {
        // reset the time to the start
        start();
    }

    public Time getTime() {
        // every time System getTime, return original time+1
        // if check is false, sleep
        if (check){
            time.setTime(time.getTime()+1000);
        }else {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    @Override
    public void registerObserver(TimeObserver timeObserver) {

    }

    @Override
    public void removeObserver(TimeObserver timeObserver) {

    }

    @Override
    public void notifyTimeObservers() {

    }
}
