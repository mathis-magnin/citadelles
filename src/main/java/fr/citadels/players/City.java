package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;

import java.util.ArrayList;

public class City extends ArrayList<DistrictCard> {


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
        return this.size() >= 7;
    }

}
