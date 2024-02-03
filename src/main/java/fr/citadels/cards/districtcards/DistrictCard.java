package fr.citadels.cards.districtcards;

import fr.citadels.cards.Card;
import fr.citadels.cards.CardFamily;
import fr.citadels.players.Player;

public class DistrictCard extends Card {

    /* Attribute */

    private boolean built;
    private final int goldCost;

    /* Constructor */

    public DistrictCard(String cardName, CardFamily cardFamily, int goldCost) {
        super(cardName, cardFamily);
        this.goldCost = goldCost;
        this.built = false;
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
     * @param build the built attribute
     */
    public void build(boolean build, Player owner) {
        this.built = build;
    }

    /**
     * Get the isBuilt attribute of the district card
     *
     * @return the isBuilt attribute
     */
    public boolean isBuilt() {
        return this.built;
    }

    /**
     * Use the ability of the district card
     */
    public void useEffect() {
        // will be implemented in the subclasses
    }

}