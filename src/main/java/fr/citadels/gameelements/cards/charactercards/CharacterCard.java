package fr.citadels.gameelements.cards.charactercards;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.Card;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;

public abstract class CharacterCard extends Card implements Comparable<CharacterCard> {

    /* Attribute */

    private final int rank;

    private Player player;

    /* Constructor */

    protected CharacterCard(String cardName, CardFamily cardFamily, int rank) {
        super(cardName, cardFamily);
        this.rank = rank;
        this.player = null;
    }

    /* Methods */

    public int getRank() {
        return this.rank;
    }

    public Player getPlayer() {
        return this.player;
    }

    public abstract void bringIntoPlay(DistrictCardsPile pile, Bank bank, Display display);

    public abstract void usePower();

    /**
     * @return a string representation of a district card
     */
    @Override
    public String toString() {
        return this.getCardName()+ " (" + this.getRank() + " - " + this.getCardFamily() + ")";
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