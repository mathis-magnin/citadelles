package fr.citadels.players;

import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;

public class Memory {

    /* Attributes */

    private final DistrictsPile pile;
    private final Display display;

    private District districtToBuild;
    private int powerToUse;
    private Character target;
    private int cardsToDiscard;
    private District districtToDestroy;


    /* Constructor */

    public Memory(Game game) {
        this.pile = game.getPile();
        this.display = game.getDisplay();
        this.districtToBuild = null;
        this.powerToUse = -1;
        this.target = null;
        this.cardsToDiscard = 0;
        this.districtToDestroy = null;
    }


    /* Getters */

    /**
     * @return the pile
     */
    public DistrictsPile getPile() {
        return pile;
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
    public District getDistrictToBuild() {
        return this.districtToBuild;
    }


    public int getPowerToUse() {
        return this.powerToUse;
    }


    /**
     * @return the target
     */
    public Character getTarget() {
        return target;
    }


    /**
     * @return the number of cards to discard when the magician's power is used
     */
    public int getCardsToDiscard() {
        return this.cardsToDiscard;
    }


    /**
     * @return the district to destroy when the warlord's power is used
     */
    public District getDistrictToDestroy() {
        return this.districtToDestroy;
    }


    /* Setters */

    /**
     * @param district the district the player want to build
     */
    public void setDistrictToBuild(District district) {
        this.districtToBuild = district;
    }


    /**
     * @param number the number of the power the player wants to use
     */
    public void setPowerToUse(int number) {
        this.powerToUse = number;
    }


    /**
     * @param target the target to set
     */
    public void setTarget(Character target) {
        this.target = target;
    }


    /**
     * @param number the number of cards to discard when the magician's power is used
     */
    public void setCardsToDiscard(int number) {
        this.cardsToDiscard = number;
    }


    /**
     * @param districtToDestroy the district card to destroy when the warlord's power is used
     */
    public void setDistrictToDestroy(District districtToDestroy) {
        this.districtToDestroy = districtToDestroy;
    }

}