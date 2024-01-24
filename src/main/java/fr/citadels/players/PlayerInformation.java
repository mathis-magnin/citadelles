package fr.citadels.players;

import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;

public class PlayerInformation {

    /* Attributes */
    private final DistrictCardsPile pile;
    private final Bank bank;
    private final Display display;

    private DistrictCard districtToBuild;
    private int powerToUse;
    private CharacterCard target;

    public PlayerInformation(Game game) {
        this.pile = game.getPile();
        this.bank = game.getBank();
        this.display = game.getDisplay();
        this.districtToBuild = null;
        this.powerToUse = -1;
        this.target = null;
    }


    /* Getters */

    /**
     * @return the pile
     */
    public DistrictCardsPile getPile() {
        return pile;
    }


    /**
     * @return the bank
     */
    public Bank getBank() {
        return bank;
    }


    /**
     * @return the display
     */
    public Display getDisplay() {
        return display;
    }


    /**
     * @return the district to build
     */
    public DistrictCard getDistrictToBuild() {
        return this.districtToBuild;
    }


    public int getPowerToUse() {
        return this.powerToUse;
    }


    /**
     * @return the target
     */
    public CharacterCard getTarget() {
        return target;
    }


    /* Setters */

    /**
     * @param district the district the player want to build.
     */
    public void setDistrictToBuild(DistrictCard district) {
        this.districtToBuild = district;
    }


    public void setPowerToUse(int number) {
        this.powerToUse = number;
    }


    /**
     * @param target the target to set
     */
    public void setTarget(CharacterCard target) {
        this.target = target;
    }

}