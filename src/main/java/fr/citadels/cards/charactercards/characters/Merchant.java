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
     * 1. The player gain one gold coin.
     * 2. The player gain one gold coin per Trade district he has in his city.
     *
     * @precondition The player must have chosen which power he wants to use.
     */
    public void usePower() {
        switch (this.getPlayer().getMemory().getPowerToUse()) {
            case 1:
                this.gainGoldsWithCity();
                break;
            case 2:
                this.gainOneGold();
                break;
            default:
                break;
        }
    }


    /**
     * 1. The player gain one gold coin.
     */
    private void gainOneGold() {
        this.getPlayer().getActions().addGold(1);
        this.getPlayer().getMemory().getDisplay().addMerchantPower(this);
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }


    /**
     * 2. The player gain one gold coin per Noble district he has in his city.
     * The SchoolOfMagic district act as a Noble district.
     */
    private void gainGoldsWithCity() {
        int gold = this.getPlayer().getCity().getNumberOfDistrictWithFamily(this.getFamily());

        boolean activateSchoolOfMagicEffect = (DistrictsPile.allDistrictCards[63].getOwner() == this.getPlayer()); // School of magic effect
        gold = activateSchoolOfMagicEffect ? gold + 1 : gold;

        if (0 < gold) {
            this.getPlayer().getActions().addGold(gold);
            this.getPlayer().getMemory().getDisplay().addGoldTakenFromCity(this.getPlayer(), gold, activateSchoolOfMagicEffect);
            this.getPlayer().getMemory().getDisplay().addBlankLine();
        }
    }

}