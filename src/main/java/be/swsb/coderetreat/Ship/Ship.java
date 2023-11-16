package be.swsb.coderetreat.Ship;

import be.swsb.coderetreat.Coordinate;

import java.util.List;

public class Ship {
    private final ShipType shipType;

    private boolean isSunk = false;
    private final List<Coordinate> coordinates;

    public Ship(ShipType shipType, List<Coordinate> coordinates) {
        this.shipType = shipType;
        this.coordinates = coordinates;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void markAsSunk() {
        isSunk = true;
    }

    public ShipType getShipType() {
        return shipType;
    }
}
