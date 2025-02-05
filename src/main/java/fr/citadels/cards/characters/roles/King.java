package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;

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


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * INCOME : The player gain one gold coin per Noble district he has in his city.
     * The SchoolOfMagic district act as a Noble district.
     */
    @Override
    public void usePower() {
        super.income();
    }

}