package fr.citadels.players;

import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;

public class Memory {

    /* Attributes */

    private final DistrictsPile pile;
    private final Display display;

    private boolean draw;
    private District districtToBuild;
    private Choices.Moment momentWhenUse;
    private Power powerToUse;
    private Character target;
    private int cardsToDiscard;
    private District districtToDestroy;
    private CharactersList remainingCharacters;


    /* Constructor */

    public Memory(Game game) {
        this.pile = game.getPile();
        this.display = game.getDisplay();
        this.draw = false;
        this.districtToBuild = null;
        this.momentWhenUse = Choices.Moment.BEFORE_RESSOURCES;
        this.powerToUse = Power.KILL;
        this.target = null;
        this.cardsToDiscard = 0;
        this.districtToDestroy = null;
        this.remainingCharacters = null;
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


    public boolean getDraw() {
        return this.draw;
    }


    /**
     * @return the district to build
     */
    public District getDistrictToBuild() {
        return this.districtToBuild;
    }


    public Choices.Moment getMomentWhenUse() {
        return this.momentWhenUse;
    }


    public Power getPowerToUse() {
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


    public CharactersList getRemainingCharacters() {
        return this.remainingCharacters;
    }


    /* Setters */

    public void setDraw(boolean draw) {
        this.draw = draw;
    }


    /**
     * @param district the district the player want to build
     */
    public void setDistrictToBuild(District district) {
        this.districtToBuild = district;
    }


    public void setMomentWhenUse(Choices.Moment moment) {
        this.momentWhenUse = moment;
    }


    /**
     * @param power The power the player wants to use.
     */
    public void setPowerToUse(Power power) {
        this.powerToUse = power;
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


    public void setRemainingCharacters(CharactersList characters) {
        this.remainingCharacters = characters;
    }

}