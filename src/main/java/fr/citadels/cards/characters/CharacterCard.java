package fr.citadels.cards.characters;

import fr.citadels.cards.Card;

public class CharacterCard extends Card {

    /* Attribute */

    private final int rank;

    /* Constructor */

    public CharacterCard(String cardName, int rank) {
        super(cardName);
        this.rank = rank;
    }

    /* Methods */

    public int getRank() {
        return this.rank;
    }

    /**
     * @return a string representation of a district card
     */
    @Override
    public String toString() {
        return "Carte personnage " + this.getCardName();
    }

}
