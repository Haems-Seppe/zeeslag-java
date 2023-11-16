package be.swsb.coderetreat;

import org.junit.jupiter.api.Test;

import static be.swsb.coderetreat.Direction.HORIZONTAL;
import static be.swsb.coderetreat.Direction.VERTICAL;
import static be.swsb.coderetreat.Game.renderBattlefield;
import static be.swsb.coderetreat.Ship.ShipType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BattlefieldTest {
    @Test
    public void renderEmptyOceanReturnsStringOfOcean() {
        Player player = new Player("Seppe");
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        """);
    }

    @Test
    public void aPatrolBoatIsPlacedHorizontallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(4, 0), HORIZONTAL, PATROL_BOAT);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOSSOOOO
                        """);
    }

    @Test
    public void aPatrolBoatIsPlacedVerticallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(3, 0), VERTICAL, PATROL_BOAT);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOSOOOOOO
                        OOOSOOOOOO
                        """);
    }

    @Test
    public void aSubmarineIsPlacedVerticallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(4, 0), VERTICAL, SUBMARINE);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        """);
    }

    @Test
    public void aDestroyerIsPlacedVerticallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(4, 0), VERTICAL, DESTROYER);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        """);
    }

    @Test
    public void aBattleshipIsPlacedVerticallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(4, 0), VERTICAL, BATTLESHIP);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        OOOOSOOOOO
                        """);
    }

    @Test
    public void aCarrierIsPlacedVerticallyWithAGivenCoordinate() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(9, 0), VERTICAL, CARRIER);
        String ocean = renderBattlefield(player,false);
        assertThat(ocean).isEqualTo(
                """
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOO
                        OOOOOOOOOS
                        OOOOOOOOOS
                        OOOOOOOOOS
                        OOOOOOOOOS
                        OOOOOOOOOS
                        """);
    }

    @Test
    public void aShipCanOnlyBePlacedWithinTheOcean() {
        Player player = new Player("Seppe");

        assertThatThrownBy(() -> player.placeShip(new Coordinate(10, 0), VERTICAL, SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("These coordinates are out of bounds.");
    }

    @Test
    public void aShipWithNegativeXCoordinatesCanNotBePlaced() {
        Player player = new Player("Seppe");

        assertThatThrownBy(() -> player.placeShip(new Coordinate(-1, 0), VERTICAL, SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("These coordinates are out of bounds.");
    }

    @Test
    public void aShipWithNegativeYCoordinatesCanNotBePlaced() {
        Player player = new Player("Seppe");

        assertThatThrownBy(() -> player.placeShip(new Coordinate(0, -1), VERTICAL, SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("These coordinates are out of bounds.");
    }

    @Test
    public void aShipHasToFitFromTheStartToTheirEndPosition() {
        Player player = new Player("Seppe");

        assertThatThrownBy(() -> player.placeShip(new Coordinate(0, 9), VERTICAL, SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This ship is too large to be placed here.");
    }

    @Test
    public void aShipCanNotBePlacedOnTopOfAnotherShip() {
        Player player = new Player("Seppe");
        player.placeShip(new Coordinate(0, 3), VERTICAL, SUBMARINE);

        assertThatThrownBy(() -> player.placeShip(new Coordinate(0, 3), VERTICAL, SUBMARINE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This ship overlaps with another ship.");
    }
}
