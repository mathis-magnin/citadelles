package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;

public class MerchantCard extends CharacterCard {

    /* Constructor */

    public MerchantCard() {
        super("Marchand", CardFamily.TRADE, 6);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        if(this.getPlayer() != null) {
            this.getPlayer().playAsMerchant();
        }
    }

    public void usePower() {
        this.getPlayer().addGold(1);
        this.getPlayer().getDisplay().addMerchantPower(this);
    }

}
