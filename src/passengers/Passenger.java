package passengers;

import cars.BaseCar;

import java.util.Random;

public class Passenger implements CarInStationObserver {
    private String boardingStation;
    private String getOffStation;
    private final double randomGap = 16.67;

    public Passenger(String boardingStation) {
        this.boardingStation = boardingStation;
        Random r = new Random();
        double random = r.nextDouble() * 100;

        if (random < randomGap) {
            getOffStation = "GZ";
        } else if (random < randomGap * 2) {
            getOffStation = "CP";
        } else if (random < randomGap * 3) {
            getOffStation = "WG";
        } else if (random < randomGap * 4) {
            getOffStation = "XP";
        } else if (random < randomGap * 5) {
            getOffStation = "XY";
        } else if (random < randomGap * 6) {
            if ("BJ".equals(this.boardingStation)) {
                getOffStation = "XN";
            } else {
                getOffStation = "BJ";
            }
        }
    }

    @Override
    public void updateCarInStation(BaseCar car) {
        if (car.getLocation().equals(getOffStation)) {
            car.
        }
    }
}
