package fr.citadels.players;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.cards.districtcards.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Comparable<Player> {

    /* Attributes */
    private CharacterCard target;
    private final String name;
    private Hand hand;
    private City city;
    private int gold;
    private CharacterCard character;

    protected final DistrictCardsPile pile;

    protected final Bank bank;

    protected final Display display;

    /* Constructor */

    protected Player(String name, List<DistrictCard> cards, DistrictCardsPile pile, Bank bank, Display display) {
        this.name = name;
        this.hand = new Hand(cards);
        this.city = new City();
        this.character = null;
        this.pile = pile;
        this.bank = bank;
        this.display = display;
        this.target = null;
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

    /***
     * Set the target of the player
     * @param target
     */
    public void setTarget(CharacterCard target) {
        this.target = target;
    }

    /**
     * Get the target of the player
     *
     * @return the target
     */
    public CharacterCard getTarget() {
        return target;
    }

    /**
     * @return true if the player has a target, false otherwise
     */
    public Display getDisplay() {
        return display;
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

    public void addGold(int amount) {
        this.bank.take(amount);
        this.gold += amount;
    }

    public void removeGold(int amount) {
        if (amount >= this.gold) {
            this.bank.give(this.gold);
            this.gold = 0;
        } else {
            this.bank.give(amount);
            this.gold -= amount;
        }
    }

    /**
     * Sort the player's hand
     *
     * @param family the family of the cards to put first
     */
    public void sortHand(CardFamily family) {
        this.hand.sortCards(family);
    }

    /**
     * Remove a card from the player's hand
     *
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
     * Get the player's character
     *
     * @return the character
     */
    public CharacterCard getCharacter() {
        return this.character;
    }


    /**
     * Set the player's character and the character's player.
     *
     * @param character
     */
    public void setCharacter(CharacterCard character) {
        this.character = character;
        character.setPlayer(this);
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
     * put back the cards drawn except the one played
     *
     * @param drawnCards  cards drawn
     * @param randomIndex index of the card played
     */
    public void putBack(DistrictCard[] drawnCards, int randomIndex) {
        for (int i = 0; i < drawnCards.length; i++) {
            if (i != randomIndex) {
                this.pile.placeBelowPile(drawnCards[i]);
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
     * takes 2 cards or 2 golds from the bank and add them to the player
     *
     * @param draw true if the player has to draw cards
     */
    public void takeCardsOrGold(boolean draw) {
        if (!draw) {
            if (!bank.isEmpty())
                addGold(2);
            else draw = true;

            display.addGoldTaken(this, 2);
            display.addBlankLine();
        }
        if (draw) {
            DistrictCard[] drawnCards = this.pile.draw(2);
            this.display.addDistrictDrawn(drawnCards);
            if (drawnCards.length != 0) { // if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(drawnCards);
                hand.add(cardToPlay);

                this.display.addDistrictChosen(this, cardToPlay);
                this.display.addBlankLine();
            }
        }
    }

    /**
     * take gold from the city if the family of the card is the same as the family of the character
     */

    public void takeGoldFromCity() {
        if (character != null) {
            int goldToTake = 0;
            for (DistrictCard card : getCity()) {
                if (card.getCardFamily().equals(character.getCardFamily())) {
                    goldToTake++;
                }
            }
            if (goldToTake > 0) {
                addGold(goldToTake);
                bank.take(goldToTake);
                this.display.addGoldTakenFromCity(this, goldToTake);
                this.display.addBlankLine();
            }
        }
    }


    public void placeCard(DistrictCard cardToPlace) {
        if (cardToPlace != null) {
            addCardToCity(cardToPlace);
            bank.give(cardToPlace.getGoldCost());
            removeGold(cardToPlace.getGoldCost());
            display.addDistrictBuilt(this, cardToPlace);
        } else {
            display.addNoDistrictBuilt();
        }
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
    public abstract DistrictCard chooseCardInHand();


    /**
     * Choose and take a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public abstract void chooseCharacter(CharacterCardsList characters);


    /**
     * play a round for the linked player
     */
    public abstract void play();


    /**
     * play a round for the linked player when he embodies the assassin
     */
    public abstract void playAsAssassin();


    /**
     * play a round for the linked player when he embodies the thief
     */
    public abstract void playAsThief();


    /**
     * play a round for the linked player when he embodies the magician
     */
    public abstract void playAsMagician();


    /**
     * play a round for the linked player when he embodies the king
     */
    public abstract void playAsKing();


    /**
     * play a round for the linked player when he embodies the bishop
     */
    public abstract void playAsBishop();


    /**
     * play a round for the linked player if he embodies the merchant
     */
    public abstract void playAsMerchant();


    /**
     * play a round for the linked player if he embodies the architect
     */
    public abstract void playAsArchitect();


    /**
     * play a round for the linked player if he embodies the warlord
     */
    public abstract void playAsWarlord();

}