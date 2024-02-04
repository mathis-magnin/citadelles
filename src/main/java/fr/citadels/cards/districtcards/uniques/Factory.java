package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.districtcards.uniques.Unique;

public class Factory extends Unique {
    public Factory() {
        super("Manufacture", 5);
    }


    @Override
    public void useEffect() {
        if (isBuilt() && getOwner().activateFactoryEffect(this.getOwner())) {
            getOwner().getInformation().getDisplay().addFactoryEffectActivated();
            getOwner().getActions().draw(3);
            getOwner().getActions().removeGold(3);
            getOwner().getHand().sortCards(getOwner().getCharacter().getCardFamily());
        }
    }
}
