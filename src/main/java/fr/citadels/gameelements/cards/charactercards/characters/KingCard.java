package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class KingCard extends CharacterCard {

    /* Constructor */

    public KingCard() {
        super("Roi", CardFamily.NOBLE, 4);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsKing();
        }
    }

    public void usePower(){
        return;
    }

}
