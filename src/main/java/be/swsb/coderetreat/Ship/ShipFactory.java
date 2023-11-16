package be.swsb.coderetreat.Ship;

import be.swsb.coderetreat.Coordinate;
import be.swsb.coderetreat.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShipFactory {
    public static Ship createShip(ShipType shipType, Direction direction, Coordinate startPosition) {
        List<Coordinate> shipCoordinates = new ArrayList<>();

        IntStream.range(0, shipType.getSize()).forEach(i -> {
            int xCoordinate = direction == Direction.HORIZONTAL ? startPosition.xCoordinate + i : startPosition.xCoordinate;
            int yCoordinate = direction == Direction.VERTICAL ? startPosition.yCoordinate + i : startPosition.yCoordinate;
            shipCoordinates.add(new Coordinate(xCoordinate, yCoordinate));
        });

        return new Ship(shipType, shipCoordinates);
    }
}
