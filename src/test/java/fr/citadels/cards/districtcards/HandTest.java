package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandTest {
    Hand hand = new Hand(List.of(DistrictsPile.allDistrictCards[55], DistrictsPile.allDistrictCards[65], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[9]));


    @Test
    void sortCardsNOBLE() {
        hand.sortCards(Family.NOBLE);
        Assertions.assertEquals("Château", hand.get(0).getName());
        Assertions.assertEquals("Manoir", hand.get(1).getName());
        Assertions.assertEquals("Université", hand.get(2).getName());
        Assertions.assertEquals("Forteresse", hand.get(3).getName());
        Assertions.assertEquals("Église", hand.get(4).getName());
    }

    @Test
    void sortCardsRELIGIOUS() {
        hand.sortCards(Family.RELIGIOUS);
        Assertions.assertEquals("Église", hand.get(0).getName());
        Assertions.assertEquals("Université", hand.get(1).getName());
        Assertions.assertEquals("Forteresse", hand.get(2).getName());
        Assertions.assertEquals("Château", hand.get(3).getName());
        Assertions.assertEquals("Manoir", hand.get(4).getName());
    }

    @Test
    void sortCardsTRADE() {
        hand.sortCards(Family.TRADE);
        Assertions.assertEquals("Université", hand.get(0).getName());
        Assertions.assertEquals("Forteresse", hand.get(1).getName());
        Assertions.assertEquals("Château", hand.get(2).getName());
        Assertions.assertEquals("Manoir", hand.get(3).getName());
        Assertions.assertEquals("Église", hand.get(4).getName());
    }

    @Test
    void sortCardsMILITARY() {
        hand.sortCards(Family.MILITARY);
        Assertions.assertEquals("Forteresse", hand.get(0).getName());
        Assertions.assertEquals("Université", hand.get(1).getName());
        Assertions.assertEquals("Château", hand.get(2).getName());
        Assertions.assertEquals("Manoir", hand.get(3).getName());
        Assertions.assertEquals("Église", hand.get(4).getName());
    }
}