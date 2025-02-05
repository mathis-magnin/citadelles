package fr.citadels.engine.score;

import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;

import fr.citadels.cards.districts.Unique;
import fr.citadels.players.Player;

public class Score implements Comparable<Score> {

    /* Static content */

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
    private int districtsPoints;
    private int allFamiliesPoints;
    private int completeCityPoints;
    private int uniqueDistrictsPoints;
    private boolean activateMiracleCourtyardEffect;


    /* Constructor */

    public Score(Player player) {
        this.player = player;
        this.districtsPoints = 0;
        this.allFamiliesPoints = 0;
        this.completeCityPoints = 0;
        this.uniqueDistrictsPoints = 0;
        this.activateMiracleCourtyardEffect = false;
    }


    /* Basic methods */

    public Player getPlayer() {
        return this.player;
    }


    /**
     * @return the total amount of points.
     */
    public int getPoints() {
        return this.districtsPoints + this.allFamiliesPoints + this.completeCityPoints + this.uniqueDistrictsPoints;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.player.getName()).append("\n");
        str.append("\tScore total : ").append(this.getPoints()).append(" points\n");
        str.append("\tQuartiers construits : ").append(this.districtsPoints).append(" points\n");
        if (this.uniqueDistrictsPoints != 0) {
            str.append("\tEffets des quartiers merveilles : ").append(this.uniqueDistrictsPoints).append(" points\n");
        }
        if (this.completeCityPoints != 0) {
            str.append((this.player == Score.firstPlayerWithCompleteCity) ? "\tPremière cité " : "\tCité ").append("complète : ").append(this.completeCityPoints).append(" points\n");
        }
        if (this.allFamiliesPoints != 0) {
            str.append("\tQuartier de chaque famille ").append((this.activateMiracleCourtyardEffect) ? "grâce à l'effet de la Cour des miracles " : "").append(": ").append(this.allFamiliesPoints).append(" points\n");
        }
        return str.toString();
    }


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
        return (this.getPoints() - other.getPoints() != 0) ? this.getPoints() - other.getPoints() : this.player.compareTo(other.getPlayer());
    }


    /* Method */

    /**
     * Determine the score of a player and update it attributes.
     * Player scores points as follows :
     * 1. Score points equal to the building cost of each of his districtcards.
     * 2. Player scores any extra points from his unique districtcards.
     * 3. If the player's city has at least one district of each type, player scores 3 points.
     * 4. The player who first completed his city scores 4 points.
     * 5. Any other player who completed his city scores 2 points.
     *
     * @precondition The static procedure Score.setFirstPlayerWithCompleteCity(Player player) must have been called to
     * be able to follow rule 4.
     */
    public void determinePoints() {
        for (District district : this.player.getCity()) {
            if (district.equals(DistrictsPile.allDistrictCards[Unique.UNIVERSITY.toIndex()]) || district.equals(DistrictsPile.allDistrictCards[Unique.DRAGON_GATE.toIndex()])) {
                this.uniqueDistrictsPoints += 8;
            } else {
                this.districtsPoints += district.getGoldCost();   // 1
            }
        }

        boolean[] districtOfEachFamily = this.player.getCity().hasOneDistrictOfEachFamily();
        if (districtOfEachFamily[0]) {
            this.allFamiliesPoints += 3;   // 3
            this.activateMiracleCourtyardEffect = districtOfEachFamily[1];
        }

        if (this.player.hasCompleteCity()) {
            this.completeCityPoints = ((this.player == Score.firstPlayerWithCompleteCity) ? 4 : 2); // 4 and 5
        }
    }

}