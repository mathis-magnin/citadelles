package fr.citadels.cards.districtcards.uniques;

public class Factory extends Unique {
    public Factory() {
        super("Manufacture", 5);
    }


    @Override
    public boolean useEffect() {
        if (isBuilt() && getOwner().activateFactoryEffect()) {
            getOwner().getInformation().getDisplay().addFactoryEffectActivated();
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
