package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;

public class MagicianCard extends CharacterCard {

    /* Constructor */

    public MagicianCard() {
        super("Magicien", CardFamily.NEUTRAL, 3);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsMagician();
    }

    public void usePower(){
        return;
    }

}
