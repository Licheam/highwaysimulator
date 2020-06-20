package model.stations;

import enumerates.CarDirection;
import enumerates.CarType;
import model.cars.IvecoCar;
import model.cars.VolveCar;
import model.highway.CarTrack;
import model.timer.TimeModel;

import java.text.SimpleDateFormat;

public class XNCarStation extends BaseCarStation {

    public XNCarStation(CarTrack track, CarDirection direction, double location,
                        CarFactory carFactory, TimeModel timeModel) {
        super(track, direction, location, carFactory, timeModel);

        DEFAULT_NUMBER_OF_VOLVE = 5;
        DEFAULT_NUMBER_OF_IVECO = 12;
        DEFAULT_BEGIN_TIME_OF_VOLVE_FORMAT = "8:30";
        DEFAULT_BEGIN_TIME_OF_IVECO_FORMAT = "8:00";
        DEFAULT_END_TIME_OF_VOLVE_FORMAT = "17:30";
        DEFAULT_END_TIME_OF_IVECO_FORMAT = "18:00";
        DEFAULT_TIME_GAP_OF_VOLVE = 36000000;
        DEFAULT_TIME_GAP_OF_IVECO = 12000000;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= DEFAULT_NUMBER_OF_VOLVE; i++) {
            volveCars.add((VolveCar) carFactory.getCar(CarType.Volve, track));
        }
        for (int i = 1; i <= DEFAULT_NUMBER_OF_IVECO; i++) {
            ivecoCars.add((IvecoCar) carFactory.getCar(CarType.Iveco, track));
        }
    }


    @Override
    public String toString() {
        return "XN";
    }
}
