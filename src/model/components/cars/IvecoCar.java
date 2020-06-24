package model.components.cars;

import model.components.highway.CarTrack;

/**
 * @author wangmengxi
 *
 * It's a prototype of IvecoCar
 * It's used to set special constance for Iveco,
 * also to Override the toString function.
 */
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IvecoCar) {
            return this.ID == ((IvecoCar) obj).getID();
        }
        return false;
    }
}
