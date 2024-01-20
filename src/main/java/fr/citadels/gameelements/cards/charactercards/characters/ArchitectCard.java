package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;

public class ArchitectCard extends CharacterCard {

    /* Constructor */

    public ArchitectCard() {
        super("Architecte", CardFamily.NEUTRAL, 7);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsArchitect();
    }

    public void usePower(){
        return;
    }
}
