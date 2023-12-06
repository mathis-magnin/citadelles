package fr.citadels.engine;

import fr.citadels.players.Player;

public class Crown {

    /* Attribute */

    private Player player;
    private int playerIndex;

    /* Constructor */

    public Crown() {
        this.player = null;
        this.playerIndex = -1;
    }

    /* Methods */

    /**
     * Get the player who has the crown
     * @return the player who has the crown
     */
    public Player getPlayerWithCrown() {
        return this.player;
    }

    /**
     *Set the player who has the crown
     * @param player the player who has the crown
     */
    public void setPlayerWithCrown(Player player) {
        this.player = player;
    }

    /**
     * Get the index of the player who has the crown
     * @return the index of the player who has the crown
     */
    public int getPlayerIndexWithCrown() {
        return this.playerIndex;
    }

    /**
     * Set the index of the player who has the crown
     * @param playerIndex the index of the player who has the crown
     */
    public void setPlayerIndexWithCrown(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
