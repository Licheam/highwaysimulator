package view;

import enumerates.CarDirection;
import enumerates.CarType;
import exceptions.LocationErrorException;
import model.cars.BaseCar;
import model.cars.IvecoCar;
import model.cars.VolveCar;
import model.highway.CarPackage;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.highway.CarTrackObserver;
import model.passengers.CarPassengerObserver;
import model.stations.BaseCarStation;
import model.stations.CarStationObserver;
import model.timer.TimeModel;
import model.timer.TimeObserver;

/**
 * view.View is a class that demonstrate the data
 * <p>
 * it includes time, model.cars and model.passengers in the XNCarStation and BJCarStation,
 * model.cars in the CarTrack.
 */
public class TerminalView implements TimeObserver, CarStationObserver, CarTrackObserver,
        CarPassengerObserver, CarPositionObserver {

    @Override
//  version that without CarPackage.
//    write your code under here. :D
    public void updateCarPosition(BaseCar car) {
    }

//  version that have CarPackage.
    public void updateCarPosition(CarTrack track) throws LocationErrorException {
        System.out.println("正在高速路上行驶的车辆：");
        for (CarPackage car : track.getCars()) {
            printIDOfCar(track, car);
            System.out.print("车辆位置：");
            printCarLocation(track, car);
            System.out.println("乘客数：" + car.car.getNumberOfPassengers());
        }
    }

    private void printCarLocation(CarTrack track, CarPackage car) throws LocationErrorException {
        String direction;
        // BJ to XN is forward.
        if (car.direction == CarDirection.Forward) {
            direction = "东";
        } else if (car.direction == CarDirection.Backward) {
            direction = "西";
        } else {
            direction = "";
        }
        System.out.println(track.getLocationDetails(car.location, car.direction).getKey()
                + "站" + "以" + direction
                + track.getLocationDetails(car.location, car.direction).getValue());
    }

    private void printIDOfCar(CarTrack track, CarPackage car) {
        if (car.car instanceof VolveCar) {
            System.out.println("Volve:" + ((VolveCar) car.car).ID);
        } else if (car.car instanceof IvecoCar) {
            System.out.println("Iveco:" + ((IvecoCar) car.car).ID + car.car.getLocation());
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