package fr.citadels.engine;

import fr.citadels.players.Player;

import java.util.Random;

public class Crown {

    /* Attribute */

    private int playerIndex;


    /* Constructor */

    public Crown() {
        this.playerIndex = -1;
    }


    /* Basic methods */

    public int getCrownedPlayerIndex() {
        return this.playerIndex;
    }


    /**
     * Note : This procedure may be deleted of we choose to define the next crowned owner at the beginning of each game turn.
     */
    public void setCrownedPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }


    /* Methods */

    /**
     * Give the crown to a random player.
     */
    public void initializeCrown() {
        Random randomIndex = new Random();
        this.playerIndex = randomIndex.nextInt(Game.NB_PLAYERS);
    }


    /**
     * Define the next player who will have the crown.
     * Note : This procedure may be deleted if we choose to give the crown during the king's turn.
     *
     * @param players the list of the player of the game.
     * @precondition The procedure initializeCrown() must have been called once.
     */
    public void defineNextCrownedPlayer(Player[] players) {
        this.initializeCrown(); // Milestone 2 : No character, no king to check.
    }

}