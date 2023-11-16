package be.swsb.coderetreat;

import org.junit.jupiter.api.Test;

import static be.swsb.coderetreat.Direction.VERTICAL;
import static be.swsb.coderetreat.Game.renderBattlefield;
import static be.swsb.coderetreat.Ship.ShipType.DESTROYER;
import static be.swsb.coderetreat.Ship.ShipType.SUBMARINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BattleshipTest {

    @Test
    public void whenAPlayerShootsAndMissesNothingHappens() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player, new Player("Seppe"));
        game.setSHOOTINGPhase();
        game.handleShot(new Coordinate(1, 1), player);
        String ocean = renderBattlefield(player, false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        SOOOOOOOOO
                        SOOOOOOOOO
                        SOOOOOOOOO
                        """);
    }

    @Test
    public void whenAPlayerShootsAndHitsAShipThenTileIsMarked() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player, new Player("Seppe"));
        game.setSHOOTINGPhase();
        game.handleShot(new Coordinate(0, 2), player);
        String ocean = renderBattlefield(player, false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        HOOOOOOOOO
                        SOOOOOOOOO
                        SOOOOOOOOO
                        """);
    }

    @Test
    public void whenAPlayerShootsATileWithInvalidArgumentsAnExceptionIsThrown() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player, new Player("Seppe"));
        game.setSHOOTINGPhase();
        assertThatThrownBy(() -> game.handleShot(new Coordinate(10, 1), player))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("These coordinates are out of bounds.");
    }

    @Test
    public void whenAPlayerShootsAndHitsTheLastStandingPartOfAshipThenShipIsSunk() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player, new Player("Seppe"));
        game.setSHOOTINGPhase();
        game.handleShot(new Coordinate(0, 1), player);
        game.handleShot(new Coordinate(0, 0), player);
        game.handleShot(new Coordinate(0, 2), player);


        String ocean = renderBattlefield(player, false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        XOOOOOOOOO
                        XOOOOOOOOO
                        XOOOOOOOOO
                        """);

    }

    @Test
    public void whenAllShipsOfAPlayerAreSunkTheGameHasEnded() {
        Player player1 = new Player("Seppe");
        Player player2 = new Player("Michiel");
        Game game = new Game(player1, player2);

        player1.getBattlefield().placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        player2.getBattlefield().placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        player2.getBattlefield().placeShip(new Coordinate(1, 0), VERTICAL, DESTROYER);
        game.setSHOOTINGPhase();
        game.handleShot(new Coordinate(0, 1), player2);
        game.handleShot(new Coordinate(0, 0), player2);
        game.handleShot(new Coordinate(0, 2), player2);

        game.handleShot(new Coordinate(1, 1), player2);
        game.handleShot(new Coordinate(1, 0), player2);
        game.handleShot(new Coordinate(1, 2), player2);

        assertThat(game.gameIsOver()).isTrue();
    }

    @Test
    public void aBattlefieldCanBeShownWithOnlyTheShotTiles() {
        Player player1 = new Player("Seppe");
        Player player2 = new Player("Michiel");
        player1.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        player2.placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player1, player2);
        game.setSHOOTINGPhase();
        game.handleShot(new Coordinate(0, 0), player2);
        game.handleShot(new Coordinate(0, 1), player2);

        String ocean = renderBattlefield(player2, true);
        assertThat(ocean).isEqualTo(
                """
                        ??????????
                        ??????????
                        ??????????
                        ??????????
                        ??????????
                        ??????????
                        ??????????
                        ??????????
                        H?????????
                        H?????????
                        """);
    }

    @Test
    public void theGameStartsWithThePlacementPhase() {
        Player player1 = new Player("Seppe");
        Player player2 = new Player("Michiel");
        player1.getBattlefield().placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        player2.getBattlefield().placeShip(new Coordinate(0, 0), VERTICAL, SUBMARINE);
        Game game = new Game(player1, player2);

        assertThat(game.gameIsOver()).isFalse();
        assertThat(game.gamePhase).isEqualTo(Game.GamePhase.PLACING_SHIPS);
    }

    @Test
    public void whileTheGameIsInPlacementPhaseShootingIsNotAllowed() {
        Player player1 = new Player("Seppe");
        Player player2 = new Player("Michiel");
        Game game = new Game(player1, player2);

        assertThatThrownBy(() -> game.handleShot(new Coordinate(0, 0), player2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("You can't shoot yet!");
    }

    @Test
    public void whileTheGameIsInShootingPhasePlacingShipsIsNotAllowed() {
        Player player1 = new Player("Seppe");
        Player player2 = new Player("Michiel");
        Game game = new Game(player1, player2);
        game.setSHOOTINGPhase();

        assertThatThrownBy(() -> game.placeAllShips(player2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("You can't place a ship in this stage of the game!");
    }
}