package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class BishopCard extends CharacterCard {

    /* Constructor */

    public BishopCard() {
        super("Évêque", CardFamily.RELIGIOUS, 5);
    }

    /* Methods */

    @Override
    public void bringIntoPlay(DistrictCardsPile pile, Bank bank, Display display) {
        if(this.getPlayer() != null) {
            //this.getPlayer().playAsBishop(pile,bank,display);
        }
    }

    public void usePower(){
        return;
    }

}