package be.swsb.coderetreat;

public enum ShipType {
    PATROL_BOAT(2),
    SUBMARINE(3);
    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
