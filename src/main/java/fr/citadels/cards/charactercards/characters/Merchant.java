package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;

public class Merchant extends Character {

    /* Constructor */

    public Merchant() {
        super("Marchand", Family.TRADE, 6);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsMerchant();
    }

    public void usePower() {
        this.getPlayer().getActions().addGold(1);
        this.getPlayer().getInformation().getDisplay().addMerchantPower(this);
        this.getPlayer().getInformation().getDisplay().addBlankLine();
    }

}
