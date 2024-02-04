package fr.citadels.cards.districtcards.unique;

import fr.citadels.cards.districtcards.DistrictCard;

public class Laboratory extends Unique {
    public Laboratory() {
        super("Laboratoire", 5);
    }


    @Override
    public void useEffect() {
        if (isBuilt() && getOwner().activateLaboratoryEffect(this.getOwner())) {
            getOwner().getActions().addGold(3);
            DistrictCard card = getOwner().getHand().remove(getOwner().getHand().size() - 1);
            getOwner().getInformation().getPile().placeBelowPile(card);
            getOwner().getInformation().getDisplay().addLaboratoryEffectActivated(card, getOwner());
        }
    }
}
