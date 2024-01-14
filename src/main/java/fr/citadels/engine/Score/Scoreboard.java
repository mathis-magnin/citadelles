package fr.citadels.engine.Score;

import fr.citadels.players.Player;

import java.util.Arrays;
import java.util.Collections;

public class Scoreboard {

    /* Attribute */

    private final Score[] scores;


    /* Constructor */

    public Scoreboard(int nbPlayers){
        this.scores = new Score[nbPlayers];
    }


    public Scoreboard(Player[] players) {
        this.scores = new Score[players.length];
        for (int i = 0; i < players.length; i++) {
            scores[i] = new Score(players[i]);

        }
    }


    /* Basic method */

    public void initializeScoreboard(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            scores[i] = new Score(players[i]);
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < scores.length; i++) {
            str.append("■ ").append(i + 1).append("e place\n").append(scores[i].toString());
            if (((i != scores.length - 1) && (scores[i].getPoints() == scores[i + 1].getPoints())) || ((i != 0) && (scores[i].getPoints() == scores[i - 1].getPoints()))) {
                str.append("\tDernier personnage joué : ").append(scores[i].getPlayer().getCharacter()).append("\n");
            }
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
     * @precondition The Scoreboard's procedure determineRanking() must have been
     * called to be able to get the winner of the game.
     */
    public Player getWinner() {
        return this.scores[0].getPlayer();
    }

}
