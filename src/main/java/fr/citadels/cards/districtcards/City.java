package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import fr.citadels.cards.districtcards.uniques.Keep;
import fr.citadels.cards.districtcards.uniques.MiracleCourtyard;

import java.util.ArrayList;
import java.util.List;

public class City extends ArrayList<District> {

    /* Static content */

    public static final int NUMBER_DISTRICTS_TO_WIN = 7;


    /* Constructors */

    public City() {
        super();
    }


    public City(List<District> cards) {
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
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean isComplete() {
        return (City.NUMBER_DISTRICTS_TO_WIN <= this.size());
    }


    /**
     * Calcul the number of district with the same family as that given in parameter.
     *
     * @param family the family to compare to.
     * @return the number of district with the same family to that given in parameter.
     */
    public int getNumberOfDistrictWithFamily(Family family) {
        int number = 0;
        for (District district : this) {
            number = (district.getFamily().equals(family)) ? number + 1 : number;
        }
        return number;
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

        for (District district : this) {
            switch (district.getFamily()) {
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
                    if (district.equals(new MiracleCourtyard())) {
                        activateMiracleCourtyardEffect = 1;
                    } else {
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
     * this method gives the first card in the hand that is not already in the city.
     *
     * @param hand the cards to check.
     * @return the index of the first card in the hand that is not already in the city.
     */
    public int getFirstNotDuplicateIndex(List<District> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (!this.contains(hand.get(i))) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Find the cheapest district of the city.
     *
     * @return The cheapest district of the city.
     */
    public District getCheapestDistrict() {
        District districtToDestroy = null;
        for (District district : this) {
            if (!district.equals(new Keep()) && ((districtToDestroy == null) || (district.getGoldCost() < districtToDestroy.getGoldCost()))) {
                districtToDestroy = district;
            }
        }
        return districtToDestroy;
    }

}
