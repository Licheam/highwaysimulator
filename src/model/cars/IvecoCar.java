package model.cars;

import model.highway.CarTrack;

public class IvecoCar extends BaseCar {

    public IvecoCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
        MAX_SPEED = 1.4;
        MAX_PASSENGERS = 21;
        PULL_OFF_TIME = 120000;
    }

    @Override
    public String toString() {
        return "Iveco";
    }
}
