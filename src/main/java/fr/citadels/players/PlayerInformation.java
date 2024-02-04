package fr.citadels.players;

import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;

public class PlayerInformation {

    /* Attributes */
    private final DistrictCardsPile pile;
    private final Display display;

    private DistrictCard districtToBuild;
    private int powerToUse;
    private CharacterCard target;
    private int cardsToDiscard;
    private DistrictCard districtToDestroy;
    private boolean recoverDistrictDecision;

    public PlayerInformation(Game game) {
        this.pile = game.getPile();
        this.display = game.getDisplay();
        this.districtToBuild = null;
        this.powerToUse = -1;
        this.target = null;
        this.cardsToDiscard = 0;
        this.districtToDestroy = null;
        this.recoverDistrictDecision = false;
    }


    /* Getters */

    /**
     * @return the pile
     */
    public DistrictCardsPile getPile() {
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


    /**
     * @return the number of cards to discard when the magician's power is used
     */
    public int getCardsToDiscard() {
        return this.cardsToDiscard;
    }


    /**
     * @return the district to destroy when the warlord's power is used
     */
    public DistrictCard getDistrictToDestroy() {
        return this.districtToDestroy;
    }


    public boolean getRecoverDistrictDecision() {
        return this.recoverDistrictDecision;
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


    /**
     * @param number the number of cards to discard when the magician's power is used
     */
    public void setCardsToDiscard(int number) {
        this.cardsToDiscard = number;
    }

    /**
     * @param districtToDestroy the district card to destroy when the warlord's power is used
     */
    public void setDistrictToDestroy(DistrictCard districtToDestroy) {
        this.districtToDestroy = districtToDestroy;
    }


    public void setRecoverDistrictDecision(boolean b) {
        this.recoverDistrictDecision = b;
    }

}