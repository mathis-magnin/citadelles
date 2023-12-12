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
     */

    /* Attributes */

    private final Player[] playerList;
    private final DistrictCardsPile districtCardsPile;
    private final Crown crown;
    private final Bank bank;
    private boolean isFinished;
    private Scoreboard scoreboard;

    public static final int NB_PLAYERS = 2;


    /* Constructor */

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.districtCardsPile = new DistrictCardsPile();
        this.crown = new Crown();
        this.bank = new Bank();
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
        return crown;
    }


    public Bank getBank() {
        return this.bank;
    }


    public boolean isFinished() {
        return this.isFinished;
    }


    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }


    /* Methods */

    /**
     * Initialize the game
     */
    void initializeGame() {
        this.districtCardsPile.initializePile();
        this.districtCardsPile.shufflePile();
        this.crown.initializeCrown();
        Player.setBank(this.bank);
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
            int nextPlayerIndex = (i + this.crown.getCrownedPlayerIndex()) % NB_PLAYERS;
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
            this.crown.defineNextCrownedPlayer(this.playerList);
            System.out.print("Le joueur " + this.playerList[this.crown.getCrownedPlayerIndex()].getName() + " a la couronne.\n");
            this.playTurn();
        }

        this.scoreboard.determineRanking();
        System.out.println("Le gagnant est " + this.scoreboard.getWinner().getName() + " !\n");
        System.out.println(this.scoreboard);
    }

}