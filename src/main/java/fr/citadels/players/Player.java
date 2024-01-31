package fr.citadels.players;

import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.Hand;

import java.util.List;

public abstract class Player implements Comparable<Player>, PlayerChoices {

    /* Attributes */

    private final String name;
    private Hand hand;
    private City city;
    private int gold;
    private CharacterCard character;
    protected final PlayerInformation information;
    protected final PlayerActions actions;


    /* Constructor */

    protected Player(String name, List<DistrictCard> cards, Game game) {
        this.name = name;
        this.hand = new Hand(cards);
        this.city = new City();
        this.character = null;

        this.information = new PlayerInformation(game);
        actions = new PlayerActions(this, information);
    }


    /* Basic methods */

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
     *
     * @return the player's actions
     */
    public PlayerActions getActions() {
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
    public CharacterCard getCharacter() {
        return this.character;
    }


    /**
     * Get the player's information
     *
     * @return information
     */
    public PlayerInformation getInformation() {
        return information;
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
    public void setCharacter(CharacterCard character) {
        this.character = character;
        character.setPlayer(this);
    }


    /**
     * Set the player's gold
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
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean hasCompleteCity() {
        return city.isComplete();
    }


    /**
     * check if the player has the card in his city
     *
     * @param card the card to check
     * @return true if the player has the card in his city
     */
    public boolean hasCardInCity(DistrictCard card) {
        return city.contains(card);
    }


    /**
     * play a round for the linked player when he embodies the assassin
     */
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();
        chooseTargetToKill();
        getCharacter().usePower();
    }


    /**
     * play a round for the linked player when he embodies the thief
     */
    public void playAsThief() {
        playResourcesPhase();
        playBuildingPhase();
        chooseTargetToRob();
        getCharacter().usePower();
    }


    /**
     * play a round for the linked player when he embodies the magician
     */
    public void playAsMagician() {
        int momentToUsePower = chooseMagicianPower();
        if (momentToUsePower == 0) {
            this.getCharacter().usePower();
        }
        playResourcesPhase();
        if (momentToUsePower == 1) {
            this.getCharacter().usePower();
        }
        playBuildingPhase();
        if (momentToUsePower == 2) {
            this.getCharacter().usePower();
        }
    }


    /**
     * play a round for the linked player when he embodies the king
     */
    public void playAsKing() {
        playResourcesPhase();
        playBuildingPhase();
    }


    /**
     * play a round for the linked player when he embodies the bishop
     */
    public void playAsBishop() {
        playResourcesPhase();
        playBuildingPhase();
    }


    /**
     * play a round for the linked player if he embodies the merchant
     */
    public void playAsMerchant() {
        getCharacter().usePower();
        playResourcesPhase();
        playBuildingPhase();
    }


    /**
     * play a round for the linked player if he embodies the architect
     */
    public void playAsArchitect() {
        this.getInformation().setPowerToUse(1);  // draw two cards
        this.getCharacter().usePower();

        this.playResourcesPhase();
        this.playBuildingPhase();

        this.getInformation().setPowerToUse(2);  // build another district
        for (int i = 0; i < 2; i++) {
            this.chooseDistrictToBuild();
            if (this.getInformation().getDistrictToBuild() != null) {
                this.getCharacter().usePower();
            } else {
                this.getInformation().getDisplay().addNoArchitectPower();
                this.getInformation().getDisplay().addBlankLine();
            }
        }
    }


    /**
     * play a round for the linked player if he embodies the warlord
     */
    public void playAsWarlord() {
        playResourcesPhase();
        playBuildingPhase();
        chooseTargetToDestroy();
        if ((getInformation().getTarget() != null) && (getInformation().getDistrictToDestroy() != null)) {
            getCharacter().usePower();
        }
        else {
            getInformation().getDisplay().addNoWarlordPower();
        }
    }

}