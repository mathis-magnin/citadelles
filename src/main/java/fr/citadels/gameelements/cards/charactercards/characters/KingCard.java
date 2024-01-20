package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;

public class KingCard extends CharacterCard {

    /* Constructor */

    public KingCard() {
        super("Roi", CardFamily.NOBLE, 4);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsKing();
    }

    public void usePower(){
        return;
    }

}
