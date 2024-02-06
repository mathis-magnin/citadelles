package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    // Cité vide
    City c1 = new City();

    // Cité complète avec une carte de chaque famille
    City c2 = new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[17], DistrictsPile.allDistrictCards[65], DistrictsPile.allDistrictCards[33], DistrictsPile.allDistrictCards[55]));

    // Cité complète sans une carte de chaque famille
    City c3 = new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[17], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[7]));

    // City with all except Military but with MiracleCourtyard district
    City c4 = new City(List.of(DistrictsPile.allDistrictCards[57], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[58])); // Noble, Religious, Trade, Unique

    // City with 3 family but with MiracleCourtyard district
    City c5 = new City(List.of(DistrictsPile.allDistrictCards[57], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25]));

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
    void getNumberOfDistrictWithFamily() {
        assertEquals(1, c2.getNumberOfDistrictWithFamily(Family.NOBLE));
        assertEquals(2, c2.getNumberOfDistrictWithFamily(Family.RELIGIOUS));
        assertEquals(2, c2.getNumberOfDistrictWithFamily(Family.TRADE));
        assertEquals(2, c2.getNumberOfDistrictWithFamily(Family.MILITARY));
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
        assertEquals(-1, c2.getFirstNotDuplicateIndex(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[17], DistrictsPile.allDistrictCards[65], DistrictsPile.allDistrictCards[33], DistrictsPile.allDistrictCards[55])));

        //no duplicates
        assertEquals(0, c2.getFirstNotDuplicateIndex(List.of(DistrictsPile.allDistrictCards[9], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[35], DistrictsPile.allDistrictCards[49], DistrictsPile.allDistrictCards[66], DistrictsPile.allDistrictCards[52], DistrictsPile.allDistrictCards[43], DistrictsPile.allDistrictCards[50])));

        //some duplicates at the beginning
        assertEquals(2, c2.getFirstNotDuplicateIndex(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[33])));

        //some duplicates at the end
        assertEquals(0, c2.getFirstNotDuplicateIndex(List.of(DistrictsPile.allDistrictCards[58], DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[17])));
    }

}