package be.swsb.coderetreat;

public class Tile {
    private final int xCoordinate;
    private final int yCoordinate;

    private TileRepresentation representation;

    public Tile(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.representation = TileRepresentation.WATER;
    }

    public void setRepresentation(TileRepresentation representation) {
        this.representation = representation;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public TileRepresentation getRepresentation() {
        return representation;
    }
}
