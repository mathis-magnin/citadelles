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


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * INCOME : The player gain one gold coin per Religious district he has in his city.
     * The SchoolOfMagic district act as a Religious district.
     */
    @Override
    public void usePower() {
        super.income();
    }

}