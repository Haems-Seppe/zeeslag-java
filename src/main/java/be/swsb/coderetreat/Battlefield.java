package be.swsb.coderetreat;

public class Battlefield {

    private final static int battlefieldSize = 10;
    public Tile[][] ocean = new Tile[battlefieldSize][battlefieldSize];

    public Battlefield() {
        createEmptyOcean();
    }

    private void createEmptyOcean() {
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                this.ocean[i][j] = new Tile(i, j);
            }
        }
    }

    public String render() {
        StringBuilder oceanString = new StringBuilder();
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                oceanString.append(
                        switch (ocean[i][j].getType()) {
                            case WATER -> "O";
                            case SHIP -> "S";
                        }
                );
            }
            oceanString.append("\n");
        }
        return oceanString.toString();
    }

    public void placeShip(int coordinateX, int coordinateY, Direction direction, ShipType ship) {
        validateShipCanBePlaced(coordinateX, coordinateY, direction, ship);
        boolean isHorizontal = direction == Direction.HORIZONTAL;

        for (int i = 0; i < ship.getSize(); i++) {
            int x = isHorizontal ? coordinateX : coordinateX + i;
            int y = isHorizontal ? coordinateY + i : coordinateY;
            ocean[x][y].markAsShip();
        }
    }

    private void validateShipCanBePlaced(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        checkIfOutOfBounds(coordinateX, coordinateY, direction, type);
        checkIfSpotsAreEmpty(coordinateX, coordinateY, direction, type);
    }

    private void checkIfOutOfBounds(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        boolean isHorizontal = direction == Direction.HORIZONTAL;

        if (isHorizontal ? (coordinateX + type.getSize()) > battlefieldSize || coordinateY > battlefieldSize : coordinateY +
                type.getSize() > battlefieldSize || coordinateX > battlefieldSize) {
            throw new IllegalArgumentException("These coordinates are out of bound.");
        }
    }

    private void checkIfSpotsAreEmpty(int coordinateX, int coordinateY, Direction direction, ShipType type) {
        boolean isHorizontal = direction == Direction.HORIZONTAL;
        for (int i = 0; i < type.getSize(); i++) {
            int x = isHorizontal ? coordinateX : coordinateX + i;
            int y = isHorizontal ? coordinateY + i : coordinateY;

            if (!ocean[x][y].isWater()) {
                throw new IllegalStateException("There is already a ship on this tile."); //add cordinates
            }
        }
    }
}
