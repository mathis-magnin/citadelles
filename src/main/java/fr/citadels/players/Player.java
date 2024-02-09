package fr.citadels.players;

import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.Hand;

import java.util.List;

public abstract class Player implements Comparable<Player>, Choices {

    /* Attributes */

    private final String name;
    private Hand hand;
    private City city;
    private int gold;
    private Character character;
    protected Memory memory;
    protected Actions actions;


    /* Constructor */

    protected Player(String name, List<District> cards, Game game) {
        this.name = name;
        this.hand = new Hand(cards);
        this.city = new City();
        this.character = null;
        this.gold = 0;

        this.memory = new Memory(game);
        actions = new Actions(this, memory);
    }

    protected Player(String name) {
        this.name = name;
        this.hand = null;
        this.city = null;
        this.character = null;
        this.memory = null;
        actions = null;
    }


    /* Basic methods */
    public void initPlayer(List<District> cards, Game game) {
        this.hand = new Hand(cards);
        this.city = new City();
        this.character = null;
        this.gold = 0;
        this.memory = new Memory(game);
        this.actions = new Actions(this, memory);
    }

    /**
     * Get the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }


    /**
     * Get a copy of the player's hand
     *
     * @return the hand
     */
    public Hand getHand() {
        return this.hand;
    }


    /**
     * @return the player's actions
     */
    public Actions getActions() {
        return actions;
    }


    /**
     * Get a copy of the player's city
     *
     * @return the city
     */
    public City getCity() {
        return this.city;
    }


    /**
     * get the amount of gold of the player
     *
     * @return the amount specified
     */
    public int getGold() {
        return this.gold;
    }


    /**
     * Get the player's character
     *
     * @return the character
     */
    public Character getCharacter() {
        return this.character;
    }


    /**
     * Get the player's memory
     *
     * @return memory
     */
    public Memory getMemory() {
        return memory;
    }


    /**
     * Set the player's hand
     *
     * @param hand the hand to set
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }


    /**
     * Set the player's city
     *
     * @param city the hand to set
     */
    public void setCity(City city) {
        this.city = city;
    }


    /**
     * Set the player's character and the character's player.
     *
     * @param character the character to set
     */
    public void setCharacter(Character character) {
        this.character = character;
        character.setPlayer(this);
    }


    /**
     * Set the player's gold
     *
     * @param gold the amount of gold coins that the player should have
     */
    public void setGold(int gold) {
        this.gold = gold;
    }


