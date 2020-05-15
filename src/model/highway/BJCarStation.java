package model.highway;

import exception.OverDepartException;
import exception.TimeErrorException;
import model.timer.TimeModel;

import java.text.SimpleDateFormat;

public class BJCarStation extends BaseCarStation {

    private static final int DEFAULT_NUMBER_OF_VOLVE = 5;
    private static final int DEFAULT_NUMBER_OF_IVECO = 12;
    private static final String DEFAULT_BEGIN_TIME_OF_VOLVE_FORMAT = "8:30";
    private static final String DEFAULT_BEGIN_TIME_OF_IVECO_FORMAT = "8:00";
    private static final String DEFAULT_END_TIME_OF_VOLVE_FORMAT = "17:30";
    private static final String DEFAULT_END_TIME_OF_IVECO_FORMAT = "18:00";
    private static long DEFAULT_BEGIN_TIME_OF_VOLVE;
    private static long DEFAULT_BEGIN_TIME_OF_IVECO;
    private static long DEFAULT_END_TIME_OF_VOLVE;
    private static long DEFAULT_END_TIME_OF_IVECO;
    private static final long DEFAULT_TIME_GAP_OF_VOLVE = 36000000;
    private static final long DEFAULT_TIME_GAP_OF_IVECO = 12000000;


    public BJCarStation(CarTrack track, CarDirection direction,
                        int location, CarFactory carFactory, TimeModel timeModel) {
        super(track, direction, location, carFactory, timeModel);
        for (int i = 1; i <= DEFAULT_NUMBER_OF_VOLVE; i++) {
            volveCars.add((VolveCar) carFactory.getCar(CarType.Volve, track));
        }
        for (int i = 1; i <= DEFAULT_NUMBER_OF_IVECO; i++) {
            ivecoCars.add((IvecoCar) carFactory.getCar(CarType.Iveco, track));
        }

        try {
            DEFAULT_BEGIN_TIME_OF_VOLVE = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                    .parse(DEFAULT_BEGIN_TIME_OF_VOLVE_FORMAT)
                    .getTime();
            DEFAULT_BEGIN_TIME_OF_IVECO = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                    .parse(DEFAULT_BEGIN_TIME_OF_IVECO_FORMAT)
                    .getTime();
            DEFAULT_END_TIME_OF_VOLVE = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                    .parse(DEFAULT_END_TIME_OF_VOLVE_FORMAT)
                    .getTime();
            DEFAULT_END_TIME_OF_IVECO = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
                    .parse(DEFAULT_END_TIME_OF_IVECO_FORMAT)
                    .getTime();
        } catch(Exception ignored) {}


    }

    @Override
    protected void simulateCarStation(long timeGap) throws TimeErrorException {
        if (timeGap <= 0) {
            throw new TimeErrorException();
        } else {
            if (DEFAULT_BEGIN_TIME_OF_VOLVE <= currentTime + timeGap
                    && currentTime + timeGap <= DEFAULT_END_TIME_OF_VOLVE) {
                if ((currentTime + timeGap - DEFAULT_BEGIN_TIME_OF_VOLVE) % DEFAULT_TIME_GAP_OF_VOLVE == 0) {
                    try {
                        track.dispatchCar(departCar(CarType.Volve), direction, location);
                    } catch (OverDepartException overDepartException) {
                        overDepartException.printStackTrace();
                    }
                }
            }

            if (DEFAULT_BEGIN_TIME_OF_IVECO <= currentTime + timeGap
                    && currentTime + timeGap <= DEFAULT_END_TIME_OF_IVECO) {
                if ((currentTime + timeGap - DEFAULT_BEGIN_TIME_OF_IVECO) % DEFAULT_TIME_GAP_OF_IVECO == 0) {
                    try {
                        track.dispatchCar(departCar(CarType.Iveco), direction, location);
                    } catch (OverDepartException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void updateTime() {
        super.updateTime();

        try {
            simulateCarStation(timeModel.getTime() - currentTime);
        } catch (TimeErrorException e) {
            e.printStackTrace();
        }
    }
}
