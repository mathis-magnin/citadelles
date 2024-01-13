package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;

import java.util.ArrayList;
import java.util.List;

public class City extends ArrayList<DistrictCard> {

    /* Constructor */

    public City(){
        super();
    }

    public City(List<DistrictCard> cards){
        this.addAll(cards);
    }

    /* Methods */

    /**
     * Check if the player has a complete city
     *
     * @return A boolean value.
     */
    public boolean isComplete() {
        return this.size() >= 7;
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
            }
        }

        return hasNoble && hasReligious && hasTrade && hasMilitary && hasSpecial;
    }

}
