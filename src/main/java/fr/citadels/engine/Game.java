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

    /* Constant values */

    public static final int NB_PLAYERS = 4;


    /* Static attributes */

    private static final Random RAND = new Random();


    /* Attributes */

    private final Player[] playerList;
    private final Crown crown;
    private final Bank bank;
    private final Display display;
    private final DistrictCardsPile pile;
    private final Scoreboard scoreboard;
    private boolean isFinished;


    /* Constructor */

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.crown = new Crown();
        this.bank = new Bank();
        this.display = new Display();
        this.pile = new DistrictCardsPile();
        this.isFinished = false;
        this.scoreboard = new Scoreboard(NB_PLAYERS);
    }


    /* Getters */

    public Player[] getPlayerList() {
        return this.playerList;
    }


    public Crown getCrown() {
        return this.crown;
    }


    public Bank getBank() {
        return this.bank;
    }


    public Display getDisplay() {
        return this.display;
    }


    public DistrictCardsPile getPile() {
        return this.pile;
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
        this.pile.initializePile();
        this.pile.shufflePile();

        this.display.addGameTitle();

        // Initialize the players
        this.playerList[0] = new RandomBot("HASARDEUX", Arrays.asList(this.pile.draw(4)), this.pile, this.bank, this.display, RAND);
        this.playerList[1] = new SpendthriftBot("DÉPENSIER", Arrays.asList(this.pile.draw(4)), this.pile, this.bank, this.display, RAND);
        this.playerList[2] = new ThriftyBot("ÉCONOME", Arrays.asList(this.pile.draw(4)), this.pile, this.bank, this.display, RAND);
        this.playerList[3] = new KingBot("MONARCHISTE", Arrays.asList(this.pile.draw(4)), this.pile, this.bank, this.display, RAND);
        this.display.addPlayers(this.playerList);
        this.display.addBlankLine();

        for (Player player : this.playerList) {
            this.display.addFirstDistrictsDrawn(player);
        }
        this.display.addBlankLine();

        // Give 2 golds to every player
        for (Player player : this.playerList) {
            player.addGold(2);
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
        /* Set all character's player at null */
        for (CharacterCard character : CharacterCardsList.allCharacterCards) {
            character.setPlayer(null);
        }

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
            this.playerList[index].chooseCharacter(characters);
        }

        this.display.addBlankLine();
        this.display.addCharacterNotChosen(characters.remove(0)); // Only one character is not chosen

        this.display.addBlankLine();
    }


    /**
     * Play a turn for each player
     */
    public void playTurnPhase() {
        this.display.addTurnPhaseTitle();

        for (CharacterCard character : CharacterCardsList.allCharacterCards) {
            if (character.getPlayer() != null) {
                this.display.addPlayerTurn(this.playerList[this.crown.getCrownedPlayerIndex()], character.getPlayer());
                this.display.addBlankLine();
                this.display.addPlayer(character.getPlayer());
                this.display.addBlankLine();

                character.bringIntoPlay();

                if (character.getPlayer().hasCompleteCity() && !this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(character.getPlayer());
                    this.display.addGameFinished(character.getPlayer());
                    this.display.addBlankLine();
                    this.isFinished = true;
                }
            }
            else {
                this.display.addNoPlayerTurn(this.playerList[this.crown.getCrownedPlayerIndex()], character);
                this.display.addBlankLine();
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