package fr.citadels.cards.districts;

import fr.citadels.cards.Card;
import fr.citadels.cards.Family;
import fr.citadels.players.Player;

public class District extends Card {

    /* Attributes */

    private final int goldCost;
    private Player owner;


    /* Constructor */

    public District(String cardName, Family cardFamily, int goldCost) {
        super(cardName, cardFamily);
        this.goldCost = goldCost;
    }


    /* Basic methods */

    public int getGoldCost() {
        return this.goldCost;
    }


    public Player getOwner() {
        return owner;
    }


    public void setOwner(Player owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return this.getName() + " (" + this.getGoldCost() + " - " + this.getFamily() + ")";
    }


    /* Methods */

    /**
     * @return true if the district card is built, false otherwise.
     */
    public boolean isBuilt() {
        return owner != null;
    }


    /**
     * Use the ability of the district card
     */
    public boolean useEffect() {
        return false;
    }

}