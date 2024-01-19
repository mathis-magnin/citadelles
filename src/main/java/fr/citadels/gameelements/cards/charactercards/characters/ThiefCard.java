package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class ThiefCard extends CharacterCard {

    /* Constructor */

    public ThiefCard() {
        super("Voleur", CardFamily.NEUTRAL, 2);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsThief();
        }
    }

    public void usePower(){
        return;
    }

}
