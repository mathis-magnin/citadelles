package fr.citadels.engine;

import fr.citadels.engine.score.Score;
import fr.citadels.engine.score.Scoreboard;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.charactercards.characters.KingCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
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

    private final Player[] playersTab;
    private final Display display;
    private final DistrictCardsPile pile;
    private final Scoreboard scoreboard;
    private boolean isFinished;
    private Player crownedPlayer;


    /* Constructor */

    public Game() {
        this.playersTab = new Player[NB_PLAYERS];
        this.display = new Display();
        this.pile = new DistrictCardsPile();
        this.isFinished = false;
        this.scoreboard = new Scoreboard(NB_PLAYERS);
        this.crownedPlayer = null;
    }


    /* Getters */

    public Player[] getPlayerList() {
        return this.playersTab;
    }


    public Display getDisplay() {
        return this.display;
    }


    public DistrictCardsPile getPile() {
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
        this.playersTab[0] = new RandomBot("HASARDEUX", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[1] = new SpendthriftBot("DÉPENSIER", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[2] = new ThriftyBot("ÉCONOME", Arrays.asList(this.pile.draw(4)), this, RAND);
        this.playersTab[3] = new KingBot("MONARCHISTE", Arrays.asList(this.pile.draw(4)), this);
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
        for (CharacterCard character : CharacterCardsList.allCharacterCards) {
            character.setPlayer(null);
            character.setDead(false);
            character.setRobbed(false);
        }

        this.display.addSelectionPhaseTitle();

        CharacterCardsList characters = new CharacterCardsList(CharacterCardsList.allCharacterCards);
        CharacterCard[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        CharacterCard[] removedCharactersFaceDown = characters.removeCharactersFaceDown();

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
        CharacterCard characterKilled = null;

        for (CharacterCard character : CharacterCardsList.allCharacterCards) {
            if (character.getPlayer() != null && !character.isDead()) {
                this.display.addPlayerTurn(this.crownedPlayer, character.getPlayer());
                this.display.addBlankLine();
                this.display.addPlayer(character.getPlayer());
                this.display.addBlankLine();

                if (character.isRobbed()) {
                    character.getPlayer().getActions().getRobbed();
                }
                if (character.equals(new KingCard())) {
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
            if (characterKilled.equals(new KingCard())) {
                this.display.addKingHeir();
            }
            this.display.addBlankLine();
        }
        this.display.addBlankLine();
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

        this.scoreboard.initializeScoreboard(this.playersTab);
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