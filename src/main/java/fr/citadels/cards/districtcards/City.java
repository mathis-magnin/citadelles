package fr.citadels.cards.districtcards;

import fr.citadels.cards.districtcards.uniques.MiracleCourtyard;

import java.util.ArrayList;
import java.util.List;

public class City extends ArrayList<DistrictCard> {

    public static final int SIZE_TO_WIN = 7;

    /* Constructors */

    public City() {
        super();
    }


    public City(List<DistrictCard> cards) {
        this.addAll(cards);

    }


    /* Basic method */

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


    /* Methods */

    /**
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean isComplete() {
        return this.size() >= SIZE_TO_WIN;
    }


    /**
     * Check if the player has one district of each family in his city.
     * The MiracleCourtyard district count as the missing family.
     *
     * @return A boolean value.
     */
    public boolean hasOneDistrictOfEachFamily() {
        int hasNoble = 0;
        int hasReligious = 0;
        int hasTrade = 0;
        int hasMilitary = 0;
        int hasSpecial = 0;
        int activateMiracleCourtyardEffect = 0;

        for (DistrictCard card : this) {
            switch (card.getCardFamily()) {
                case NOBLE:
                    hasNoble = 1;
                    break;
                case RELIGIOUS:
                    hasReligious = 1;
                    break;
                case TRADE:
                    hasTrade = 1;
                    break;
                case MILITARY:
                    hasMilitary = 1;
                    break;
                case UNIQUE:
                    if (card.equals(new MiracleCourtyard())) {
                        activateMiracleCourtyardEffect = 1;
                    }
                    else {
                        hasSpecial = 1;
                    }
                    break;
                case NEUTRAL:
                    break;
            }

        }
        return (5 <= hasNoble + hasReligious + hasTrade + hasMilitary + hasSpecial + activateMiracleCourtyardEffect);
    }


    /**
     * this method gives the first card in the hand that is not already in the city
     *
     * @param hand the cards to check
     * @return the index of the first card in the hand that is not already in the city
     */
    public int getFirstNotDuplicateIndex(List<DistrictCard> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (!this.contains(hand.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
