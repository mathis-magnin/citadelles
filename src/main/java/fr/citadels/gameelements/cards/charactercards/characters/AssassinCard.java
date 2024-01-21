package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;

public class AssassinCard extends CharacterCard {

    /* Constructor */

    public AssassinCard() {
        super("Assassin", CardFamily.NEUTRAL, 1);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsAssassin();
    }

    public void usePower() {
        getPlayer().getInformation().getTarget().setDead(true);
        getPlayer().getInformation().getDisplay().killed(getPlayer().getInformation().getTarget());
        getPlayer().getInformation().getDisplay().addBlankLine();
    }

}
