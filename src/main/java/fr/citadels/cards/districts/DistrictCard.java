package fr.citadels.cards.districts;

import fr.citadels.cards.Card;
import fr.citadels.cards.CardFamily;

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

}