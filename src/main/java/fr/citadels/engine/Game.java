package fr.citadels.engine;

import fr.citadels.engine.score.Score;
import fr.citadels.engine.score.Scoreboard;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.King;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.players.*;
import fr.citadels.players.bots.Monarchist;
import fr.citadels.players.bots.Random;
import fr.citadels.players.bots.Spendthrift;
import fr.citadels.players.bots.Thrifty;

import java.util.*;

public class Game {

    /* Constant values */

    public static final int NB_PLAYERS = 4;


    /* Static attributes */

    private static final java.util.Random RAND = new java.util.Random();


    /* Attributes */

    private final Player[] playersTab;
    private final Display display;
    private final DistrictsPile pile;
    private final Scoreboard scoreboard;
    private boolean isFinished;
    private Player crownedPlayer;


    /* Constructor */

    public Game() {
        this.playersTab = new Player[NB_PLAYERS];
        this.display = new Display();
        this.pile = new DistrictsPile();
        this.isFinished = false;
        this.scoreboard = new Scoreboard(NB_PLAYERS);
        this.crownedPlayer = null;
    }


    /* Getters */

    public Player[] getPlayersTab() {
        return this.playersTab;
    }


    public Display getDisplay() {
        return this.display;
    }


    public DistrictsPile getPile() {
        return this.pile;
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
        this.playersTab[0] = new Random("HASARDEUX", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[1] = new Spendthrift("DÉPENSIER", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[2] = new Thrifty("ÉCONOME", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[3] = new Monarchist("MONARCHISTE", Arrays.asList(this.pile.draw(4)), this);
        this.display.addPlayers(this.playersTab);
        this.display.addBlankLine();

        for (Player player : this.playersTab) {
            this.display.addFirstDistrictsDrawn(player);
        }
        this.display.addBlankLine();

        // Give 2 golds to every player
        for (Player player : this.playersTab) {
            player.getActions().addGold(2);
        }
        this.display.addInitialGoldGiven(2);
        this.display.addBlankLine();

        this.crownedPlayer = this.playersTab[RAND.nextInt(NB_PLAYERS)];
        this.display.addFirstCrownedPlayer(this.crownedPlayer);
        this.display.addBlankLine(3);

        this.display.printAndReset();
    }


    /**
     * Get the index of the crowned player.
     *
     * @return the index of the crowned player.
     * -1 if there is no crowned player (it should not happen !).
     */
    private int getCrownedPlayerIndex() {
        int length = this.playersTab.length;
        for (int i = 0; i < length; i++) {
            if (this.playersTab[i] == this.crownedPlayer) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        /* Reset character's attributes */
        for (Character character : CharactersList.allCharacterCards) {
            character.setPlayer(null);
            character.setDead(false);
            character.setRobbed(false);
        }

        this.display.addSelectionPhaseTitle();

        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        Character[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        Character[] removedCharactersFaceDown = characters.removeCharactersFaceDown();

        this.display.addRemovedCharacter(removedCharactersFaceUp, removedCharactersFaceDown);
        this.display.addBlankLine(2);

        int crownedPlayerIndex = this.getCrownedPlayerIndex();
        this.display.addCrownedPlayer(this.crownedPlayer);
        this.display.addBlankLine();

        int length = this.playersTab.length;
        int index;

        for (int i = 0; i < length; i++) {
            index = (i + crownedPlayerIndex) % length;
            this.playersTab[index].chooseCharacter(characters);
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
        Character characterKilled = null;

        for (Character character : CharactersList.allCharacterCards) {
            if (character.getPlayer() != null && !character.isDead()) {
                this.display.addPlayerTurn(this.crownedPlayer, character.getPlayer());
                this.display.addBlankLine();
                this.display.addPlayer(character.getPlayer());
                this.display.addBlankLine();

                if (character.isRobbed()) {
                    character.getPlayer().getActions().getRobbed();
                }
                if (character.equals(new King())) {
                    this.crownedPlayer = character.getPlayer();
                    this.display.addKingPower();
                    this.display.addBlankLine();
                }
                character.bringIntoPlay();

                if (character.getPlayer().hasCompleteCity() && !this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(character.getPlayer());
                    this.display.addGameFinished(character.getPlayer());
                    this.display.addBlankLine();
                    this.isFinished = true;
                }
            } else {
                this.display.addNoPlayerTurn(this.crownedPlayer, character);
                this.display.addBlankLine();
                if (character.isDead())
                    characterKilled = character;
            }
            this.display.addBlankLine();
        }
        if ((characterKilled != null) && (characterKilled.getPlayer() != null)) {
            this.display.addWasKilled(characterKilled);
            if (characterKilled.equals(new King())) {
                this.display.addKingHeir();
            }
            this.display.addBlankLine();
        }
        this.display.addBlankLine();
    }


    /**
     * Play the game until a player has a complete city and determine the ranking
     */
    public void play() {
        this.initializeGame();
        int round = 1;

        while (!this.isFinished) {
            this.display.addTurnTitle(round++);

            this.playSelectionPhase();
            this.playTurnPhase();

            this.display.printAndReset();
        }

        this.scoreboard.initialize(this.playersTab);
        this.scoreboard.determineRanking();
        this.display.addScoreTitle();
        this.display.addPlayersCity(this.playersTab);
        this.display.addBlankLine();
        this.display.addScoreboard(this.scoreboard);
        this.display.addBlankLine();
        this.display.addWinner(this.scoreboard.getWinner());
        this.display.printAndReset();
    }

}