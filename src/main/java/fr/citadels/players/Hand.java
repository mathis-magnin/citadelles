package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;

import java.util.ArrayList;
import java.util.List;

public class Hand extends ArrayList<DistrictCard> {

    /* Constructor */

    public Hand(List<DistrictCard> cards){
        this.addAll(cards);
    }

}
