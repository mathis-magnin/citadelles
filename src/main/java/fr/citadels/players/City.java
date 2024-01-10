package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;

import java.util.ArrayList;

public class City extends ArrayList<DistrictCard> {

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
