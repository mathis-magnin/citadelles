package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Comparable<Player> {

    /* Attributes */

    protected final String name;
    protected List<DistrictCard> cardsInHand;
    protected List<DistrictCard> cityCards;
    protected int gold;

    protected CharacterCard character;


    /* Constructor */

    protected Player(String name, List<DistrictCard> cards) {
        this.name = name;
        this.cardsInHand = new ArrayList<>();
        this.cardsInHand.addAll(cards); /* Avoid modification from outside */
        this.cityCards = new ArrayList<>();
        this.character = null;
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
     * Get a copy of the cards in hand
     *
     * @return the cards in hand
     */
    public List<DistrictCard> getCardsInHand() {
        return new ArrayList<>(this.cardsInHand);
    }


    /**
     * Get a copy of the cards face up (of the player's city)
     *
     * @return the cards face up
     */
    public List<DistrictCard> getCityCards() {
        return new ArrayList<>(this.cityCards);
    }


    /**
     * get the amount of gold of the player
     *
     * @return the amount specified
     */
    public int getGold() {
        return this.gold;
    }


    public CharacterCard getCharacter() {
        return this.character;
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


    /* Methods */

    /**
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean hasCompleteCity() {
        return cityCards.size() >= 7;
    }


    /**
     * put back the cards drawn except the one played
     *
     * @param drawnCards  cards drawn
     * @param pile        pile of cards
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


    /**
     * check if the player has the card in hand
     *
     * @param card the card to check
     * @return true if the player has the card in hand
     */
    public boolean hasCardInCity(DistrictCard card) {
        return cityCards.contains(card);
    }


    /**
     * add amount to the gold of the player
     *
     * @param amount that represents the amount to add
     */
    public void addGold(int amount) {
        if (Game.BANK.getGold() >= amount)
            gold += Game.BANK.take(amount);
        else throw new IllegalArgumentException("Not enough money in bank");
    }


    /**
     * decrease the gold of "amount"
     *
     * @param amount the amount to remove from the player's wallet
     * @throws IllegalArgumentException if the amount exceeds the money owned
     * @precondition amount must be less or equal to the gold amount of the player
     */
    public void pay(int amount) throws IllegalArgumentException {
        if (amount > gold || amount < 0)
            throw new IllegalArgumentException("Not enough money\n" + "expected : " + amount + "\nactual : " + gold);
        gold -= amount;
        Game.BANK.give(amount);
    }

    /**
     * takes 2 cards or 2 golds from the bank and add them to the player
     *
     * @param pile pile of cards
     * @param draw true if the player has to draw cards
     */
    public void takeCardsOrGold(DistrictCardsPile pile, boolean draw) {
        if (!draw) {
            try {
                addGold(2);
            } catch (IllegalArgumentException e) {
                draw = true;
            }
        }
        if (draw) {
            DistrictCard[] drawnCards = pile.draw(2);
            if (drawnCards.length != 0) {//if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(pile, drawnCards);
                cardsInHand.add(cardToPlay);
            }
        }
    }

    /**
     * choose a card to play among the cards drawn
     *
     * @param pile       pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public abstract DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards);


    /**
     * choose a card in hand
     *
     * @return the card chosen or null if no card can be chosen
     */
    public abstract DistrictCard chooseCardInHand();


    /**
     * play a round for the linked player
     *
     * @param pile of cards
     * @return the actions of the player
     */
    public abstract String play(DistrictCardsPile pile);


    /**
     * Choose and take a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public abstract void chooseCharacter(CharacterCardsList characters);

}