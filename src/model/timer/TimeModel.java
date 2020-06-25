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
public class TimeModel implements TimeObservable, TimerStateObservable {
    private static final String DEFAULT_DATE_FORMAT = "HH:mm";
    private static final String DEFAULT_START_TIME = "07:30";
    private static final String DEFAULT_END_TIME = "18:00";
    private long simulatedTimeGap = 60000;
    private long updateTimeGap = 1000;
    private long startTime;
    private long endTime;
    private Time time;
    private boolean isToStop = false;
    private boolean paused = false;
    private volatile boolean running = false;
    private final ArrayList<TimeObserver> timeObservers = new ArrayList<>();
    private final ArrayList<TimerStateObserver> timerStateObservers = new ArrayList<>();


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

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    private void setRunning(boolean running) {
        this.running = running;
        notifyTimerStateObservers();
    }

    private void setPaused(boolean paused) {
        this.paused = paused;
        notifyTimerStateObservers();
    }

    @SuppressWarnings("BusyWait")
    private void simulateTime() {
        setRunning(true);
        notifyTimeObservers();
        while (!isToStop) {
            try {
                sleep(updateTimeGap);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            time.setTime(time.getTime() + simulatedTimeGap);
            notifyTimeObservers();

            if (time.getTime() == endTime) {
                isToStop = true;
                reset();
            }
        }
        setRunning(false);
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
        setPaused(false);
        simulateTime();
    }

    class SwitchStateRunnable implements Runnable {
        @Override
        public void run() {
            if (running) {
                isToStop = true;
                setPaused(true);
            } else if (paused) {
                isToStop = false;
                setPaused(false);
                simulateTime();
            } else {
                start();
            }
        }
    }

    public void switchState() {
        new Thread(new SwitchStateRunnable()).start();
    }

    public void restart() {
        reset();
        start(startTime, endTime);
    }

    public void reset() {
        if (!isToStop) {
            isToStop = true;
            while (running) {
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

    @Override
    public void registerObserver(TimerStateObserver timerStatesObserver) {
        timerStateObservers.add(timerStatesObserver);
    }

    @Override
    public void removeObserver(TimerStateObserver timerStateObserver) {
        timerStateObservers.remove(timerStateObserver);
    }

    @Override
    public void notifyTimerStateObservers() {
        for (TimerStateObserver timerStateObserver : timerStateObservers) {
            timerStateObserver.updateTimerState();
        }
    }
}
