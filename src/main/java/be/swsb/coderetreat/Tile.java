package be.swsb.coderetreat;

public class Tile {
    private final int xCoordinate;
    private final int yCoordinate;
    private TileType type;


    public Tile(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.type = TileType.WATER;
    }

    public boolean isWater(){
        return TileType.WATER == type;
    }

    public void markAsShip(){
        this.type = TileType.SHIP;
    }

    public TileType getType() {
        return type;
    }

    public enum TileType {
        WATER,
        SHIP
    }
}
