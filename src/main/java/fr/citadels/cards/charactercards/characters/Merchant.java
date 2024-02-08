package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.districtcards.DistrictsPile;

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


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * The abilities of the Merchant are :
     * INCOME : The player gain one gold coin per Trade district he has in his city.
     * GOLD : The player gain one gold coin.
     *
     * @precondition The player must have chosen which power he wants to use.
     */
    public void usePower() {
        switch (this.getPlayer().getMemory().getPowerToUse()) {
            case INCOME:
                super.income();
                break;
            case GOLD:
                this.gold();
                break;
            default:
                break;
        }
    }


    /**
     * The player gain one gold coin.
     */
    private void gold() {
        this.getPlayer().getActions().addGold(1);
        this.getPlayer().getMemory().getDisplay().addMerchantPower(this);
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }

}