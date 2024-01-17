package fr.citadels.players;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import fr.citadels.gameelements.cards.districtcards.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Comparable<Player> {

    /* Attributes */

    private final String name;
    private Hand hand;
    private City city;
    private int gold;

    private CharacterCard character;


    /* Constructor */

    public Player(String name, List<DistrictCard> cards) {
        this.name = name;
        this.hand = new Hand(cards);
        this.city = new City();
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
     * Get a copy of the player's hand
     *
     * @return the hand
     */
    public Hand getHand() {
        return new Hand(new ArrayList<>(this.hand));
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * Sort the player's hand
     * @param family the family of the cards to put first
     */
    public void sortHand(CardFamily family) {
        this.hand.sortCards(family);
    }

    /**
     * Remove a card from the player's hand
     * @param index the index of the card to remove
     * @return the card removed
     */
    public DistrictCard removeCardFromHand(int index) {
        return this.hand.removeCard(index);
    }


    /**
     * Get a copy of the player's city
     *
     * @return the city
     */
    public City getCity() {
        return new City(new ArrayList<>(this.city));
    }

    /**
     * Add a card to the player's city
     */
    public void addCardToCity(DistrictCard card) {
        this.city.add(card);
    }

    /**
     * Add cards to the player's city
     */
    public void addCardsToCity(List<DistrictCard> cards) {
        this.city.addAll(cards);
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
     * Get a copy of the player's character
     * @return the character
     */
    public CharacterCard getCharacter() {
        if (this.character == null) return null; // if the player has no character (first round
        return this.character; //new CharacterCard(this.character.getCardName(), this.character.getCardFamily(), this.character.getRank());
    }

    public void setCharacter(CharacterCard character) {
        this.character = character;
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
        return this.name + "\n\tPersonnage : " +  this.character + "\n\tFortune : " + this.gold + "\n\tMain : " + this.hand + "\n\tCité : " + this.city;
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
     * check if the player has the card in his city
     *
     * @param card the card to check
     * @return true if the player has the card in his city
     */
    public boolean hasCardInCity(DistrictCard card) {
        return city.contains(card);
    }


    /**
     * add amount to the gold of the player
     *
     * @param amount that represents the amount to add
     */
    public void addGold(int amount, Bank bank) {
        if (bank.getGold() >= amount)
            gold += bank.take(amount);
        else throw new IllegalArgumentException("Not enough money in bank");
    }


    /**
     * decrease the gold of "amount"
     *
     * @param amount the amount to remove from the player's wallet
     * @throws IllegalArgumentException if the amount exceeds the money owned
     * @precondition amount must be less or equal to the gold amount of the player
     */
    public void pay(int amount, Bank bank) throws IllegalArgumentException {
        if (amount > gold || amount < 0)
            throw new IllegalArgumentException("Not enough money\n" + "expected : " + amount + "\nactual : " + gold);
        gold -= amount;
        bank.give(amount);
    }

    /**
     * takes 2 cards or 2 golds from the bank and add them to the player
     *
     * @param pile pile of cards
     * @param draw true if the player has to draw cards
     */
    public void takeCardsOrGold(DistrictCardsPile pile, Bank bank, boolean draw, Display display) {
        if (!draw) {
            try {
                addGold(2, bank);

                display.addGoldTaken(this,2);
                display.addBlankLine();
            } catch (IllegalArgumentException e) {
                draw = true;
            }
        }
        if (draw) {
            DistrictCard[] drawnCards = pile.draw(2);
            display.addDistrictDrawn(drawnCards);
            if (drawnCards.length != 0) { // if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(pile, drawnCards);
                hand.add(cardToPlay);

                display.addDistrictChosen(this, cardToPlay);
                display.addBlankLine();
            }
        }
    }

    /**
     * take gold from the city if the family of the card is the same as the family of the character
     */
    public void takeGoldFromCity(Bank bank, Display display){
        if(character != null) {
            int goldToTake = 0;
            for(DistrictCard card : getCity()) {
                if(card.getCardFamily().equals(character.getCardFamily())) {
                    goldToTake++;
                }
            }
            if (goldToTake > 0) {
                addGold(goldToTake, bank);
                display.addGoldTakenFromCity(this, goldToTake);
                display.addBlankLine();
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
    public abstract void play(DistrictCardsPile pile, Bank bank, Display display);


    /**
     * Choose and take a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public abstract void chooseCharacter(CharacterCardsList characters, Display display);

}