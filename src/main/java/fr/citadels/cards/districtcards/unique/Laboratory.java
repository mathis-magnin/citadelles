package fr.citadels.cards.districtcards.unique;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;

public class Laboratory extends DistrictCard {
    private Player owner;

    public Laboratory() {
        super("Laboratoire", CardFamily.UNIQUE, 5);
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
        if (owner != null && owner.activateLaboratoryEffect() && isBuilt()) {
            DistrictCard card = owner.getHand().remove(owner.getHand().size()-1);
            owner.getInformation().getPile().placeBelowPile(card);
            owner.getActions().addGold(1);
            owner.getInformation().getDisplay().addLaboratoryEffectActivated(owner, card);
        }
    }
}
