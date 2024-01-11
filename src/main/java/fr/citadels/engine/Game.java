package fr.citadels.engine;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.players.*;

import java.util.*;

public class Game {

    private static final Random RAND = new Random();
    public static final int NB_PLAYERS = 4;

    /* Attributes */

    private final Player[] playerList;
    private final DistrictCardsPile districtCardsPile;
    private final Crown crown;
    private final Bank bank;
    private Scoreboard scoreboard;
    private final Display display;
    private boolean isFinished;


    /* Constructor */

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.districtCardsPile = new DistrictCardsPile();
        this.crown = new Crown();
        this.bank = new Bank();
        this.isFinished = false;
        this.display = new Display();
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


    public Bank getBank() {
        return this.bank;
    }


    public boolean isFinished() {
        return this.isFinished;
    }


    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }


    public Display getDisplay() {
        return this.display;
    }


    /* Methods */

    /**
     * Initialize the game
     */
    void initializeGame() {
        this.districtCardsPile.initializePile();
        this.districtCardsPile.shufflePile();

        // Initialize the players
        DistrictCard[] cards = districtCardsPile.draw(4);
        this.playerList[0] = new RandomBot("L'hasardeux", new ArrayList<>(Arrays.asList(cards)), RAND);
        display.addCardDrawn(this.playerList[0], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[1] = new SpendthriftBot("Le dépensier", new ArrayList<>(Arrays.asList(cards)), RAND);
        display.addCardDrawn(this.playerList[1], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[2] = new ThriftyBot("L'économe", new ArrayList<>(Arrays.asList(cards)), RAND);
        display.addCardDrawn(this.playerList[2], cards);

        cards = districtCardsPile.draw(4);
        this.playerList[3] = new KingBot("Le monarchiste", new ArrayList<>(Arrays.asList(cards)), RAND);
        display.addCardDrawn(this.playerList[3], cards);

        this.scoreboard = new Scoreboard(this.playerList);
        this.crown.initializeCrown(RAND);
        display.printAndReset();
    }

    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        int crownedPlayerIndex = this.crown.getCrownedPlayerIndex();
        int length = this.playerList.length;
        int index;

        CharacterCardsList characters = new CharacterCardsList();
        Collections.shuffle(characters);
        CharacterCard[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        CharacterCard[] removedCharactersFaceDown = characters.removeCharactersFaceDown();
        display.addRemovedCharacter(removedCharactersFaceUp, removedCharactersFaceDown);

        for (int i = 0; i < length; i++) {
            index = (i + crownedPlayerIndex) % length;
            playerList[index].chooseCharacter(characters, display);
        }
        display.printAndReset();
    }


    /**
     * Play a turn for each player
     */
    public void playTurn() {
        this.playSelectionPhase();

        Player[] orderedPlayers = new Player[playerList.length];
        System.arraycopy(this.playerList, 0, orderedPlayers, 0, this.playerList.length);
        Arrays.sort(orderedPlayers);   // Sort the player based on their character's rank.

        for (Player player : orderedPlayers) {
            display.addPlayerTurn(player);
            player.play(this.districtCardsPile, this.bank, this.display);
            if (player.hasCompleteCity()) {
                if (!this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(player);
                }
                this.isFinished = true;
            }
            display.printAndReset();
        }
    }


    /**
     * Play the game until a player has a complete city and determine the ranking
     */
    public void playGame() {
        this.initializeGame();
        int round = 1;

        while (!this.isFinished) {
            display.addTurn(round++);
            this.crown.defineNextCrownedPlayer(this.playerList, RAND);
            display.addCrownedPlayer(this.playerList[this.crown.getCrownedPlayerIndex()]);
            display.printAndReset();
            this.playTurn();
        }

        this.scoreboard.determineRanking();
        display.addScoreboard(this.scoreboard);
        display.addWinner(this.scoreboard.getWinner());
        display.printAndReset();
    }

}