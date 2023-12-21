package fr.citadels.cards.districts;

import fr.citadels.cards.Card;

public class DistrictCard extends Card {

    /* Attribute */

    private final int goldCost;

    /* Constructor */

    public DistrictCard(String cardName, int goldCost) {
        super(cardName);
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
        return "Carte quartier " + this.getCardName() + " (" + this.getGoldCost() + ")";
    }

}