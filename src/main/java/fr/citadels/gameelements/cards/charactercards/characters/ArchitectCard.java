package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class ArchitectCard extends CharacterCard {

    /* Constructor */

    public ArchitectCard() {
        super("Architecte", CardFamily.NEUTRAL, 7);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsArchitect();
        }
    }


    public void usePower(){
        switch (this.getPlayer().getPowerToUse()) {
            case 1 :
                this.drawTwoCards();
                break;
            case 2:
                this.build();
                break;
        }
    }


    private void drawTwoCards() {
        this.getPlayer().getDisplay().add;
        this.getPlayer().draw(2);
    }


    private void build() {
        this.getPlayer().build();
    }

}
