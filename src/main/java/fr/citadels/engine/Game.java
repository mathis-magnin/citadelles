package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import fr.citadels.players.BotFirstStrategy;
import fr.citadels.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.crown = new Crown();
        this.districtCardsPile = new DistrictCardsPile();
        this.isFinished = false;
    }

    /* Getters */

    public Player[] getPlayerList() {
        return this.playerList;
    }

    public DistrictCardsPile getDistrictCardsPile() {
        return this.districtCardsPile;
    }

    public Crown getCrown() {
        return this.crown;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
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
     * Initialize the game
     */
    void initializeGame() {
        this.districtCardsPile.initializePile();
        this.districtCardsPile.shufflePile();
        for (int i = 0; i < NB_PLAYERS; i++) {
            List<DistrictCard> cards = new ArrayList<>(Arrays.asList(districtCardsPile.draw(4)));
            this.playerList[i] = new BotFirstStrategy("Joueur " + (i + 1), cards);
        }
        this.scoreboard = new Scoreboard(this.playerList);
    }

    /**
     * Play a turn for each player
     */
    public void playTurn() {
        for (int i = 0; i < NB_PLAYERS; i++) {
            int nextPlayerIndex = (i + this.crown.getPlayerIndexWithCrown()) % NB_PLAYERS;
            System.out.println(playerList[nextPlayerIndex].play(this.districtCardsPile));
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
        this.initializeGame();
        int round = 1;

        while (!this.isFinished) {
            System.out.println("Tour " + round++);
            this.giveCrown();
            System.out.print("Le joueur " + this.crown.getPlayerWithCrown().getName() + " a la couronne.\n");
            this.playTurn();
            System.out.println();
        }
        this.scoreboard.determineRanking();

        System.out.println("Le gagnant est " + this.scoreboard.getWinner().getName() + " !\n");
        System.out.println(this.scoreboard);
    }
}
