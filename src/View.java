import enumerates.CarType;
import model.highway.CarTrack;
import model.timer.TimeModel;
import stations.BJCarStation;
import stations.XNCarStation;

public class View {
    public void printDetail(TimeModel time, XNCarStation XNCarStation, BJCarStation BJCarStation,
                            CarTrack track) {
        System.out.println("当前时间为：" + time.getTime());
        System.out.println("西安站内：");
        System.out.print("沃尔沃客车：");
        for (int ID : XNCarStation.getIDOfCars(CarType.Volve)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        System.out.print("依维柯客车：");
        for (int ID : XNCarStation.getIDOfCars(CarType.Iveco)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        System.out.println("等待乘客：" + XNCarStation.);
        System.out.println("-----------------------------------------------------");
        System.out.println("宝鸡站内：");
        System.out.print("沃尔沃客车：");
        for (int ID : BJCarStation.getIDOfCars(CarType.Volve)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        System.out.print("依维柯客车：");
        for (int ID : BJCarStation.getIDOfCars(CarType.Iveco)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        System.out.println("等待乘客：" + BJCarStation.);
        System.out.println("-----------------------------------------------------");
        System.out.println("正在高速路上行驶的车辆：");
        System.out.println("沃尔沃客车：");
        for (int ID : track.getIDOfCars(CarType.Volve)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        for (double Location : track.getLocationOfCars(CarType.Volve)) {
            System.out.println(Location + "\t");
        }
        System.out.println("依维柯客车：");
        for (int ID : track.getIDOfCars(CarType.Iveco)) {
            System.out.print(ID + "\t");
        }
        System.out.println("");
        for (double Location : track.getLocationOfCars(CarType.Iveco)) {
            System.out.println(Location + "\t");
        }
        System.out.println("-----------------------------------------------------");
    }
}
