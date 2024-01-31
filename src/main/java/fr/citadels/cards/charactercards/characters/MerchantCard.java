package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;

public class MerchantCard extends CharacterCard {

    /* Constructor */

    public MerchantCard() {
        super("Marchand", CardFamily.TRADE, 6);
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
