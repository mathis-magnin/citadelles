package fr.citadels.players;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.Hand;

import java.util.List;

public abstract class Player implements Comparable<Player>, CharacterStrategies {

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
     * choose a card to take among the cards drawn
     *
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public abstract DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards);


    /**
     * choose a card in hand
     *
     * @return the card chosen or null if no card can be chosen
     */
    public abstract void chooseDistrictToBuild();


    /**
     * Choose and take a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public abstract void chooseCharacter(CharacterCardsList characters);


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    public abstract void chooseTargetToKill();


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    public abstract void chooseTargetToRob();


    /**
     * When the player embodies the magician, choose which power he should use, when he should use it,
     * and consequently either the character to exchange his hand with from the list of possibles targets
     * or the cards from his hands he should and replace by cards from the pile
     */
    public abstract int chooseMagicianPower();


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    public abstract void chooseTargetToDestroy();


    /**
     * play the phase when the player takes resources for his turn
     */
    public abstract void playResourcesPhase();


    /**
     * play the phase when the player builds districts in his city
     */
    public abstract void playBuildingPhase();

}