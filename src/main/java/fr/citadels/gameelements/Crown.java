package fr.citadels.gameelements;

import fr.citadels.engine.Game;
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
     * return the player index of the one who is currently the king.
     *
     * @param players the list of the player of the game.
     * @return the player index of the one who is currently the king.
     */
    public int getKingPlayer(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getCharacter() != null && players[i].getCharacter().getCardName().equals("Roi")) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Give the crown to a random player.
     */
    public void initializeCrown(Random rand) {
        this.playerIndex = rand.nextInt(Game.NB_PLAYERS);
    }


    /**
     * Define the next player who will have the crown.
     * Note : This procedure may be deleted if we choose to give the crown during the king's turn.
     *
     * @param players the list of the player of the game.
     * @precondition The procedure initializeCrown() must have been called once.
     */
    public void defineNextCrownedPlayer(Player[] players, Random rand) {
        int index = getKingPlayer(players);
        if (index != -1) {
            this.playerIndex = index;
        }
        else if(this.playerIndex == -1) {
            initializeCrown(rand);
        }
    }

}