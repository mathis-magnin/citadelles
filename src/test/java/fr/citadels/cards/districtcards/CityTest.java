package fr.citadels.cards.districtcards;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    // Cité vide
    City c1 = new City();

    // Cité complète avec une carte de chaque famille
    City c2 = new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17], DistrictCardsPile.allDistrictCards[65], DistrictCardsPile.allDistrictCards[33], DistrictCardsPile.allDistrictCards[55]));

    // Cité complète sans une carte de chaque famille
    City c3 = new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[7]));

    // City with all except Military but with MiracleCourtyard district
    City c4 = new City(List.of(DistrictCardsPile.allDistrictCards[57], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[58])); // Noble, Religious, Trade, Unique

    // City with 3 family but with MiracleCourtyard district
    City c5 = new City(List.of(DistrictCardsPile.allDistrictCards[57], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25]));

    @Test
    void testCity() {
        assertEquals(0, c1.size());
        assertEquals(8, c2.size());
        assertEquals(7, c3.size());
        assertEquals(5, c4.size());
        assertEquals(4, c5.size());
    }

    @Test
    void isCompleteTest() {
        assertFalse(c1.isComplete());
        assertTrue(c2.isComplete());
        assertTrue(c3.isComplete());
        assertFalse(c4.isComplete());
        assertFalse(c5.isComplete());
    }

    @Test
    void hasOneDistrictOfEachFamilyTest() {
        assertFalse(c1.hasOneDistrictOfEachFamily());
        assertTrue(c2.hasOneDistrictOfEachFamily());
        assertFalse(c3.hasOneDistrictOfEachFamily());
        assertTrue(c4.hasOneDistrictOfEachFamily());
        assertFalse(c5.hasOneDistrictOfEachFamily());
    }

    @Test
    void getFirstNotDuplicateIndex() {
        //all duplicates
        assertEquals(-1, c2.getFirstNotDuplicateIndex(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17], DistrictCardsPile.allDistrictCards[65], DistrictCardsPile.allDistrictCards[33], DistrictCardsPile.allDistrictCards[55])));

        //no duplicates
        assertEquals(0, c2.getFirstNotDuplicateIndex(List.of(DistrictCardsPile.allDistrictCards[9], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[35], DistrictCardsPile.allDistrictCards[49], DistrictCardsPile.allDistrictCards[66], DistrictCardsPile.allDistrictCards[52], DistrictCardsPile.allDistrictCards[43], DistrictCardsPile.allDistrictCards[50])));

        //some duplicates at the beginning
        assertEquals(2, c2.getFirstNotDuplicateIndex(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[19], DistrictCardsPile.allDistrictCards[33])));

        //some duplicates at the end
        assertEquals(0, c2.getFirstNotDuplicateIndex(List.of(DistrictCardsPile.allDistrictCards[58], DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[17])));
    }

}