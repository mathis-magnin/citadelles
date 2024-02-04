package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;


public class Factory extends DistrictCard {
    private Player owner;

    public Factory() {
        super("Manufacture", CardFamily.UNIQUE, 5);
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public void build(boolean build, Player owner) {
        super.build(build, owner);
        this.owner = owner;
    }


    @Override
    public void useEffect() {
        if (owner != null && owner.activateFactoryEffect() && isBuilt()) {
            owner.getInformation().getDisplay().addFactoryEffectActivated(owner.getHand());
            owner.getActions().draw(3);
            owner.getActions().removeGold(3);
            owner.getHand().sortCards(owner.getCharacter().getCardFamily());
        }
    }
}
