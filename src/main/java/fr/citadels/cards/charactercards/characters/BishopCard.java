package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;

public class BishopCard extends CharacterCard {

    /* Constructor */

    public BishopCard() {
        super("Évêque", CardFamily.RELIGIOUS, 5);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsBishop();
    }

    public void usePower() {
        return;
    }

}
