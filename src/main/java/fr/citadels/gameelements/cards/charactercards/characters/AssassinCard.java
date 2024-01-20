package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class AssassinCard extends CharacterCard {

    /* Constructor */

    public AssassinCard() {
        super("Assassin", CardFamily.NEUTRAL, 1);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if (this.getPlayer() != null) {
            this.getPlayer().playAsAssassin();
        }

    }

    public void usePower() {
        getPlayer().getTarget().setDead(true);
        getPlayer().getDisplay().killed(getPlayer().getTarget());
        getPlayer().getDisplay().addBlankLine();

    }

}
