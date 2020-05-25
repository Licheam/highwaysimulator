package passengers;

import cars.BaseCar;

import java.util.Random;

public class Passenger implements CarInStationObserver {
    private String boardingStation;
    private String GetOffStation;
    private final double randomGap = 16.67;
    public Passenger(String boardingStation) {
        this.boardingStation = boardingStation;
        Random r = new Random();
        double random = r.nextDouble() * 100;
        if (random < randomGap){
            GetOffStation = "GZ";
        }else if (random < randomGap*2){
            GetOffStation = "CP";
        }else if (random < randomGap*3){
            GetOffStation = "WG";
        }else if (random < randomGap*4){
            GetOffStation = "XP";
        }else if (random < randomGap*5){
            GetOffStation = "XY";
        }else if (random < randomGap*6){
            if ("BJ".equals(this.boardingStation)){
                GetOffStation = "XN";
            }else {
                GetOffStation = "BJ";
            }
        }
    }

    @Override
    public void updateCarInStation(BaseCar car) {
        if (car.getLocation().equals(GetOffStation)){
            car.
        }
    }
}
