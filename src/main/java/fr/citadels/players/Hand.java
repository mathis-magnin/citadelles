package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;

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

    public Hand(List<DistrictCard> cards){
        this.addAll(cards);
    }

}
