package fr.citadels.cards.districts;

import fr.citadels.cards.Family;

import java.util.ArrayList;
import java.util.List;

public class Hand extends ArrayList<District> {

    /* Constructor */

    public Hand(List<District> cards) {
        this.addAll(cards);
    }


    /* Basic method */

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (!this.isEmpty()) {
            for (District districtCard : this) {
                str.append(districtCard.toString()).append(", ");
            }
            str.delete(str.length() - 2, str.length());
        }
        return str.toString();
    }


    /* Methods */

    /**
     * Remove a card from the hand.
     *
     * @param index the index of the card to remove.
     * @return the card removed.
     */
    public District removeCard(int index) {
        return super.remove(index);
    }


    /**
     * Sort the cards by preferred family (first) and by cost (descending).
     *
     * @param preferredFamily the family of cards to put first.
     */
    public void sortCards(Family preferredFamily) {
        this.sort((o1, o2) -> {
            if (o1.getFamily().equals(preferredFamily) && !o2.getFamily().equals(preferredFamily)) {
                return -1;
            } else if (!o1.getFamily().equals(preferredFamily) && o2.getFamily().equals(preferredFamily)) {
                return 1;
            } else {
                return Integer.compare(o2.getGoldCost(), o1.getGoldCost());
            }
        });
    }

}