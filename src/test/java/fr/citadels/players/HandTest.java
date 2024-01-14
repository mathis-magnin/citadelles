package fr.citadels.players;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.cards.districtcards.Hand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand = new Hand(List.of(DistrictCardsPile.allDistrictCards[55], DistrictCardsPile.allDistrictCards[65], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[16], DistrictCardsPile.allDistrictCards[9]));


    @Test
    void sortCardsNOBLE() {
        hand.sortCards(CardFamily.NOBLE);
        assertEquals("Château", hand.get(0).getCardName());
        assertEquals("Manoir", hand.get(1).getCardName());
        assertEquals("Université", hand.get(2).getCardName());
        assertEquals("Forteresse", hand.get(3).getCardName());
        assertEquals("Église", hand.get(4).getCardName());
    }

    @Test
    void sortCardsRELIGIOUS() {
        hand.sortCards(CardFamily.RELIGIOUS);
        assertEquals("Église", hand.get(0).getCardName());
        assertEquals("Université", hand.get(1).getCardName());
        assertEquals("Forteresse", hand.get(2).getCardName());
        assertEquals("Château", hand.get(3).getCardName());
        assertEquals("Manoir", hand.get(4).getCardName());
    }

    @Test
    void sortCardsTRADE() {
        hand.sortCards(CardFamily.TRADE);
        assertEquals("Université", hand.get(0).getCardName());
        assertEquals("Forteresse", hand.get(1).getCardName());
        assertEquals("Château", hand.get(2).getCardName());
        assertEquals("Manoir", hand.get(3).getCardName());
        assertEquals("Église", hand.get(4).getCardName());
    }

    @Test
    void sortCardsMILITARY() {
        hand.sortCards(CardFamily.MILITARY);
        assertEquals("Forteresse", hand.get(0).getCardName());
        assertEquals("Université", hand.get(1).getCardName());
        assertEquals("Château", hand.get(2).getCardName());
        assertEquals("Manoir", hand.get(3).getCardName());
        assertEquals("Église", hand.get(4).getCardName());
    }
}