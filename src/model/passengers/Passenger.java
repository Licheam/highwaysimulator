package model.passengers;

import enumerates.CarDirection;
import model.cars.BaseCar;

import java.util.ArrayList;
import java.util.Random;

/**
 * Passenger is to present the passenger in our simulation.
 * <p>
 * it contains two features that indicate the boarding station and station that it gets off.
 * it's constructor calculates the station it gets off in a average probability
 */
public class Passenger implements CarInStationObserver {
    private String boardingStation;
    private String getOffStation;
    private CarDirection direction;
    private int P_GZ;
    private int P_CP;
    private int P_WG;
    private int P_XP;
    private int P_XY;
    private int P_EndStation;
    private static final ArrayList<String> CAR_STATION = new ArrayList<>() {{
        add("GZ");
        add("CP");
        add("WG");
        add("XP");
        add("XY");
        add("XN");
        add("BJ");
    }};

    public Passenger(String boardingStation) {
        this.boardingStation = boardingStation;
        P_GZ = P_CP = P_WG = P_XP = P_XY = P_EndStation = 20;
        if ("BJ".equals(this.boardingStation)) {
            direction = CarDirection.Forward;
        } else {
            direction = CarDirection.Backward;
        }

        this.getOffStation = getGetOffStation(direction);
    }

    private String getGetOffStation(CarDirection direction) {
        Random r = new Random();
        double random = r.nextLong() * 120;
        double bias = r.nextDouble() * 2.5;
        if (direction == CarDirection.Backward) {
            bias = -bias;
        }

        modifyProbability(bias);

        if (random < P_GZ) {
            getOffStation = "GZ";
        } else if (random < P_GZ + P_CP) {
            getOffStation = "CP";
        } else if (random < P_GZ + P_CP + P_WG) {
            getOffStation = "WG";
        } else if (random < P_GZ + P_CP + P_WG + P_XP) {
            getOffStation = "XP";
        } else if (random < P_GZ + P_CP + P_WG + P_XP + P_XY) {
            getOffStation = "XY";
        } else if (random <= P_GZ + P_CP + P_WG + P_XP + P_XY + P_EndStation) {
            if (direction == CarDirection.Forward) {
                getOffStation = "XN";
            } else {
                getOffStation = "BJ";
            }
        }

        return getOffStation;
    }

    private void modifyProbability(double bias) {
        P_GZ -= bias * 3;
        P_CP -= bias * 2;
        P_XP += bias * 0.5;
        P_XY += bias * 1.5;
        P_EndStation += bias * 3;
    }


    public void changeProbability(String station, int probability) {
        assert CAR_STATION.contains(station);
        assert probability <= 120;

        switch (station) {
            case "GZ" -> P_GZ = probability;
            case "CP" -> P_CP = probability;
            case "WG" -> P_WG = probability;
            case "XP" -> P_XP = probability;
            case "XY" -> P_XY = probability;
            case "XN", "BJ" -> P_EndStation = probability;
        }

    }

    @Override
    public void updateCarInStation(BaseCar car, String carStation) {
        if (carStation.equals(getOffStation)) {
            car.removePassenger(this);
        }
    }
}
