package fr.citadels.cards;

import java.util.Objects;

public abstract class Card {

    /* Attributes */

    private final String name;
    private final Family family;


    /* Constructor */

    protected Card(String name, Family family) {
        this.name = name;
        this.family = family;
    }


    /* Basic methods */

    public String getName() {
        return this.name;
    }


    public Family getFamily() {
        return this.family;
    }


    /**
     * Judge if 2 card objects are equals.
     *
     * @return true if the 2 cards are equals and false otherwise.
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
        return this.getName().equals(card.getName());
    }


    /**
     * Generates a hash code for a district card.
     *
     * @return a hash value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

}