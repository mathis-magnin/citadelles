package fr.citadels.gameelements.cards.districtcards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    //Cité vide
    City c1 = new City();
    //Cité complète avec une carte de chaque famille
    City c2 = new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17], DistrictCardsPile.allDistrictCards[65], DistrictCardsPile.allDistrictCards[33], DistrictCardsPile.allDistrictCards[55]));
    //Cité complète sans une carte de chaque famille
    City c3 = new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[7]));

    @Test
    void testCity(){
        assertEquals(0, c1.size());
        assertEquals(8, c2.size());
        assertEquals(7, c3.size());
    }

    @Test
    void isCompleteTest() {
        assertFalse(c1.isComplete());
        assertTrue(c2.isComplete());
        assertTrue(c3.isComplete());
    }

    @Test
    void hasOneDistrictOfEachFamilyTest() {
        assertFalse(c1.hasOneDistrictOfEachFamily());
        assertTrue(c2.hasOneDistrictOfEachFamily());
        assertFalse(c3.hasOneDistrictOfEachFamily());
    }

}