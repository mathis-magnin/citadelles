package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;

public class Graveyard extends District {

    /* Constructor */

    public Graveyard() {
        super("Cimetière", Family.UNIQUE, 5);
    }


    /* Method */

    /**
     * When the Warlord destroys a district, the player who built the Graveyard can pay a gold coin to take it back into his hand.
     * The player can't use the effect if he is the Warlord.
     *
     * @return true if the card was added into the player's hand.
     */
    @Override
    public boolean useEffect() {
        District removedDistrict = CharactersList.allCharacterCards[7].getPlayer().getMemory().getDistrictToDestroy();
        if (this.isBuilt() && !this.getOwner().getCharacter().equals(new Warlord())) {
            if (this.getOwner().activateGraveyardEffect(removedDistrict)) {
                this.getOwner().getActions().removeGold(1);
                this.getOwner().getHand().add(removedDistrict);
                this.getOwner().getMemory().getDisplay().addGraveyardEffect(this.getOwner());
                return true;
            }
            this.getOwner().getMemory().getDisplay().addNoGraveyardEffect(this.getOwner());
        }
        return false;
    }

}
