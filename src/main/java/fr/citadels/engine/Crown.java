package fr.citadels.engine;

import fr.citadels.players.Player;

public class Crown {

    /* Attribute */

    private Player player;
    private int playerIndex;

    /* Constructor */

    public Crown() {
        this.player = null;
    }

    /* Methods */

    /***
     * get the player who has the crown
     * @return the player who has the crown
     */
    public Player getPlayerWithCrown() {
        return this.player;
    }

    /***
     * set the player who has the crown
     * @param player the player who has the crown
     */
    public void setPlayerWithCrown(Player player) {
        this.player = player;
    }

    /***
     * get the index of the player who has the crown
     * @return the index of the player who has the crown
     */
    public int getPlayerIndexWithCrown() {
        return this.playerIndex;
    }

    /***
     * set the index of the player who has the crown
     * @param playerIndex the index of the player who has the crown
     */
    public void setPlayerIndexWithCrown(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
