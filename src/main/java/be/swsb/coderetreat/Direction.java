package be.swsb.coderetreat;

public enum Direction {
    HORIZONTAL,
    VERTICAL;

    public static Direction fromStringAbbreviation(String abbreviation) {
        return switch (abbreviation.toLowerCase()) {
            case "h" -> HORIZONTAL;
            case "v" -> VERTICAL;
            default -> throw new IllegalArgumentException("Invalid direction abbreviation. Please enter 'h' or 'v'.");
        };
    }
}
