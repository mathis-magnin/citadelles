package fr.citadels.engine;

import fr.citadels.engine.score.Score;
import fr.citadels.engine.score.Scoreboard;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.Crown;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.*;
import fr.citadels.players.bots.KingBot;
import fr.citadels.players.bots.RandomBot;
import fr.citadels.players.bots.SpendthriftBot;
import fr.citadels.players.bots.ThriftyBot;

import java.util.*;

public class Game {

    private static final Random RAND = new Random();
    public static final int NB_PLAYERS = 4;

    /* Attributes */

    private final Player[] playerList;
    private final DistrictCardsPile districtCardsPile;
    private final Crown crown;
    private final Bank bank;
    private final Display display;
    private final Scoreboard scoreboard;
    private boolean isFinished;


    /* Constructor */

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.districtCardsPile = new DistrictCardsPile();
        this.crown = new Crown();
        this.bank = new Bank();
        this.display = new Display();
        this.isFinished = false;
        this.scoreboard = new Scoreboard(NB_PLAYERS);
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

        this.display.addGameTitle();

        // Initialize the players
        this.playerList[0] = new RandomBot("HASARDEUX", new ArrayList<>(Arrays.asList(districtCardsPile.draw(4))), RAND);
        this.playerList[1] = new SpendthriftBot("DÉPENSIER", new ArrayList<>(Arrays.asList(districtCardsPile.draw(4))), RAND);
        this.playerList[2] = new ThriftyBot("ÉCONOME", new ArrayList<>(Arrays.asList(districtCardsPile.draw(4))), RAND);
        this.playerList[3] = new KingBot("MONARCHISTE", new ArrayList<>(Arrays.asList(districtCardsPile.draw(4))), RAND);
        this.display.addPlayers(this.playerList);
        this.display.addBlankLine();

        for (Player player : this.playerList) {
            this.display.addFirstDistrictsDrawn(player);
        }
        this.display.addBlankLine();

        // Give 2 golds to every player
        for (Player player : this.playerList) {
            player.addGold(2, this.bank);
        }
        this.display.addInitialGoldGiven(2);
        this.display.addBlankLine();

        this.crown.initializeCrown(RAND);
        this.display.addFirstCrownedPlayer(this.playerList[this.crown.getCrownedPlayerIndex()]);
        this.display.addBlankLine(3);

        this.display.printAndReset();
    }


    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        this.display.addSelectionPhaseTitle();

        CharacterCardsList characters = new CharacterCardsList();
        Collections.shuffle(characters);
        CharacterCard[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        CharacterCard[] removedCharactersFaceDown = characters.removeCharactersFaceDown();

        this.display.addRemovedCharacter(removedCharactersFaceUp, removedCharactersFaceDown);
        this.display.addBlankLine(2);

        this.crown.defineNextCrownedPlayer(this.playerList, RAND);
        int crownedPlayerIndex = this.crown.getCrownedPlayerIndex();

        this.display.addCrownedPlayer(this.playerList[crownedPlayerIndex]);
        this.display.addBlankLine();

        int length = this.playerList.length;
        int index;

        for (int i = 0; i < length; i++) {
            index = (i + crownedPlayerIndex) % length;
            playerList[index].chooseCharacter(characters, display);
        }

        this.display.addBlankLine();
    }


    /**
     * Play a turn for each player
     */
    public void playTurnPhase() {
        this.display.addTurnPhaseTitle();

        Player[] orderedPlayers = new Player[playerList.length];
        System.arraycopy(this.playerList, 0, orderedPlayers, 0, this.playerList.length);
        Arrays.sort(orderedPlayers);   // Sort the player based on their character's rank.

        for (Player player : orderedPlayers) {
            this.display.addPlayerTurn(player);
            this.display.addBlankLine();
            this.display.addPlayer(player);
            this.display.addBlankLine();
            player.play(this.districtCardsPile, this.bank, this.display);
            if (player.hasCompleteCity()) {
                if (!this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(player);
                    this.display.addGameFinished(player);
                    this.display.addBlankLine();
                }
                this.isFinished = true;
            }
            this.display.addBlankLine();
        }
    }


    /**
     * Play the game until a player has a complete city and determine the ranking
     */
    public void playGame() {
        this.initializeGame();
        int round = 1;

        while (!this.isFinished) {
            this.display.addTurnTitle(round++);

            this.playSelectionPhase();
            this.playTurnPhase();

            this.display.printAndReset();
        }

        this.scoreboard.initializeScoreboard(this.playerList);
        this.scoreboard.determineRanking();
        this.display.addScoreTitle();
        this.display.addScoreboard(this.scoreboard);
        this.display.addBlankLine();
        this.display.addWinner(this.scoreboard.getWinner());
        this.display.printAndReset();
    }

}