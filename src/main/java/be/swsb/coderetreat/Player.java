package be.swsb.coderetreat;

import be.swsb.coderetreat.Ship.ShipType;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final Battlefield battlefield;
    private final String name;

    private final List<Coordinate> shotsTaken = new ArrayList<>();

    public Player(String name) {
        this.battlefield = new Battlefield();
        this.name = name;
    }

    public boolean allShipsAreSunk() {
        return battlefield.checkIfAllShipsAreSunk();
    }

    public void placeShip(Coordinate startPosition, Direction direction, ShipType shipType) {
        battlefield.placeShip(startPosition, direction, shipType);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public List<Coordinate> getShotsTaken() {
        return shotsTaken;
    }

    public String getName() {
        return name;
    }
}
