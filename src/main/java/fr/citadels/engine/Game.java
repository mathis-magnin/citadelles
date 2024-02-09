package fr.citadels.engine;

import fr.citadels.cards.characters.CharactersDeck;
import fr.citadels.cards.characters.Role;
import fr.citadels.engine.score.Score;
import fr.citadels.engine.score.Scoreboard;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.roles.King;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.players.*;


import java.util.*;


public class Game {

    /* Constant values */

    public static final int PLAYER_NUMBER = 5;


    /* Attributes */

    private final Random random;
    private final Display display;

    private final DistrictsPile pile;
    private final CharactersDeck characters;
    private final Player[] players;
    private Player crownedPlayer;

    private int turnNumber;
    private boolean isFinished;
    private final Scoreboard scoreboard;


    /* Constructor */

    public Game(Player[] players, Random random) {
        this.random = random;
        this.display = new Display();

        this.pile = new DistrictsPile();
        this.characters = new CharactersDeck(random);
        this.players = players;
        this.crownedPlayer = null;

        this.turnNumber = 1;
        this.isFinished = false;
        this.scoreboard = new Scoreboard(PLAYER_NUMBER);
    }


    /* Basic methods */

    public Display getDisplay() {
        return this.display;
    }


    public DistrictsPile getPile() {
        return this.pile;
    }


    public CharactersDeck getCharactersDeck() {
        return this.characters;
    }


    public Player[] getPlayers() {
        return this.players;
    }


    public Player getCrownedPlayer() {
        return this.crownedPlayer;
    }


    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }


    public boolean isFinished() {
        return this.isFinished;
    }


    public Integer getTurnNumber() {
        return this.turnNumber;
    }


    /* Methods */

    /**
     * Initialize players
     */
    public void initializePlayers() {
        for (Player player : this.players) {
            player.initPlayer(List.of(pile.draw(4)), this);
        }
    }


    /**
     * Initialize the game
     */
    public void initializeGame() {
        this.pile.reset();
        this.pile.initializePile();
        this.pile.shufflePile();

        this.display.addGameTitle();

        // Initialize the players
        this.initializePlayers();
        this.display.addPlayers(this.players);
        this.display.addBlankLine();

        for (Player player : this.players) {
            this.display.addFirstDistrictsDrawn(player);
        }
        this.display.addBlankLine();

        // Give 2 golds to every player
        for (Player player : this.players) {
            player.getActions().addGold(2);
        }
        this.display.addInitialGoldGiven(2);
        this.display.addBlankLine();

        this.crownedPlayer = this.players[random.nextInt(PLAYER_NUMBER)];
        this.placeKingAtTheStartOfPlayers();
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
        int length = this.players.length;
        for (int i = 0; i < length; i++) {
            if (this.players[i] == this.crownedPlayer) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Show the selected characters of the players
     */
    public void showSelectedCharacters() {
        for (Player player : this.players) {
            player.getMemory().getDisplay().addCharacterChosen(player, player.getCharacter());
        }
    }


    /**
     * Rotate the list in order to place the crownedPlayer at the first position
     */
    public void placeKingAtTheStartOfPlayers() {
        List<Player> playersList = Arrays.asList(this.players);
        Collections.rotate(playersList, (this.players.length - this.getCrownedPlayerIndex()));
        int i = 0;
        for(Player player : playersList) {
            this.players[i] = player;
            i++;
        }
    }


    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        this.display.addSelectionPhaseTitle();

        this.characters.determineRemovedCharacters();
        Character[] faceUpCharacters = this.characters.getFaceUpCharacters();
        Character[] faceDownCharacters = this.characters.getFaceDownCharacters();
        this.display.addRemovedCharacters(faceUpCharacters, faceDownCharacters);
        this.display.addBlankLine(2);

        this.display.addCrownedPlayer(this.crownedPlayer);
        this.display.addBlankLine();

        List<Character> charactersList = List.of(this.characters.get());

        for (int i = 0; i < this.players.length; i++) {
            this.players[i].getMemory().setFaceUpCharacters(faceUpCharacters);
            this.players[i].getMemory().setPossibleCharacters(charactersList);
            this.players[i].getMemory().setPlayers(this.players);
            this.players[i].getMemory().setPlayerIndex(i);
            this.players[i].chooseCharacter(charactersList);
        }
        this.showSelectedCharacters();
        this.display.addBlankLine();
        this.display.addCharacterNotChosen(charactersList.remove(0)); // Only one character is not chosen

        this.display.addBlankLine();
    }


    /**
     * Check if the game is finished and mark it if it is
     *
     * @param character the character to check
     */
    public void checkAndMarkEndOfGame(Character character) {
        if (character.getPlayer().hasCompleteCity() && !this.isFinished) {
            Score.setFirstPlayerWithCompleteCity(character.getPlayer());
            this.display.addGameFinished(character.getPlayer());
            this.display.addBlankLine();
            this.isFinished = true;
        }
    }


    /**
     * Set the crowned player to King
     *
     * @param character the potential character to set as the crowned player
     */
    public void setNextCrownedPlayerIfPossible(Character character) {
        if (character.equals(new King())) {
            this.crownedPlayer = character.getPlayer();
            this.placeKingAtTheStartOfPlayers();
            this.display.addKingPower();
            this.display.addBlankLine();
        }
    }


    /**
     * Play a turn for each player
     */
    public void playTurnPhase() {
        this.display.addTurnPhaseTitle();
        Character characterKilled = null;

        for (Character character : this.characters.get()) {
            if (character.isPlayed() && !character.isDead()) {
                this.display.addPlayerTurn(this.crownedPlayer, character.getPlayer());
                this.display.addBlankLine();
                this.display.addPlayer(character.getPlayer());
                this.display.addBlankLine();

                if (character.isRobbed()) {
                    character.getPlayer().getActions().getRobbed();
                }

                setNextCrownedPlayerIfPossible(character);
                character.bringIntoPlay();
                character.getPlayer().getMemory().setTurnNumber(this.turnNumber);
                character.getPlayer().getMemory().setPreviousArchitect(this.characters.get(Role.ARCHITECT).getPlayer());
                checkAndMarkEndOfGame(character);
            } else {
                this.display.addNoPlayerTurn(this.crownedPlayer, character);
                this.display.addBlankLine();
                if (character.isDead()) {
                    characterKilled = character;
                }
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

        while (!this.isFinished) {
            this.display.addTurnTitle(this.turnNumber);

            this.playSelectionPhase();
            this.playTurnPhase();

            this.display.printAndReset();
            this.turnNumber++;
        }

        this.scoreboard.initialize(this.players);
        this.scoreboard.determineRanking();
        this.display.addScoreTitle();
        this.display.addPlayersCity(this.players);
        this.display.addBlankLine();
        this.display.addScoreboard(this.scoreboard);
        this.display.addBlankLine();
        this.display.addWinner(this.scoreboard.getWinner());
        this.display.printAndReset();

    }

}