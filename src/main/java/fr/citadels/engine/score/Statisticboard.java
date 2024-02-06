package fr.citadels.engine.score;

import fr.citadels.players.Player;

public class Statisticboard {

    /* Attribute */

    private Statistic[] statistics;


    /* Constructor */

    public Statisticboard(int playerNumber) {
        this.statistics = new Statistic[playerNumber];
    }


    /* Basic methods */

    /**
     * Initialize the statisticboard.
     * @param players an array of player
     * @precondition players should be the same size as statistics.
     */
    public void initialize(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            this.statistics[i] = new Statistic(players[i]);
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Statistic statistic: this.statistics) {
            str.append(statistic.toString());
        }
        return str.toString();
    }


    public Statistic[] getStatistics() {
        return this.statistics;
    }


    /* Methods */

    /**
     * Update all the statistic within the statisticboard based on the new scoreboard.
     * @param scoreboard the scoreboard of the game
     */
    public void update(Scoreboard scoreboard) {
        for (Score score : scoreboard.getScores()) {
            for (Statistic statistic : this.statistics) {
                if (statistic.getPlayer().equals(score.getPlayer())) {
                    statistic.update(score, scoreboard.getWinner().equals(statistic.getPlayer()));
                }
            }
        }
    }

}
