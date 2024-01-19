package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

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

    public void usePower(){
        return;
    }

}
