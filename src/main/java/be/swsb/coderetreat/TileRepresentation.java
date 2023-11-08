package be.swsb.coderetreat;

public enum TileRepresentation {
    WATER("O"),
    SHIP("S");
    private final String character;

    TileRepresentation(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
