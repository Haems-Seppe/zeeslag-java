package be.swsb.coderetreat;

public class Tile {
    private final Coordinate coordinate;
    private TileType type;


    public Tile(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.type = TileType.WATER;
    }

    public boolean isWater() {
        return TileType.WATER == type;
    }

    public boolean isShip() {
        return TileType.SHIP == type;
    }

    public boolean isHit() {
        return TileType.HIT == type;
    }

    public void markAsShip() {
        this.type = TileType.SHIP;
    }

    public void markAsHit() {
        this.type = TileType.HIT;
    }

    public void markAsSunk() {
        this.type = TileType.SUNK;
    }

    public TileType getType() {
        return type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public enum TileType {
        WATER,
        SHIP,
        HIT,
        SUNK
    }
}
