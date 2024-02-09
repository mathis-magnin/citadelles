package fr.citadels.players;

import fr.citadels.cards.characters.CharactersDeck;
import fr.citadels.cards.characters.Power;
import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;

import java.util.ArrayList;
import java.util.List;

public class Memory {

    /* Attributes */

    private final DistrictsPile pile;
    private final CharactersDeck charactersDeck;
    private final Display display;
    private Player[] players;
    private int playerIndex;
    private boolean draw;
    private District districtToBuild;
    private Choices.Moment momentWhenUse;
    private Power powerToUse;
    private Character target;
    private int numberCardsToDiscard;
    private District districtToDestroy;
    private Character[] faceUpCharacters;
    private List<Character> possibleCharacters;
    private Player previousArchitect;
    private int turnNumber;


    /* Constructor */

    public Memory(Game game) {
        this.pile = game.getPile();
        this.charactersDeck = game.getCharactersDeck();
        this.display = game.getDisplay();
        this.players = game.getPlayers();
        this.playerIndex = 0;
        this.draw = false;
        this.districtToBuild = null;
        this.momentWhenUse = Choices.Moment.BEFORE_RESOURCES;
        this.powerToUse = Power.KILL;
        this.target = null;
        this.numberCardsToDiscard = 0;
        this.districtToDestroy = null;
        this.faceUpCharacters = null;
        this.possibleCharacters = null;
        this.previousArchitect = null;
        this.turnNumber = game.getTurnNumber();
    }


    /* Getters */

    /**
     * @return the pile
     */
    public DistrictsPile getPile() {
        return pile;
    }


    public CharactersDeck getCharactersDeck() {
        return charactersDeck;
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


    public Player[] getPlayers() {
        return this.players;
    }


    public int getPlayerIndex() {
        return this.playerIndex;
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
    public int getNumberCardsToDiscard() {
        return this.numberCardsToDiscard;
    }


    /**
     * @return the district to destroy when the warlord's power is used
     */
    public District getDistrictToDestroy() {
        return this.districtToDestroy;
    }


    public Character[] getFaceUpCharacters() {
        return faceUpCharacters;
    }


    public List<Character> getPossibleCharacters() {
        return this.possibleCharacters;
    }


    public Player getPreviousArchitect() {
        return this.previousArchitect;
    }

    public Integer getTurnNumber() {
        return this.turnNumber;
    }

    /* Setters */

    public void setPlayers(Player[] players) {
        this.players = players;
    }


    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }


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
    public void setNumberCardsToDiscard(int number) {
        this.numberCardsToDiscard = number;
    }


    /**
     * @param districtToDestroy the district card to destroy when the warlord's power is used
     */
    public void setDistrictToDestroy(District districtToDestroy) {
        this.districtToDestroy = districtToDestroy;
    }


    public void setFaceUpCharacters(Character[] characters) {
        this.faceUpCharacters = characters;
    }


    public void setPreviousArchitect(Player previousArchitect) {
        this.previousArchitect = previousArchitect;
    }


    public void setPossibleCharacters(List<Character> characters) {
        this.possibleCharacters = new ArrayList<>();
        this.possibleCharacters.addAll(characters);
    }


    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

}