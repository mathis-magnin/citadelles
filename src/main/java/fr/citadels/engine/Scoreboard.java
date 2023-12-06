package fr.citadels.engine;

import fr.citadels.players.Player;

import java.util.Arrays;
import java.util.Collections;

public class Scoreboard {

    /* Attribute */

    private final Score[] scores;


    /* Constructor */

    public Scoreboard(Player[] players) {
        this.scores = new Score[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = new Score(players[i]);
        }
    }


    /* Basic method */

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Score score : scores) {
            str.append(score.toString());
            str.append("\n");
        }
        return str.toString();
    }


    /* Methods */

    /**
     * Determine the ranking of all the players.
     *
     * @precondition The static Score's procedure Score.setFirstPlayerWithCompleteCity(Player player) must have been
     * called to be able to correctly call the Score's procedure determinePoints().
     */
    public void determineRanking() {
        for (Score score : scores) {
            score.determinePoints();
        }
        Arrays.sort(this.scores, Collections.reverseOrder()); // Sorting the array of score in descending order
    }


    /**
     * Determine the winner of the game.
     *
     * @return The winner of the game.
     * @precondition The static Score's procedure Score.setFirstPlayerWithCompleteCity(Player player) must have been
     * called to be able to correctly call the procedure determineRanking().
     */
    public Player getWinner() {
        this.determineRanking();
        return this.scores[0].getPlayer();
    }

}
