package fr.citadels.cards.districts;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardsPileTest {
    DistrictCardsPile districtCardsPile = new DistrictCardsPile();

    @BeforeEach
    void setUp() {
        districtCardsPile.initializePile();
    }

    @org.junit.jupiter.api.Test
    void testInitializePile() {
        assertEquals(Arrays.asList(DistrictCardsPile.allDistrictCards), new ArrayList<>(districtCardsPile));
    }

    @org.junit.jupiter.api.Test
    void testShufflePile() {
        districtCardsPile.shufflePile();
        assertNotEquals(Arrays.asList(DistrictCardsPile.allDistrictCards), new ArrayList<>(districtCardsPile));
    }

    @org.junit.jupiter.api.Test
    void testDraw() {
        DistrictCard[] cardsDrawn = new DistrictCard[]{new DistrictCard("Manoir", CardFamily.NOBLE, 3), new DistrictCard("Manoir", CardFamily.NOBLE, 3)};
        assertEquals(Arrays.asList(cardsDrawn), Arrays.asList(districtCardsPile.draw(2)));
    }

    @org.junit.jupiter.api.Test
    void testPlaceBelowPile() {
        districtCardsPile.shufflePile();
        districtCardsPile.placeBelowPile(new DistrictCard("Manoir", CardFamily.NOBLE, 3));
        districtCardsPile.draw(DistrictCardsPile.allDistrictCards.length);
        assertEquals(new DistrictCard("Manoir", CardFamily.NOBLE, 3), districtCardsPile.draw(1)[0]);
    }
}