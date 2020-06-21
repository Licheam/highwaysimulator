package model.timer;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * TimeModel is a time unit that offer the information about time.
 * <p>
 * it contains a series of methods interface that handel operations of starting, pausing, reseting time.
 */
public class TimeModel implements TimeObservable {
    private static final String DEFAULT_DATE_FORMAT = "HH:mm";
    private static final String DEFAULT_START_TIME = "07:30";
    private static final String DEFAULT_END_TIME = "18:00";
    private static final long DEFAULT_SIMULATED_TIME_GAP = 60000;
    //    private static final long DEFAULT_UPDATE_TIME_GAP = 1000;
    private static final long DEFAULT_UPDATE_TIME_GAP = 100;
    private long startTime;
    private long endTime;
    private Time time;
    private boolean isToStop = false;
    private volatile boolean isRunning = false;
    ArrayList<TimeObserver> timeObservers = new ArrayList<>();


    public long getStartTime() {
        try {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                    .parse(DEFAULT_START_TIME)
                    .getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return startTime;
    }

    @SuppressWarnings("BusyWait")
    private void simulateTime() {
        isRunning = true;
        notifyTimeObservers();
        while (!isToStop) {
            try {
                sleep(DEFAULT_UPDATE_TIME_GAP);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            time.setTime(time.getTime() + DEFAULT_SIMULATED_TIME_GAP);
            notifyTimeObservers();

            if (time.getTime() == endTime) {
                isToStop = true;
            }
        }
        isRunning = false;
    }

    public void start() {
        try {
            start(new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                            .parse(DEFAULT_START_TIME)
                            .getTime()
                    , new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                            .parse(DEFAULT_END_TIME)
                            .getTime()
            );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void start(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.time = new Time(startTime);
        isToStop = false;
        simulateTime();
    }

    public void pause() {
        isToStop = true;
    }

    public void restart() {
        reset();
        start(startTime, endTime);
    }

    public void reset() {
        if (!isToStop) {
            isToStop = true;
            while (isRunning) {
                Thread.onSpinWait();
            }
        }

        time.setTime(startTime);
    }

    public long getTime() {
        return time.getTime();
    }

    public String getFormatTime() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(time.getTime());
    }


    @Override
    public void registerObserver(TimeObserver timeObserver) {
        this.timeObservers.add(timeObserver);
    }

    @Override
    public void removeObserver(TimeObserver timeObserver) {
        this.timeObservers.remove(timeObserver);
    }

    @Override
    public void notifyTimeObservers() {
        for (TimeObserver timeObserver : timeObservers) {
            timeObserver.updateTime(this);
        }
    }
}
