package fr.citadels.cards.districts.uniques;

import fr.citadels.cards.Family;
import fr.citadels.cards.districts.District;

public class Laboratory extends District {

    /* Constructor */

    public Laboratory() {
        super("Laboratoire", Family.UNIQUE, 5);
    }

    /* Method */

    /**
     * Once per turn, the player who owns this district can discard one district from his hand
     * and gain 1 gold coin
     *
     * @return true if the effect is used
     */
    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().chooseLaboratoryEffect()) {
            getOwner().getActions().addGold(1);
            District card = getOwner().getHand().remove(getOwner().getHand().size() - 1);
            getOwner().getMemory().getPile().placeBelowPile(card);
            getOwner().getMemory().getDisplay().addLaboratoryEffect(card, getOwner());
            return true;
        }
        return false;
    }
}
