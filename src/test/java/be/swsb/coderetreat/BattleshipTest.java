package be.swsb.coderetreat;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BattleshipTest {

    @Test
    public void renderEmptyOceanReturnsStringOfOcean() {
        Battlefield battlefield = new Battlefield();
        String ocean = battlefield.render();
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
    public void aPatrolBoatIsPlacedHorizontallyWithAGivenCoordinate(){
        Battlefield battlefield = new Battlefield();
        battlefield.placeShip(0,0, Direction.HORIZONTAL, ShipType.PATROL_BOAT);
        String ocean = battlefield.render();
        assertThat(ocean).isEqualTo(
                """
                        SSOOOOOOOO
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
    public void aPatrolBoatIsPlacedVerticallyWithAGivenCoordinate(){
        Battlefield battlefield = new Battlefield();
        battlefield.placeShip(0,0, Direction.VERTICAL, ShipType.PATROL_BOAT);
        String ocean = battlefield.render();
        assertThat(ocean).isEqualTo(
                """
                        SOOOOOOOOO
                        SOOOOOOOOO
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
    public void aSubmarineIsPlacedVerticallyWithAGivenCoordinate(){
        Battlefield battlefield = new Battlefield();
        battlefield.placeShip(0,0, Direction.VERTICAL, ShipType.SUBMARINE);
        String ocean = battlefield.render();
        assertThat(ocean).isEqualTo(
                """
                        SOOOOOOOOO
                        SOOOOOOOOO
                        SOOOOOOOOO
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
    public void aShipCanOnlyBePlacedWithinTheOcean() {
        Battlefield battlefield = new Battlefield();

        assertThatThrownBy(() -> battlefield.placeShip(11, 0, Direction.VERTICAL, ShipType.SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid place for your ship.");
    }
    @Test
    public void aShipCanNotBePlacedOnTopOfAnotherBoat() {
        Battlefield battlefield = new Battlefield();
        battlefield.placeShip(0, 0, Direction.VERTICAL, ShipType.SUBMARINE);

        assertThatThrownBy(() -> battlefield.placeShip(0, 0, Direction.VERTICAL, ShipType.SUBMARINE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This is not a valid place for your ship.");
    }
}