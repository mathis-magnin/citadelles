package fr.citadels.cards.districtcards.unique;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

public class Unique extends DistrictCard {

    private Player owner;


    /**
     * Constructor
     *
     * @param cardName the name of the unique district card
     * @param goldCost the cost of the unique district card
     */
    public Unique(String cardName, int goldCost) {
        super(cardName, CardFamily.UNIQUE, goldCost);
        owner = null;
    }

    /**
     * @return the owner of the unique district card
     */
    @Override
    public Player getOwner() {
        return owner;
    }

    /**
     * Set the isBuilt attribute of the district card
     *
     * @param owner the player who built the district card
     */
    @Override
    public void build(Player owner) {
        this.owner = owner;
    }

    /**
     * return true if the district card is built, false otherwise (default value is false)
     *
     * @return the isBuilt attribute
     */
    @Override
    public boolean isBuilt() {
        return owner != null;
    }
}
