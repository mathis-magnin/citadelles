package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;

public class King extends Character {

    /* Constructor */

    public King() {
        super("Roi", Family.NOBLE, 4);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsKing();
    }

    public void usePower() {
        return;
    }

}
