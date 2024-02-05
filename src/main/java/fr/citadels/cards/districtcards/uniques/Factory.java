package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;

public class Factory extends DistrictCard {
    public Factory() {
        super("Manufacture", CardFamily.UNIQUE, 5);
    }


    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().activateFactoryEffect()) {
            getOwner().getInformation().getDisplay().addFactoryEffect();
            getOwner().getActions().draw(3);
            getOwner().getActions().removeGold(3);
            if (getOwner().getCharacter() != null) {
                getOwner().getHand().sortCards(getOwner().getCharacter().getCardFamily());
            }
            return true;
        }
        return false;
    }
}
