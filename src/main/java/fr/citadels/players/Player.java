package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import fr.citadels.engine.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    /* Static content */
    /*
     * Attributes
     */
    protected String name;
    protected List<DistrictCard> cardsInHand;
    protected List<DistrictCard> cityCards;
    protected int gold;

    /*
     * Constructor
     */
    protected Player(String name, List<DistrictCard> cards) {
        this.name = name;
        this.cardsInHand = new ArrayList<>();
        this.cityCards = new ArrayList<>();
        this.cardsInHand.addAll(cards); /*avoid modification from outside*/

    }

    /*
     * Methods
     */

    /***
     * get the name of the player
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /***
     * get the amount of gold of the player
     * @return the amount specified
     */
    public int getGold() {
        return gold;
    }

    /***
     * get a copy of the cards in hand
     * @return the cards in hand
     */
    public List<DistrictCard> getCardsInHand() {
        return new ArrayList<>(this.cardsInHand);
    }

    /***
     * get a copy of the cards face up (of the player's city)
     * @return the cards face up
     */
    public List<DistrictCard> getCityCards() {
        return new ArrayList<>(this.cityCards);
    }


    /**
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean hasCompleteCity() {
        return cityCards.size() >= 7;
    }


    /***
     * put back the cards drawn except the one played
     * @param drawnCards cards drawn
     * @param pile pile of cards
     * @param randomIndex index of the card played
     */
    public void putBack(DistrictCard[] drawnCards, DistrictCardsPile pile, int randomIndex) {
        for (int i = 0; i < drawnCards.length; i++) {
            if (i != randomIndex) {
                pile.placeBelowPile(drawnCards[i]);
                drawnCards[i] = null;
            }
        }
    }

    /***
     * check if the player has the card in hand
     * @param card the card to check
     * @return true if the player has the card in hand
     */
    public boolean hasCardInCity(DistrictCard card) {
        return cityCards.contains(card);
    }

    /***
     * add amount to the gold of the player
     * @param amount that represents the amount to add
     */
    public void addGold(int amount) {
        if (Game.BANK.getGold() >= amount)
            gold += Game.BANK.take(amount);
        else throw new IllegalArgumentException("Not enough money in bank");
    }

    /***
     * decrease the gold of "amount"
     * @param amount the amount to remove from the player's wallet
     * @precondition amount must be less or equal to the gold amount of the player
     * @throws IllegalArgumentException if the amount exceeds the money owned
     */
    public void pay(int amount) throws IllegalArgumentException {
        if (amount > gold || amount < 0)
            throw new IllegalArgumentException("Not enough money\n" + "expected : " + amount + "\nactual : " + gold);
        gold -= amount;
        Game.BANK.give(amount);
    }

    /***
     * choose a card to play among the cards drawn
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public abstract DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards);


    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public abstract DistrictCard chooseCardInHand();

    /***
     * play a round for the linked player
     * @param pile of cards
     * @return the actions of the player
     */
    public abstract String play(DistrictCardsPile pile);


}
