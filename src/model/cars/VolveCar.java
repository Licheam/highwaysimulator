package model.cars;

import model.highway.CarTrack;

public class VolveCar extends BaseCar {

    public VolveCar(CarTrack track, int ID) {
        super(track);
        this.ID = ID;
        MAX_SPEED = 2.0;
        MAX_PASSENGERS = 40;
        PULL_OFF_TIME = 120000;
    }

    @Override
    public String toString() {
        return "Volve";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VolveCar) {
            return this.ID == ((VolveCar) obj).getID();
        }
        return false;
    }

}

