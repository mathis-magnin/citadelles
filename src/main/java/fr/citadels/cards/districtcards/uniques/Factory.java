package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.Family;
import fr.citadels.cards.districtcards.District;

public class Factory extends District {

    /* Constructor */

    public Factory() {
        super("Manufacture", Family.UNIQUE, 5);
    }


    /* Method */

    /**
     * Once per turn, the player who owns this district can pay 3 gold coins to draw 3 cards
     *
     * @return true if the effect is used
     */
    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().activateFactoryEffect()) {
            getOwner().getInformation().getDisplay().addFactoryEffect();
            getOwner().getActions().draw(3);
            getOwner().getActions().removeGold(3);
            if (getOwner().getCharacter() != null) {
                getOwner().getHand().sortCards(getOwner().getCharacter().getFamily());
            }
            return true;
        }
        return false;
    }
}
