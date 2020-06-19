package view;

import enumerates.CarDirection;
import enumerates.CarType;
import exceptions.LocationErrorException;
import model.cars.BaseCar;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.highway.CarTrackObserver;
import model.passengers.CarPassengerObserver;
import model.stations.BaseCarStation;
import model.stations.CarStationObserver;
import model.timer.TimeModel;
import model.timer.TimeObserver;

import java.util.Map;

/**
 * view.View is a class that demonstrate the data
 * <p>
 * it includes time, model.cars and model.passengers in the XNCarStation and BJCarStation,
 * model.cars in the CarTrack.
 */
public class TerminalView implements TimeObserver, CarStationObserver, CarTrackObserver,
        CarPassengerObserver, CarPositionObserver {

    @Override
    public void updateCarPosition(BaseCar car, double location, CarDirection direction) throws LocationErrorException {
        Map.Entry<Double, String> locationDetails = car.getTrack().getLocationDetails(location, direction);

        System.out.println("第" + car.getID() + "号" + car.toString());
        if (locationDetails.getKey() > 0) {
            System.out.println("当前位置: " + locationDetails.getValue() + "站以东" + Math.abs(locationDetails.getKey()) + "公里。");
        } else if (locationDetails.getKey() < 0) {
            System.out.println("当前位置: " + locationDetails.getValue() + "站以西" + Math.abs(locationDetails.getKey()) + "公里。");
        } else {
            System.out.println("当前位置: " + locationDetails.getValue() + "站中。");
        }

        if (direction.equals(CarDirection.Forward)) {
            System.out.println("方向为东");
        } else if (direction.equals(CarDirection.Backward)) {
            System.out.println("方向为西");
        }
    }

    @Override
    public void updateCarTrack(CarTrack track) {
        System.out.println("高速公路目前情况：");
        System.out.println("Volve数目：" + track.getNumberOfVolveCars());
        System.out.println("Iveco数目：" + track.getNumberOfIvecoCars());
    }

    @Override
    public void updateCarPassenger(BaseCar car) {
        System.out.println("第" + car.getID() + "号" + car.toString() + "当前乘客数目：" + car.getNumberOfPassengers());
    }

    @Override
    public void updateCarStation(BaseCarStation carStation) {
        System.out.println("车站：" + carStation.toString());
        System.out.println("候车乘客数目：" + carStation.getNumberOfPassengers());
        System.out.println("待发Volve数目：" + carStation.getNumberOfCars(CarType.Volve));
        System.out.println("待发Iveco数目：" + carStation.getNumberOfCars(CarType.Iveco));
    }

    @Override
    public void updateTime(TimeModel time) {
        System.out.println("当前时间为：" + time.getTime());
    }
}