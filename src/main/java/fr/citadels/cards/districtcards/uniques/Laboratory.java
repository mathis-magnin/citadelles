package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.uniques.Unique;

public class Laboratory extends Unique {
    public Laboratory() {
        super("Laboratoire", 5);
    }


    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().activateLaboratoryEffect()) {
            getOwner().getActions().addGold(1);
            DistrictCard card = getOwner().getHand().remove(getOwner().getHand().size() - 1);
            getOwner().getInformation().getPile().placeBelowPile(card);
            getOwner().getInformation().getDisplay().addLaboratoryEffectActivated(card, getOwner());
            return true;
        }
        return false;
    }
}
