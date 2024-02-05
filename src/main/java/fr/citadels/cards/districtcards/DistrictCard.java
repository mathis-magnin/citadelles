package fr.citadels.cards.districtcards;

import fr.citadels.cards.Card;
import fr.citadels.cards.CardFamily;
import fr.citadels.players.Player;

public class DistrictCard extends Card {

    /* Attribute */

    private final int goldCost;

    private Player owner;


    /* Constructor */

    public DistrictCard(String cardName, CardFamily cardFamily, int goldCost) {
        super(cardName, cardFamily);
        this.goldCost = goldCost;
    }


    /* Methods */

    public int getGoldCost() {
        return this.goldCost;
    }


    /**
     * Get the owner of the district card if the card is unique
     *
     * @return null
     */
    public Player getOwner() {
        return owner;
    }


    /***
     * @return a string representation of a district card
     */
    @Override
    public String toString() {
        return this.getCardName() + " (" + this.getGoldCost() + " - " + this.getCardFamily() + ")";
    }


    /**
     * Set the owner attribute of the unique district card
     *
     * @param owner the player who built the district card
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    /**
     * return true if the district card is built, false otherwise (default value is false)
     *
     * @return the isBuilt attribute
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