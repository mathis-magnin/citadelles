package fr.citadels.cards;

import java.util.Objects;

public class Card
{
    public String cardName;

    public Card(String cardName) { this.cardName = cardName; }

    public String getCardName() { return this.cardName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || this.getClass() != o.getClass()) { return false; }
        Card card = (Card) o;
        return this.getCardName().equals(card.getCardName());
    }
}
