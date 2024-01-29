package fr.citadels.cards.districtcards;

import fr.citadels.cards.CardFamily;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandTest {
    Hand hand = new Hand(List.of(DistrictCardsPile.allDistrictCards[55], DistrictCardsPile.allDistrictCards[65], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[16], DistrictCardsPile.allDistrictCards[9]));


    @Test
    void sortCardsNOBLE() {
        hand.sortCards(CardFamily.NOBLE);
        Assertions.assertEquals("Château", hand.get(0).getCardName());
        Assertions.assertEquals("Manoir", hand.get(1).getCardName());
        Assertions.assertEquals("Université", hand.get(2).getCardName());
        Assertions.assertEquals("Forteresse", hand.get(3).getCardName());
        Assertions.assertEquals("Église", hand.get(4).getCardName());
    }

    @Test
    void sortCardsRELIGIOUS() {
        hand.sortCards(CardFamily.RELIGIOUS);
        Assertions.assertEquals("Église", hand.get(0).getCardName());
        Assertions.assertEquals("Université", hand.get(1).getCardName());
        Assertions.assertEquals("Forteresse", hand.get(2).getCardName());
        Assertions.assertEquals("Château", hand.get(3).getCardName());
        Assertions.assertEquals("Manoir", hand.get(4).getCardName());
    }

    @Test
    void sortCardsTRADE() {
        hand.sortCards(CardFamily.TRADE);
        Assertions.assertEquals("Université", hand.get(0).getCardName());
        Assertions.assertEquals("Forteresse", hand.get(1).getCardName());
        Assertions.assertEquals("Château", hand.get(2).getCardName());
        Assertions.assertEquals("Manoir", hand.get(3).getCardName());
        Assertions.assertEquals("Église", hand.get(4).getCardName());
    }

    @Test
    void sortCardsMILITARY() {
        hand.sortCards(CardFamily.MILITARY);
        Assertions.assertEquals("Forteresse", hand.get(0).getCardName());
        Assertions.assertEquals("Université", hand.get(1).getCardName());
        Assertions.assertEquals("Château", hand.get(2).getCardName());
        Assertions.assertEquals("Manoir", hand.get(3).getCardName());
        Assertions.assertEquals("Église", hand.get(4).getCardName());
    }
}