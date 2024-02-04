package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

public class Graveyard extends DistrictCard {

    /* Attribute */

    private Player owner;


    /* Constructor */

    public Graveyard() {
        super("Cimetière", CardFamily.UNIQUE, 5);
        this.owner = null;
    }


    /* Basic method */

    @Override
    public void build(boolean build, Player owner) {
        super.build(build, owner);
        this.owner = owner;
    }


    /* Method */

    public boolean useEffect(DistrictCard cardRemoved) {
        if (this.owner != null) {
            this.owner.chooseToRecoverDistrict();
            if (!this.owner.getCharacter().equals(new WarlordCard()) && this.owner.getInformation().getRecoverDistrictDecision()) {
                this.owner.getActions().removeGold(1);
                this.owner.getHand().add(cardRemoved);
                this.owner.getInformation().getDisplay().addGraveyardEffect(this.owner);
                this.owner.getInformation().setRecoverDistrictDecision(false);
                return true;
            }
            this.owner.getInformation().getDisplay().addNoGraveyardEffect(this.owner);
        }
        return false;
    }

}
