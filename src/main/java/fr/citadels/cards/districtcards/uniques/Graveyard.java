package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.DistrictCard;

public class Graveyard extends Unique {

    /* Constructor */

    public Graveyard() {
        super("Cimetière", 5);
    }


    /* Method */

    /**
     * When the Warlord destroys a district, the player who built the Graveyard can pay a gold coin to take it back into his hand.
     * The player can't use the effect if he is the Warlord.
     *
     * @param removedDistrict the district removed by the Warlord.
     * @return true if the card was added into the player's hand.
     */
    public boolean useEffect(DistrictCard removedDistrict) {
        if (this.isBuilt() && !this.getOwner().getCharacter().equals(new WarlordCard())) {
            if (this.getOwner().activateGraveyardEffect(removedDistrict)) {
                this.getOwner().getActions().removeGold(1);
                this.getOwner().getHand().add(removedDistrict);
                this.getOwner().getInformation().getDisplay().addGraveyardEffect(this.getOwner());
                return true;
            }
            this.getOwner().getInformation().getDisplay().addNoGraveyardEffect(this.getOwner());
        }
        return false;
    }

}
