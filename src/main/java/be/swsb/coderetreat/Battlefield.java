package be.swsb.coderetreat;

import static be.swsb.coderetreat.TileRepresentation.*;
import static be.swsb.coderetreat.TileRepresentation.WATER;

public class Battlefield {

    private static int battlefieldSize = 10;
    public Tile[][] ocean = new Tile[battlefieldSize][battlefieldSize];

    public Battlefield() {
        createEmptyOcean();
    }

    private void createEmptyOcean() {
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                this.ocean[i][j] = new Tile(i,j);
            }
        }
    }

    public String render() {
        StringBuilder oceanString = new StringBuilder();
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                oceanString.append(ocean[i][j].getRepresentation().getCharacter());
            }
            oceanString.append("\n");
        }
        return oceanString.toString();
    }

    public void placeShip(int coordinateX, int coordinateY, Direction direction, ShipType ship) {
        if(!shipCanBePlaced(coordinateX, coordinateY, direction, ship)){
            throw new IllegalArgumentException("This is not a valid place for your ship.");
        };
        for (int i = 0; i < ship.getSize(); i++) {
            int x = direction == Direction.HORIZONTAL ? coordinateX : coordinateX + i;
            int y = direction == Direction.HORIZONTAL ? coordinateY + i : coordinateY;
            ocean[x][y].setRepresentation(SHIP);
        }
    }

    private boolean shipCanBePlaced(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        return checkIfOutOfBounds(coordinateX, coordinateY, direction, type) && checkIfSpotsAreEmpty(coordinateX, coordinateY, direction, type);
    }

    private boolean checkIfOutOfBounds(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        return direction == Direction.HORIZONTAL ? (coordinateX + type.getSize()) <= battlefieldSize && coordinateY <= battlefieldSize : coordinateY + type.getSize() <= battlefieldSize && coordinateX <= battlefieldSize;
    }

    private boolean checkIfSpotsAreEmpty(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        for (int i = 0; i < type.getSize(); i++) {
            int x = direction == Direction.HORIZONTAL ? coordinateX : coordinateX + i;
            int y = direction == Direction.HORIZONTAL ? coordinateY + i : coordinateY;

            if (ocean[x][y].getRepresentation() != WATER) {
                return false;
            }
        }
        return true;
    }
}
