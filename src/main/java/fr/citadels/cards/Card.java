package fr.citadels.cards;

import java.util.Objects;

public abstract class Card {

    /* Attribute */

    private final String cardName;

    private final CardFamily cardFamily;

    /* Constructor */

    protected Card(String cardName, CardFamily cardFamily) {
        this.cardName = cardName;
        this.cardFamily = cardFamily;
    }

    /* Methods */

    public String getCardName() {
        return this.cardName;
    }

    public CardFamily getCardFamily() {
        return this.cardFamily;
    }

    /**
     * judge if 2 card objects are equals
     *
     * @return true if the 2 cards are equals and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return this.getCardName().equals(card.getCardName());
    }

    /**
     * generates a hash code for a district card
     *
     * @return a hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.cardName);
    }
}