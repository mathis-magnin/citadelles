package fr.citadels.cards;

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
            assertEquals("Carte quartier " + d.getCardName(), d.toString());
        }
    }

    @Test
    void testEquals() {
        assertTrue(DistrictCardsPile.allDistrictCards[0].equals(DistrictCardsPile.allDistrictCards[0]));
        assertFalse(DistrictCardsPile.allDistrictCards[0].equals(DistrictCardsPile.allDistrictCards.length - 1));
        assertTrue(DistrictCardsPile.allDistrictCards[0].equals(new DistrictCard("Manoir", 3)));
    }
}