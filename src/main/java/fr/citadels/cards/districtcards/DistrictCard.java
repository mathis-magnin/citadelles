package fr.citadels.cards.districtcards;

import fr.citadels.cards.Card;
import fr.citadels.cards.CardFamily;
import fr.citadels.players.Player;

public class DistrictCard extends Card {

    /* Attribute */

    private final int goldCost;

    /* Constructor */

    public DistrictCard(String cardName, CardFamily cardFamily, int goldCost) {
        super(cardName, cardFamily);
        this.goldCost = goldCost;
    }

    /* Methods */

    public int getGoldCost() {
        return this.goldCost;
    }

    /***
     * @return a string representation of a district card
     */
    @Override
    public String toString() {
        return this.getCardName() + " (" + this.getGoldCost() + " - " + this.getCardFamily() + ")";
    }


    /**
     * Set the isBuilt attribute of the district card
     *
     * @param owner the player who built the district card
     */
    public void build(Player owner) {
        // will be implemented in the subclasses
    }

    /**
     * return true if the district card is built, false otherwise (default value is false)
     *
     * @return the isBuilt attribute
     */
    public boolean isBuilt() {
        return false;
    }

    /**
     * Use the ability of the district card
     */
    public void useEffect() {
        // will be implemented in the subclasses
    }

}