package be.swsb.coderetreat;

import static be.swsb.coderetreat.Battlefield.battlefieldSize;

public class Coordinate {
    public final int xCoordinate;
    public final int yCoordinate;

    public Coordinate(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public static int GetYCoordinate(int yCoordinate) {
        return (battlefieldSize - 1) - yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return xCoordinate == that.xCoordinate && yCoordinate == that.yCoordinate;
    }
}
