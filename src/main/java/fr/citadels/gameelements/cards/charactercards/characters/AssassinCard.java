package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class AssassinCard extends CharacterCard {

    /* Constructor */

    public AssassinCard() {
        super("Assassin", CardFamily.NEUTRAL, 1);
    }

    /* Methods */

    @Override
    public void bringIntoPlay(DistrictCardsPile pile, Bank bank, Display display) {
        if(this.getPlayer() != null) {
            //this.getPlayer().playAsAssasin(pile,bank,display);
        }
    }

    public void usePower(){
        return;
    }

}
