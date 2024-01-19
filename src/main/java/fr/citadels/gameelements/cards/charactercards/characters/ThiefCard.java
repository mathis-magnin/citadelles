package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class ThiefCard extends CharacterCard {

    /* Constructor */

    public ThiefCard() {
        super("Voleur", CardFamily.NEUTRAL, 2);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsThief();
        }
    }

    /**
     * The thief steals all the gold of the character he chooses (the target).
     * The target can be neither the thief nor the assassin nor dead.
     */
    @Override
    public void usePower(){
        this.getPlayer().getTarget().setRobbed(true);
        this.getPlayer().getDisplay().addThiefPower(this.getPlayer(), this.getPlayer().getTarget());
    }

}
