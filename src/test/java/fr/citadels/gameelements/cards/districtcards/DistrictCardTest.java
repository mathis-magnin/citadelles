package fr.citadels.gameelements.cards.districtcards;

import fr.citadels.gameelements.cards.CardFamily;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {

    @Test
    void testGetCardName() {
        assertEquals("Manoir", DistrictCardsPile.allDistrictCards[0].getCardName());
        assertNotEquals("Manoir", DistrictCardsPile.allDistrictCards[DistrictCardsPile.allDistrictCards.length - 1].getCardName());
    }

    @Test
    void testGetGoldCost() {
        assertEquals(3, DistrictCardsPile.allDistrictCards[0].getGoldCost());
        assertEquals(6, DistrictCardsPile.allDistrictCards[DistrictCardsPile.allDistrictCards.length - 1].getGoldCost());
    }

    @Test
    void testToString() {
        for (DistrictCard d : DistrictCardsPile.allDistrictCards) {
            assertEquals(d.getCardName() + " (" + d.getGoldCost() + " - " + d.getCardFamily() + ")", d.toString());
        }
    }

    @Test
    void testEquals() {
        assertEquals(DistrictCardsPile.allDistrictCards[0], (DistrictCardsPile.allDistrictCards[0]));
        assertNotEquals(DistrictCardsPile.allDistrictCards[0], (DistrictCardsPile.allDistrictCards[DistrictCardsPile.allDistrictCards.length - 1]));
        assertEquals(DistrictCardsPile.allDistrictCards[0], (new DistrictCard("Manoir", CardFamily.NOBLE, 3)));
    }
}