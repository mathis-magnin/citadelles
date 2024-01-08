package fr.citadels.cards.characters;

import fr.citadels.cards.Card;
import fr.citadels.engine.Score;

public class CharacterCard extends Card implements Comparable<CharacterCard> {

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


    /**
     * CharacterCards comparison is based on their rank (natural ordering of integer).
     * Note : this class has a natural ordering that is inconsistent with equals.
     *
     * @param other the other characterCard to be compared.
     * @return a positive value if this rank is greater than other rank.
     * 0 if there is a tie.
     * a negative value if other rank is greater than this rank.
     */
    @Override
    public int compareTo(CharacterCard other) {
        return this.getRank() - other.getRank();
    }

}
