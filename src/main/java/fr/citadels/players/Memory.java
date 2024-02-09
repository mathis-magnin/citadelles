package fr.citadels.players;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.engine.Display;
import fr.citadels.engine.Game;

public class Memory {

    /* Attributes */

    private final Display display;
    private int turnNumber;
    private final DistrictsPile pile;

    private boolean draw;
    private District districtToBuild;
    private Choices.Moment momentWhenUse;
    private Power powerToUse;
    private Character target;
    private int numberCardsToDiscard;
    private District districtToDestroy;

    private CharactersList faceUpCharacters;
    private CharactersList possibleCharacters;
    private Player[] players;
    private int playerIndex;
    private Player previousArchitect;



    /* Constructor */

    public Memory(Game game) {
        this.display = game.getDisplay();
        this.turnNumber = game.getTurnNumber();
        this.pile = game.getPile();

        this.draw = false;
        this.districtToBuild = null;
        this.momentWhenUse = Choices.Moment.BEFORE_RESOURCES;
        this.powerToUse = Power.KILL;
        this.target = null;
        this.numberCardsToDiscard = 0;
        this.districtToDestroy = null;

        this.faceUpCharacters = null;
        this.possibleCharacters = new CharactersList();
        this.players = game.getPlayers();
        this.playerIndex = 0;
        this.previousArchitect = null;
    }


    /* Basic methods */

    public Display getDisplay() {
        return display;
    }


    public Integer getTurnNumber() {
        return this.turnNumber;
    }


    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }


    public DistrictsPile getPile() {
        return pile;
    }


    public boolean getDraw() {
        return this.draw;
    }


    public void setDraw(boolean draw) {
        this.draw = draw;
    }


    public District getDistrictToBuild() {
        return this.districtToBuild;
    }


    public void setDistrictToBuild(District district) {
        this.districtToBuild = district;
    }


    public Choices.Moment getMomentWhenUse() {
        return this.momentWhenUse;
    }


    public void setMomentWhenUse(Choices.Moment moment) {
        this.momentWhenUse = moment;
    }


    public Power getPowerToUse() {
        return this.powerToUse;
    }


    public void setPowerToUse(Power power) {
        this.powerToUse = power;
    }


    public Character getTarget() {
        return target;
    }


    public void setTarget(Character target) {
        this.target = target;
    }


    public int getNumberCardsToDiscard() {
        return this.numberCardsToDiscard;
    }


    public void setNumberCardsToDiscard(int number) {
        this.numberCardsToDiscard = number;
    }


    public District getDistrictToDestroy() {
        return this.districtToDestroy;
    }


    public void setDistrictToDestroy(District districtToDestroy) {
        this.districtToDestroy = districtToDestroy;
    }


    public CharactersList getFaceUpCharacters() {
        return faceUpCharacters;
    }


    public void setFaceUpCharacters(CharactersList characters) {
        this.faceUpCharacters = characters;
    }


    public CharactersList getPossibleCharacters() {
        return this.possibleCharacters;
    }


    public void setPossibleCharacters(CharactersList characters) {
        this.possibleCharacters = new CharactersList();
        this.possibleCharacters.addAll(characters);
    }


    public Player[] getPlayers() {
        return this.players;
    }


    public void setPlayers(Player[] players) {
        this.players = players;
    }


    public int getPlayerIndex() {
        return this.playerIndex;
    }


    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }


    public Player getPreviousArchitect() {
        return this.previousArchitect;
    }


    public void setPreviousArchitect(Player previousArchitect) {
        this.previousArchitect = previousArchitect;
    }

}