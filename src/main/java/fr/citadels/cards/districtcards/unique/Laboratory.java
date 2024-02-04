package fr.citadels.cards.districtcards.unique;

import fr.citadels.cards.districtcards.DistrictCard;

public class Laboratory extends Unique {
    public Laboratory() {
        super("Laboratoire", 5);
    }


    @Override
    public void useEffect() {
        if (isBuilt() && getOwner().activateLaboratoryEffect()) {
            System.out.println(getOwner().getName() + " A LE LABORATOIRE !!!! \n---------------------------------------------------------\n\n\n\n\n\n\n\n");
            getOwner().getActions().addGold(1);
            DistrictCard card = getOwner().getHand().remove(getOwner().getHand().size() - 1);
            getOwner().getInformation().getPile().placeBelowPile(card);
            getOwner().getInformation().getDisplay().addLaboratoryEffectActivated(card, getOwner());
        }
    }
}