    /**
     * Player comparison is based on their characterCard's rank (natural ordering of integer).
     * Note : this class has a natural ordering that is inconsistent with equals.
     *
     * @param other the other player to be compared.
     * @return a positive value if this rank is greater than other rank.
     * 0 if there is a tie.
     * a negative value if other rank is greater than this rank.
     */
    @Override
    public int compareTo(Player other) {
        return this.getCharacter().compareTo(other.getCharacter());
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Player otherPlayer)) return false;
        return this.name.equals(otherPlayer.name);
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }


    @Override
    public String toString() {
        return this.name + "\n\tPersonnage : " + this.character + "\n\tFortune : " + this.gold + "\n\tMain : " + this.hand + "\n\tCité : " + this.city;
    }


    /* Methods */


    /**
     * Check if the player has a complete city.
     *
     * @return A boolean value.
     */
    public boolean hasCompleteCity() {
        return city.isComplete();
    }


    /**
     * Check if the player has the card in his city.
     *
     * @param card the card to check
     * @return true if the player has the card in his city
     */
    public boolean hasCardInCity(District card) {
        return city.contains(card);
    }


    /**
     * Play the phase when the player takes resources for his turn.
     */
    public void playResourcesPhase() {
        this.chooseDraw();
        this.getActions().takeCardsOrGold();

        if (this.equals(DistrictsPile.allDistrictCards[60].getOwner())) { // Laboratory effect
            DistrictsPile.allDistrictCards[60].useEffect();
        }
    }


    /**
     * Play the phase when the player builds districts in his city.
     */
    public void playBuildingPhase() {
        this.chooseDistrictToBuild();
        this.getActions().build();

        if (this.equals(DistrictsPile.allDistrictCards[61].getOwner())) { // Factory effect
            DistrictsPile.allDistrictCards[61].useEffect();
        }
    }


    /**
     * Play a round for the linked player when he embodies the assassin.
     */
    public void playAsAssassin() {
        this.playResourcesPhase();

        this.playBuildingPhase();

        this.chooseTargetToKill();
        this.getCharacter().usePower();
    }


    /**
     * Play a round for the linked player when he embodies the thief.
     */
    public void playAsThief() {
        this.playResourcesPhase();

        this.playBuildingPhase();

        this.chooseTargetToRob();
        this.getCharacter().usePower();
    }


    /**
     * Play a round for the linked player when he embodies the magician.
     */
    public void playAsMagician() {
        this.chooseMagicianPower();

        if (this.memory.getMomentWhenUse().equals(Moment.BEFORE_RESOURCES)) {
            this.getCharacter().usePower();
        }

        this.playResourcesPhase();

        if (this.memory.getMomentWhenUse().equals(Moment.BETWEEN_PHASES)) {
            this.getCharacter().usePower();
        }

        this.playBuildingPhase();

        if (this.memory.getMomentWhenUse().equals(Moment.AFTER_BUILDING)) {
            this.getCharacter().usePower();
        }
    }


    /**
     * Play a round for the linked player when he embodies the king.
     */
    public void playAsKing() {
        this.playResourcesPhase();
        this.chooseMomentToTakeIncome();

        if (this.memory.getMomentWhenUse().equals(Moment.BETWEEN_PHASES)) {
            this.getCharacter().usePower();
        }

        this.playBuildingPhase();

        if (this.memory.getMomentWhenUse().equals(Moment.AFTER_BUILDING)) {
            this.getCharacter().usePower();
        }
    }


    /**
     * Play a round for the linked player when he embodies the bishop.
     */
    public void playAsBishop() {
        this.playResourcesPhase();
        this.chooseMomentToTakeIncome();

        if (this.memory.getMomentWhenUse().equals(Moment.BETWEEN_PHASES)) {
            this.getCharacter().usePower();
        }

        this.playBuildingPhase();

        if (this.memory.getMomentWhenUse().equals(Moment.AFTER_BUILDING)) {
            this.getCharacter().usePower();
        }
    }


    /**
     * Play a round for the linked player if he embodies the merchant.
     */
    public void playAsMerchant() {
        this.getMemory().setPowerToUse(Power.GOLD);
        this.getCharacter().usePower();

        this.playResourcesPhase();
        this.chooseMomentToTakeIncome();
        this.getMemory().setPowerToUse(Power.INCOME);

        if (this.memory.getMomentWhenUse().equals(Moment.BETWEEN_PHASES)) {
            this.getCharacter().usePower();
        }

        this.playBuildingPhase();

        if (this.memory.getMomentWhenUse().equals(Moment.AFTER_BUILDING)) {
            this.getCharacter().usePower();
        }
    }


    /**
     * Play a round for the linked player if he embodies the architect.
     */
    public void playAsArchitect() {
        this.getMemory().setPowerToUse(Power.DRAW);
        this.getCharacter().usePower();

        this.playResourcesPhase();
        this.playBuildingPhase();

        this.getMemory().setPowerToUse(Power.BUILD);
        for (int i = 0; i < 2; i++) {
            this.chooseDistrictToBuild();
            if (this.getMemory().getDistrictToBuild() != null) {
                this.getCharacter().usePower();
            } else {
                this.getMemory().getDisplay().addNoArchitectPower();
                this.getMemory().getDisplay().addBlankLine();
            }
        }
    }


    /**
     * Play a round for the linked player if he embodies the warlord.
     */
    public void playAsWarlord() {
        this.playResourcesPhase();
        this.chooseMomentToTakeIncome();
        this.getMemory().setPowerToUse(Power.INCOME);

        if (this.memory.getMomentWhenUse() == Moment.BETWEEN_PHASES) {
            this.getCharacter().usePower();
        }

        this.playBuildingPhase();

        if (this.memory.getMomentWhenUse() == Moment.AFTER_BUILDING) {
            this.getCharacter().usePower();
        }

        this.getMemory().setPowerToUse(Power.DESTROY);
        this.chooseTargetToDestroy();
        if ((getMemory().getTarget() != null) && (getMemory().getDistrictToDestroy() != null)) {
            getCharacter().usePower();
        } else {
            getMemory().getDisplay().addNoWarlordPower();
            this.getMemory().getDisplay().addBlankLine();
        }
    }


}