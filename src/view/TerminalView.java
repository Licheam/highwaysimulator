package view;

import enumerates.CarDirection;
import enumerates.CarType;
import model.cars.BaseCar;
import model.cars.IvecoCar;
import model.cars.VolveCar;
import model.highway.CarPackage;
import model.highway.CarPositionObserver;
import model.highway.CarTrack;
import model.highway.CarTrackObserver;
import model.passengers.CarPassengerObserver;
import model.stations.BJCarStation;
import model.stations.BaseCarStation;
import model.stations.CarStationObserver;
import model.stations.XNCarStation;
import model.timer.TimeModel;
import model.timer.TimeObserver;

/**
 * view.View is a class that demonstrate the data
 * <p>
 * it includes time, model.cars and model.passengers in the XNCarStation and BJCarStation,
 * model.cars in the CarTrack.
 */
public class TerminalView implements TimeObserver, CarStationObserver, CarTrackObserver,
        CarPassengerObserver,CarPositionObserver{
    public void printDetail(TimeModel time, XNCarStation XNCarStation, BJCarStation BJCarStation,
                            CarTrack track) {
        updateTime(time);
        System.out.println("西安站内：");
        System.out.print("沃尔沃客车：");
        for (int ID : XNCarStation.getIDOfCars(CarType.Volve)) {
            System.out.print(ID + "\t");
        }
        System.out.println();
        System.out.print("依维柯客车：");
        for (int ID : XNCarStation.getIDOfCars(CarType.Iveco)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        System.out.println("等待乘客：" + XNCarStation.getNumberOfPassengers());
        System.out.println("-----------------------------------------------------");
        System.out.println("宝鸡站内：");
        System.out.print("沃尔沃客车：");
        for (int ID : BJCarStation.getIDOfCars(CarType.Volve)) {
            System.out.print(ID + "\t");
        }
        System.out.println();
        System.out.print("依维柯客车：");
        for (int ID : BJCarStation.getIDOfCars(CarType.Iveco)) {
            System.out.print(ID + "\t");
        }
        System.out.println();
        System.out.println("等待乘客：" + BJCarStation.getNumberOfPassengers());
        System.out.println("-----------------------------------------------------");
        System.out.println("正在高速路上行驶的车辆：");
        for (CarPackage car : track.getCars()) {
            printIDOfCar(track, car);
            System.out.print("车辆位置：");
            printCarLocation(track, car);
            System.out.println("乘客数：" + car.car.getNumberOfPassengers());
        }
        System.out.println("-----------------------------------------------------");
    }

    private void printCarLocation(CarTrack track, CarPackage car) {
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

    public void printIDOfCar(CarTrack track, CarPackage car) {
        if (car.car instanceof VolveCar) {
            System.out.println("Volve:" + ((VolveCar) car.car).ID);
        } else if (car.car instanceof IvecoCar) {
            System.out.println("Iveco:" + ((IvecoCar) car.car).ID + car.car.getLocation());
        }
    }

    @Override
    public void updateCarPosition(BaseCar car) {

    }

    @Override
    public void updateCarTrack(CarTrack track) {

    }

    @Override
    public void updateCarPassenger(BaseCar car) {

    }

    @Override
    public void updateCarStation(BaseCarStation carStation) {

    }

    @Override
    public void updateTime(TimeModel time) {
        System.out.println("当前时间为：" + time.getTime());
    }
}