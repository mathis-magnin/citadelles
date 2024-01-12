package fr.citadels.engine;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.players.Player;

public class Score implements Comparable<Score> {

    /* Static contents */

    private static Player firstPlayerWithCompleteCity = null;


    /**
     * Set the static variable firstToHaveCompleteCity.
     *
     * @param player The first player who has completed his city.
     */
    public static void setFirstPlayerWithCompleteCity(Player player) {
        Score.firstPlayerWithCompleteCity = player;
    }


    /* Attributes */

    private final Player player;
    private int points;
    private int districtsPoints;
    private boolean completeCityBonus;
    private boolean allFamilyBonus;


    /* Constructor */

    public Score(Player player) {
        this.points = 0;
        this.player = player;
        this.districtsPoints = 0;
        this.completeCityBonus = false;
        this.allFamilyBonus = false;
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
        StringBuilder str = new StringBuilder();
        str.append("    Score total : ").append(this.points).append(".\n");
        str.append("    Quartiers construits : ").append(this.districtsPoints).append(" points.\n");
        if (this.completeCityBonus) {
            if (this.player == Score.firstPlayerWithCompleteCity) {
                str.append("    Première cité complète : 4 points bonus.\n");
            } else {
                str.append("    Cité complète : 2 points bonus.\n");
            }
        }
        if (this.allFamilyBonus) {
            str.append("    Quartier de chaque famille : 3 points bonus.\n");
        }
        return str.toString();
    }


    /* Method */

    /**
     * Player's score comparison is based on their points (natural ordering of integer).
     * If there is a tie, the comparison would be based on the rank of their last character (natural ordering of integer).
     * Note : this class has a natural ordering that is inconsistent with equals.
     *
     * @param other the other score to be compared.
     * @return a positive value if this score is greater than other score.
     * 0 if there is a tie.
     * a negative value if other score is greater than this score.
     */
    @Override
    public int compareTo(Score other) {
        return (this.points - other.getPoints() != 0) ? this.points - other.getPoints() : this.player.compareTo(other.getPlayer());
    }


    /**
     * Determine the score of a player and update it attributes.
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
        for (DistrictCard district : this.player.getCity()) {
            this.districtsPoints += district.getGoldCost();   // 1
        }
        this.points += this.districtsPoints;

        if (this.player.getCity().hasOneDistrictOfEachFamily()) {
            this.points += 3;   // 3
            this.allFamilyBonus = true;
        }

        if (this.player.hasCompleteCity()) {
            if (this.player == Score.firstPlayerWithCompleteCity) {
                this.points += 4;   // 4
            } else {
                this.points += 2;   // 5
            }
            this.completeCityBonus = true;
        }
    }

}