package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;
import fr.citadels.cards.districtcards.uniques.Unique;

public class Graveyard extends Unique {

    /* Constructor */

    public Graveyard() {
        super("Cimetière", 5);
    }


    /* Method */

    public boolean useEffect(DistrictCard cardRemoved) {
        if (this.getOwner() != null) {
            this.getOwner().chooseToRecoverDistrict();
            if (!this.getOwner().getCharacter().equals(new WarlordCard()) && this.getOwner().getInformation().getRecoverDistrictDecision()) {
                this.getOwner().getActions().removeGold(1);
                this.getOwner().getHand().add(cardRemoved);
                this.getOwner().getInformation().getDisplay().addGraveyardEffect(this.getOwner());
                this.getOwner().getInformation().setRecoverDistrictDecision(false);
                return true;
            }
            this.getOwner().getInformation().getDisplay().addNoGraveyardEffect(this.getOwner());
        }
        return false;
    }

}
