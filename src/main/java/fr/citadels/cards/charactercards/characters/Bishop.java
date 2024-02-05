package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;

public class Bishop extends Character {

    /* Constructor */

    public Bishop() {
        super("Évêque", Family.RELIGIOUS, 5);
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
