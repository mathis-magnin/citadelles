package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.districtcards.DistrictsPile;

public class Bishop extends Character {

    /* Constructor */

    public Bishop() {
        super("Évêque", Family.RELIGIOUS, 5);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsBishop();
    }


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * INCOME : The player gain one gold coin per Religious district he has in his city.
     * The SchoolOfMagic district act as a Religious district.
     */
    @Override
    public void usePower() {
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