package fr.citadels.engine;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.players.*;

import java.util.*;

public class Game {

    /* Static constant */
    public static final int NB_PLAYERS = 4;

    private static final Random RAND = new Random();

    /* Attributes */

    private final Player[] playerList;
    private final DistrictCardsPile districtCardsPile;
    private final Crown crown;
    private final Bank bank;
    private Scoreboard scoreboard;
    private boolean isFinished;



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
        Display events = new Display();

        //Initialize the players
        DistrictCard[] cards = districtCardsPile.draw(4);
        this.playerList[0] = new RandomBot("L'hasardeux", new ArrayList<>(Arrays.asList(cards)), RAND);
        events.displayCardDrawn(this.playerList[0], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[1] = new SpendthriftBot("Le dépensier", new ArrayList<>(Arrays.asList(cards)), RAND);
        events.displayCardDrawn(this.playerList[1], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[2] = new ThriftyBot("L'économe", new ArrayList<>(Arrays.asList(cards)), RAND);
        events.displayCardDrawn(this.playerList[2], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[3] = new KingBot("Le monarchiste", new ArrayList<>(Arrays.asList(cards)), RAND);
        events.displayCardDrawn(this.playerList[3], cards);

        this.scoreboard = new Scoreboard(this.playerList);
        this.crown.initializeCrown(RAND);
        events.printDisplay();
    }

    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        Display events = new Display();
        int crownedPlayerIndex = this.crown.getCrownedPlayerIndex();
        int length = this.playerList.length;
        int index;

        CharacterCardsList characters = new CharacterCardsList();
        Collections.shuffle(characters);
        CharacterCard[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        CharacterCard[] removedCharactersFaceDown = characters.removeCharactersFaceDown();
        events.displayRemovedCharacter(removedCharactersFaceUp, removedCharactersFaceDown);

        for (int i = 0; i < length; i++) {
            index = (i + crownedPlayerIndex) % length;
            playerList[index].chooseCharacter(characters, events);
        }
        events.printDisplay();
    }


    /**
     * Play a turn for each player
     */
    public void playTurn() {
        this.playSelectionPhase();
        Display events = new Display();

        Player[] orderedPlayers = new Player[playerList.length];
        System.arraycopy(this.playerList, 0, orderedPlayers, 0, this.playerList.length);
        Arrays.sort(orderedPlayers);   // Sort the player based on their character's rank.

        for (Player player : orderedPlayers) {
            events.displayPlayerTurn(player);
            player.play(this.districtCardsPile, this.bank, events);
            if (player.hasCompleteCity()) {
                if (!this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(player);
                }
                this.isFinished = true;
            }
            events.printAndReset();
        }
    }


    /**
     * Play the game until a player has a complete city and determine the ranking
     */
    public void playGame() {
        Display events = new Display();
        this.initializeGame();
        int round = 1;

        while (!this.isFinished) {
            events.displayTurn(round++);
            this.crown.defineNextCrownedPlayer(this.playerList, RAND);
            events.displayCrownedPlayer(this.playerList[this.crown.getCrownedPlayerIndex()]);
            events.printAndReset();
            this.playTurn();
        }

        this.scoreboard.determineRanking();
        events.displayWinner(this.scoreboard.getWinner());
        events.displayScoreboard(this.scoreboard);
        events.printDisplay();
    }

}