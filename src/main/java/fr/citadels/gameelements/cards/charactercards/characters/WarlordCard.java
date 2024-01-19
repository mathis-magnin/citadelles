package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class WarlordCard extends CharacterCard {

    /* Constructor */

    public WarlordCard() {
        super("Condottiere", CardFamily.MILITARY, 8);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsWarlord();
        }
    }

    public void usePower(){
        return;
    }

}
