package fr.citadels.engine;

import fr.citadels.cards.DistrictCardsPile;
import fr.citadels.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    /*
    const nbDistrict to win
    affichage des scores
     */

    /* Attributes */

    private Player[] playerList;
    private DistrictCardsPile districtCardsPile;
    private Crown crown;
    private boolean isFinished;
    private Scoreboard scoreboard;

    private static final int NB_PLAYERS = 2;

    /* Constructor */

    public Game(Player player1, Player player2) {
        this.playerList = new Player[NB_PLAYERS];
        this.playerList[0] = player1;
        this.playerList[1] = player2;
        this.crown = new Crown();
        this.districtCardsPile = new DistrictCardsPile();
        this.isFinished = false;
        this.scoreboard = new Scoreboard(this.playerList);
    }

    /* Methods */

    /**
     * Give the crown to a random player
     */
    public void giveCrown() {
        int randomIndex = (int) (Math.random() * NB_PLAYERS);
        this.crown.setPlayerWithCrown(this.playerList[randomIndex]);
        this.crown.setPlayerIndexWithCrown(randomIndex);
    }

    /**
     * Play a turn for each player
     */
    public void playTurn() {
        for (int i = 0; i < NB_PLAYERS; i++) {
            int nextPlayerIndex = (i + this.crown.getPlayerIndexWithCrown()) % NB_PLAYERS;
            this.playerList[nextPlayerIndex].play(this.districtCardsPile);
            if (this.playerList[nextPlayerIndex].hasCompleteCity()) {
                if (!this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(this.playerList[nextPlayerIndex]);
                }
                this.isFinished = true;
            }
        }
    }

    /**
     * Play the game until a player has a complete city and determine the ranking
     */
    public void playGame() {
        this.districtCardsPile.initializePile();
        this.districtCardsPile.shufflePile();

        while (!this.isFinished) {
            this.giveCrown();
            this.playTurn();
        }
        this.scoreboard.determineRanking();

        System.out.println("Le gagnant est " + this.scoreboard.getWinner().getName() + " !\n");
        System.out.println(this.scoreboard);
    }
}
