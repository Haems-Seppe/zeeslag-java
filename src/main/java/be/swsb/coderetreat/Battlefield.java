package be.swsb.coderetreat;

import be.swsb.coderetreat.Ship.Ship;
import be.swsb.coderetreat.Ship.ShipFactory;
import be.swsb.coderetreat.Ship.ShipType;

import java.util.ArrayList;
import java.util.List;

import static be.swsb.coderetreat.Coordinate.GetYCoordinate;

public class Battlefield {

    public Tile[][] ocean = new Tile[battlefieldSize][battlefieldSize];
    public final static int battlefieldSize = 10;
    private final List<Ship> ships = new ArrayList<>();

    public Battlefield() {
        createEmptyOcean();
    }

    private void createEmptyOcean() {
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                this.ocean[j][i] = new Tile(new Coordinate(i, GetYCoordinate(j)));
            }
        }
    }

    public void placeShip(Coordinate startPosition, Direction direction, ShipType shipType) {
        validateCoordinate(startPosition);
        checkIfShipFitsBattlefield(startPosition, direction, shipType.getSize());

        Ship ship = ShipFactory.createShip(shipType, direction, startPosition);

        checkIfTilesAreFree(ship);
        markTilesAsShip(ship);
        ships.add(ship);
    }

    public void checkIfAShipIsSunk() {
        ships.forEach(ship -> {
            if (ship.getCoordinates().stream().allMatch(coordinate -> getTile(coordinate).isHit())) {
                System.out.println("You sunk the " + ship.getShipType().name() + "!");
                markTilesAsSunk(ship);
                ship.markAsSunk();
            }
        });
    }

    public boolean checkIfAllShipsAreSunk() {
        return ships.stream().allMatch(Ship::isSunk);
    }

    public static void validateCoordinate(Coordinate coordinate) {
        if (coordinate.xCoordinate >= battlefieldSize || coordinate.yCoordinate >= battlefieldSize || coordinate.xCoordinate < 0 || coordinate.yCoordinate < 0) {
            throw new IllegalArgumentException("These coordinates are out of bounds.");
        }
    }

    private void markTilesAsSunk(Ship ship) {
        if (!ship.isSunk()) {
            List<Coordinate> coordinates = ship.getCoordinates();
            coordinates.forEach(coordinate -> getTile(coordinate).markAsSunk());
        }
    }

    private void markTilesAsShip(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        coordinates.forEach(coordinate -> getTile(coordinate).markAsShip());
    }

    private void checkIfTilesAreFree(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        coordinates.forEach(coordinate -> {
            if (!getTile(coordinate).isWater()) {
                throw new IllegalStateException("This ship overlaps with another ship.");
            }
        });
    }


    private void checkIfShipFitsBattlefield(Coordinate startPosition, Direction direction, int size) {
        boolean isHorizontal = direction == Direction.HORIZONTAL;
        int axisToCheck = isHorizontal ? startPosition.xCoordinate : startPosition.yCoordinate;
        if (axisToCheck + size >= battlefieldSize) {
            throw new IllegalArgumentException("This ship is too large to be placed here.");
        }
    }

    private Tile getTile(Coordinate coordinate) {
        return ocean[GetYCoordinate(coordinate.yCoordinate)][coordinate.xCoordinate];
    }
}
