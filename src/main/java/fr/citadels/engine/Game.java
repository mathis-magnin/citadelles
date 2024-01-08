package fr.citadels.engine;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.players.*;

import java.util.*;

public class Game {

    /*
    const nbDistrict to win
     */

    private static final Random RAND = new Random();

    /* Attributes */

    private final Player[] playerList;

    private final DistrictCardsPile districtCardsPile;
    private final Crown crown;
    private boolean isFinished;
    private Scoreboard scoreboard;

    public static final int NB_PLAYERS = 4;
    public static final Bank BANK=new Bank();

    /* Constructor */

    public Game() {
        this.playerList = new Player[NB_PLAYERS];
        this.districtCardsPile = new DistrictCardsPile();
        this.crown = new Crown();
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

        //Initialize the players
        List<DistrictCard> cards = new ArrayList<>(Arrays.asList(districtCardsPile.draw(4)));
        this.playerList[0] = new RandomBot("Joueur 1", cards, RAND);

        cards = new ArrayList<>(Arrays.asList(districtCardsPile.draw(4)));
        this.playerList[1] = new ThriftyBot("Joueur 2", cards,RAND);

        cards = new ArrayList<>(Arrays.asList(districtCardsPile.draw(4)));
        this.playerList[2] = new SpendthriftBot("Joueur 3", cards,RAND);

        cards = new ArrayList<>(Arrays.asList(districtCardsPile.draw(4)));
        this.playerList[3] = new KingBot("Joueur 4", cards,RAND);

        this.scoreboard = new Scoreboard(this.playerList);
        this.crown.initializeCrown(RAND);
    }

    /**
     * Play the selection phase of the turn
     */
    public void playSelectionPhase() {
        int crownedPlayerIndex = this.crown.getCrownedPlayerIndex();
        int length = this.playerList.length;

        CharacterCardsList characters = new CharacterCardsList();
        Collections.shuffle(characters);
        CharacterCard[] removedCharactersFaceUp = characters.removeCharactersFaceUp();
        CharacterCard[] removedCharactersFaceDown = characters.removeCharactersFaceDown();

        for (int i = 0; i < length; i++) {
            this.playerList[(i + crownedPlayerIndex) % length].chooseCharacter(characters);
        }
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
            System.out.println(player.play(this.districtCardsPile));
            if (player.hasCompleteCity()) {
                if (!this.isFinished) {
                    Score.setFirstPlayerWithCompleteCity(player);
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
            this.crown.defineNextCrownedPlayer(this.playerList,RAND);
            System.out.print("Le joueur " + this.playerList[this.crown.getCrownedPlayerIndex()].getName() + " a la couronne.\n");
            this.playTurn();
        }

        this.scoreboard.determineRanking();
        System.out.println("Le gagnant est " + this.scoreboard.getWinner().getName() + " !\n");
        System.out.println(this.scoreboard);
    }

}