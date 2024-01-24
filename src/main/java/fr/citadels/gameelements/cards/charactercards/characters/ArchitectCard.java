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
        switch (this.getPlayer().getPowerToUse()) {
            case 1 :
                this.drawTwoCards();
                break;
            case 2:
                this.build();
                break;
        }
        this.getPlayer().getDisplay().addBlankLine();
    }


    private void drawTwoCards() {
        this.getPlayer().getDisplay().addArchitectPower(1, this.getPlayer());
        this.getPlayer().draw(2);
    }


    private void build() {
        this.getPlayer().getDisplay().addArchitectPower(2, this.getPlayer());
        this.getPlayer().build();
    }

}
