package fr.citadels.gameelements.cards.districtcards;

import java.util.ArrayList;
import java.util.List;

public class City extends ArrayList<DistrictCard> {

    public static final int SIZE_TO_WIN = 7;

    /* Constructors */

    public City() {
        super();
    }


    public City(List<DistrictCard> cards){
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
     * @return A boolean value.
     */
    public boolean hasOneDistrictOfEachFamily() {
        boolean hasNoble = false;
        boolean hasReligious = false;
        boolean hasTrade = false;
        boolean hasMilitary = false;
        boolean hasSpecial = false;

        for (DistrictCard card : this) {
            switch (card.getCardFamily()) {
                case NOBLE:
                    hasNoble = true;
                    break;
                case RELIGIOUS:
                    hasReligious = true;
                    break;
                case TRADE:
                    hasTrade = true;
                    break;
                case MILITARY:
                    hasMilitary = true;
                    break;
                case UNIQUE:
                    hasSpecial = true;
                    break;
                case NEUTRAL:
                    break;
            }
        }

        return hasNoble && hasReligious && hasTrade && hasMilitary && hasSpecial;
    }

}
