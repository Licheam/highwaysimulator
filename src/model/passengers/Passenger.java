package model.passengers;

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
    private static int P_GZ;
    private static int P_CP;
    private static int P_WG;
    private static int P_XP;
    private static int P_XY;
    private static int P_EndStation;
    private static final ArrayList<String> CARSTATION = new ArrayList<>(){{
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
        getOffStation = getGetOffStation();
    }

    private String getGetOffStation() {
        Random r = new Random();
        double random = r.nextDouble() * 120;

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
        } else if (random < P_GZ + P_CP + P_WG + P_XP + P_XY + P_EndStation) {
            if ("BJ".equals(this.boardingStation)) {
                getOffStation = "XN";
            } else {
                getOffStation = "BJ";
            }
        }

        return getOffStation;
    }

    public void changeProbability(String station, int probability) {
        assert CARSTATION.contains(station);
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
