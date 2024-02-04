package fr.citadels.cards.districtcards;

import fr.citadels.cards.CardFamily;

import java.util.ArrayList;
import java.util.List;

public class Hand extends ArrayList<DistrictCard> {

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (!this.isEmpty()) {
            for (DistrictCard districtCard : this) {
                str.append(districtCard.toString()).append(", ");
            }
            str.delete(str.length() - 2, str.length());
        }
        return str.toString();
    }

    /* Constructor */

    public Hand(List<DistrictCard> cards) {
        this.addAll(cards);
    }

    /* Methods */

    /**
     * Remove a card from the hand
     *
     * @param index the index of the card to remove
     * @return the card removed
     */
    public DistrictCard removeCard(int index) {
        return super.remove(index);
    }


    /**
     * sort the cards by preferred family (first) and by cost (descending)
     *
     * @param preferredFamily the family of cards to put first
     */
    public void sortCards(CardFamily preferredFamily) {
        this.sort((o1, o2) -> {
            if (o1.getCardFamily().equals(preferredFamily) && !o2.getCardFamily().equals(preferredFamily)) {
                return -1;
            } else if (!o1.getCardFamily().equals(preferredFamily) && o2.getCardFamily().equals(preferredFamily)) {
                return 1;
            } else {
                return Integer.compare(o2.getGoldCost(), o1.getGoldCost());
            }
        });
    }


}
