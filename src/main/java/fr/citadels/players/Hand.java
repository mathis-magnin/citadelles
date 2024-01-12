package fr.citadels.players;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districts.DistrictCard;

import java.util.ArrayList;
import java.util.List;

public class Hand extends ArrayList<DistrictCard> {

    /* Constructor */

    public Hand(List<DistrictCard> cards) {
        this.addAll(cards);
    }


    /**
     * sort the cards by NOBLE (first) and by cost (descending)
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
