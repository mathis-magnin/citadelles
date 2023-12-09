package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.players.Player;

public class Score implements Comparable<Score> {

    /* Static contents */

    private static Player firstPlayerWithCompleteCity = null;
    private final Player player;


    /* Attributes */
    private int points;
    public Score(Player player) {
        this.points = 0;
        this.player = player;
    }


    /* Constructor */

    /**
     * Set the static variable firstToHaveCompleteCity;
     *
     * @param player The first player who has completed his city.
     */
    public static void setFirstPlayerWithCompleteCity(Player player) {
        Score.firstPlayerWithCompleteCity = player;
    }


    /* Basic methods */

    public int getPoints() {
        return this.points;
    }


    public Player getPlayer() {
        return this.player;
    }


    @Override
    public String toString() {
        return "Score de " + this.player.getName() + " : " + this.points;
    }

    /* Method */

    /**
     * Player's score comparison is based on their points (natural ordering of integer).
     * If there is a tie, the comparison would be based on the rank of their last character (natural ordering of integer).
     * Note : this class has a natural ordering that is inconsistent with equals.
     *
     * @param other the other score to be compared.
     * @return 1 if this score is greater than other score.
     * 0 if there is a tie.
     * -1 if other score is greater than this score.
     */
    @Override
    public int compareTo(Score other) {
        if (this.points > other.getPoints()) {
            return 1;
        } else if (this.points < other.getPoints()) {
            return -1;
        } else {
            return 0;   // Milestone 1 : no character. Comparison only based on the points.
        }
    }


    /**
     * Determine the score of a player.
     * Player scores points as follows :
     * 1. Score points equal to the building cost of each of his districts.
     * 2. Player scores any extra points from his unique districts.
     * 3. If the player's city has at least one district of each type, player scores 3 points.
     * 4. The player who first completed his city scores 4 points.
     * 5. Any other player who completed his city scores 2 points.
     *
     * @precondition The static procedure Score.setFirstPlayerWithCompleteCity(Player player) must have been called to
     * be able to follow rule 4.
     */
    public void determinePoints() {
        for (DistrictCard district : this.player.getCityCards()) {
            this.points += district.getGoldCost();   // 1
        }

        if (this.player.hasCompleteCity()) {
            if (this.player == Score.firstPlayerWithCompleteCity) {
                this.points += 4;   // 4
            } else {
                this.points += 2;   // 5
            }
        }
    }

}