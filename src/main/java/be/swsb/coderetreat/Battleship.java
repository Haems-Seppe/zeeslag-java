package be.swsb.coderetreat;

import be.swsb.coderetreat.util.Helper;

public class Battleship {

    public static void main(String[] args) {
        Helper helper = new Helper();

        String player1Name = helper.getStringInput("Player 1, what is your name?");
        String player2Name = helper.getStringInput("Player 2, what is your name?");
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        Game game = new Game(player1, player2);
        game.start();
    }

}
