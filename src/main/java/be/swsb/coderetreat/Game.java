package be.swsb.coderetreat;

import be.swsb.coderetreat.Ship.ShipType;
import be.swsb.coderetreat.util.Helper;

import static be.swsb.coderetreat.Battlefield.battlefieldSize;
import static be.swsb.coderetreat.Battlefield.validateCoordinate;
import static be.swsb.coderetreat.Coordinate.GetYCoordinate;

public class Game {
    private final Player player1, player2;
    private static final Helper helper = new Helper();
    public GamePhase gamePhase;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gamePhase = GamePhase.PLACING_SHIPS;
    }

    public boolean isGameOver() {
        if (player1.allShipsAreSunk() || player2.allShipsAreSunk()) {
            gamePhase = GamePhase.GAME_OVER;
            return true;
        }
        return false;
    }

    public void placeAllShips(Player player) {
        validateReadyToPlaceShip();
        for (ShipType type : ShipType.values()) {
            boolean placedSuccesfully = false;
            while (!placedSuccesfully) {
                try {
                    System.out.println("Time to place your " + type.name() + ", It has a length of " + type.getSize() + "!");
                    int xCoordinate = helper.getIntInput("Enter the x coordinate of the starting position:");
                    int yCoordinate = helper.getIntInput("Enter the y coordinate of the starting position:");
                    String direction = helper.getDirectionInput("Enter the direction of the ship (h or v):");
                    player.placeShip(new Coordinate(xCoordinate, yCoordinate), Direction.fromStringAbbreviation(direction), type);
                    placedSuccesfully = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println("Here's your current battlefield:");
                System.out.println(renderBattlefield(player, false));
            }
        }
    }

    public static String renderBattlefield(Player player, boolean isHidden) {
        StringBuilder oceanString = new StringBuilder();
        Tile[][] ocean = player.getBattlefield().ocean;
        for (int i = 0; i < battlefieldSize; i++) {
            for (int j = 0; j < battlefieldSize; j++) {
                if (isHidden && !player.getShotsTaken().contains(ocean[i][j].getCoordinate())) {
                    oceanString.append("?");
                } else {
                    switch (ocean[i][j].getType()) {
                        case WATER -> oceanString.append("O");
                        case SHIP -> oceanString.append("S");
                        case HIT -> oceanString.append("H");
                        case SUNK -> oceanString.append("X");
                    }
                }
            }
            oceanString.append("\n");
        }
        return oceanString.toString();
    }

    public void handleShot(Coordinate coordinate, Player opponent) {
        validateReadyToShoot();
        validateCoordinate(coordinate);
        opponent.getShotsTaken().add(coordinate);
        Tile tileToShoot = opponent.getBattlefield().ocean[GetYCoordinate(coordinate.yCoordinate)][coordinate.xCoordinate];
        if (tileToShoot.isShip()) {
            System.out.println("Good shot! You hit a ship!");
            tileToShoot.markAsHit();
            opponent.getBattlefield().checkIfAShipIsSunk();
        } else {
            System.out.println("Oh no, You missed! Better luck next time!");
        }
    }

    public void setSHOOTINGPhase() {
        gamePhase = GamePhase.SHOOTING;
    }

    public void start() {
        Player firstMover = player1;
        Player secondMover = player2;

        System.out.println("Welcome to Battleship!");
        System.out.println(firstMover.getName() + ", it is your turn to place your ships!");
        placeAllShips(player1);
        Helper.HideConsoleOutput();
        System.out.println(secondMover.getName() + ", it is your turn to place your ships!");
        placeAllShips(player2);
        Helper.HideConsoleOutput();
        System.out.println("Both players have placed their ships!");
        setSHOOTINGPhase();
        while (gamePhase.equals(GamePhase.SHOOTING)) {
            playTurn(firstMover, secondMover);
            if (isGameOver()) {
                break;
            }
            Helper.HideConsoleOutput();
            Player temp = firstMover;
            firstMover = secondMover;
            secondMover = temp;
        }
        System.out.println("You sunk all of your opponent's ships, Game Over! " + firstMover.getName() + " wins!");
        System.out.println("Here's your opponent's battlefield:");
        System.out.println(renderBattlefield(secondMover, false));
    }

    private void playTurn(Player currentPlayer, Player opponent) {
        System.out.println(currentPlayer.getName() + ", it's your turn to shoot!");
        System.out.println("Here's your battlefield:");
        System.out.println(renderBattlefield(currentPlayer, false));
        System.out.println("Here's your opponent's battlefield:");
        System.out.println(renderBattlefield(opponent, true));
        int xCoordinate = helper.getIntInput("Enter the x coordinate of the position you want to shoot:");
        int yCoordinate = helper.getIntInput("Enter the y coordinate of the position you want to shoot:");
        handleShot(new Coordinate(xCoordinate, yCoordinate), opponent);
    }

    private void validateReadyToShoot() {
        if (gamePhase != GamePhase.SHOOTING) {
            throw new IllegalStateException("You can't shoot yet!");
        }
    }

    private void validateReadyToPlaceShip() {
        if (gamePhase != GamePhase.PLACING_SHIPS) {
            throw new IllegalStateException("You can't place a ship in this stage of the game!");
        }
    }

    public enum GamePhase {
        PLACING_SHIPS,
        SHOOTING,
        GAME_OVER
    }
}
