package fr.citadels.engine.score;

import fr.citadels.players.Player;

public class Statistic {


    /* Attributes */

    private final Player player;
    private double gameNumber;
    private double winNumber;
    private double totalScore;


    /* Constructor */

    public Statistic(Player player) {
        this.player = player;
        this.gameNumber = 0.0;
        this.winNumber = 0.0;
        this.totalScore = 0.0;
    }


    /* Basic methods */

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.player.getName()).append("\n");
        str.append("\tNombre de parties gagnées : ").append(this.winNumber).append("\n");
        str.append("\tPourcentage de parties gagnées : ").append(this.getWinPercentage()).append("\n");
        str.append("\tPourcentage de parties perdues : ").append(this.getDefeatPercentage()).append("\n");
        str.append("\tScore moyen : ").append(this.getAverageScore()).append("\n");
        return str.toString();
    }


    public Player getPlayer() {
        return this.player;
    }


    public double getWinNumber() {
        return this.winNumber;
    }


    /* Methods */

    public double getWinPercentage() {
        return (this.gameNumber == 0) ? 0 : (this.winNumber / this.gameNumber) * 100.0;
    }


    public double getDefeatPercentage() {
        return (this.gameNumber == 0) ? 0 : ((this.gameNumber - this.winNumber) / this.gameNumber) * 100.0;
    }


    public double getAverageScore() {
        return  (this.gameNumber == 0) ? 0 : this.totalScore / this.gameNumber;
    }


    /**
     * Update the statistic of the player based on the new score given in parameter.
     * @param score the new score of the player
     * @param isWinner a boolean at true if the player is the winner of the game
     */
    public void update(Score score, boolean isWinner) {
        this.gameNumber++;
        this.winNumber = isWinner ? this.winNumber + 1 : this.winNumber;
        this.totalScore += score.getPoints();
    }

}