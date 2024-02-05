package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;

public class Laboratory extends DistrictCard {
    public Laboratory() {
        super("Laboratoire", CardFamily.UNIQUE, 5);
    }


    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().activateLaboratoryEffect()) {
            getOwner().getActions().addGold(1);
            DistrictCard card = getOwner().getHand().remove(getOwner().getHand().size() - 1);
            getOwner().getInformation().getPile().placeBelowPile(card);
            getOwner().getInformation().getDisplay().addLaboratoryEffect(card, getOwner());
            return true;
        }
        return false;
    }
}
