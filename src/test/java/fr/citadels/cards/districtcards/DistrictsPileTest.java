package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DistrictsPileTest {
    DistrictsPile districtCardsPile = new DistrictsPile();

    @BeforeEach
    void setUp() {
        districtCardsPile.initializePile();
    }

    @org.junit.jupiter.api.Test
    void testInitializePile() {
        assertEquals(Arrays.asList(DistrictsPile.allDistrictCards), new ArrayList<>(districtCardsPile));
    }

    @org.junit.jupiter.api.Test
    void testShufflePile() {
        districtCardsPile.shufflePile();
        assertNotEquals(Arrays.asList(DistrictsPile.allDistrictCards), new ArrayList<>(districtCardsPile));
    }

    @org.junit.jupiter.api.Test
    void testDraw() {
        District[] cardsDrawn = new District[]{new District("Manoir", Family.NOBLE, 3), new District("Manoir", Family.NOBLE, 3)};
        assertEquals(Arrays.asList(cardsDrawn), Arrays.asList(districtCardsPile.draw(2)));
    }

    @org.junit.jupiter.api.Test
    void testPlaceBelowPile() {
        districtCardsPile.shufflePile();
        districtCardsPile.placeBelowPile(new District("Manoir", Family.NOBLE, 3));
        districtCardsPile.draw(DistrictsPile.allDistrictCards.length);
        assertEquals(new District("Manoir", Family.NOBLE, 3), districtCardsPile.draw(1)[0]);
    }

    @Test
    void reset() {
        Player player = new Monarchist("hello");
        for (District district : districtCardsPile) {
            district.setOwner(player);
            assertEquals(district.getOwner(), player);
        }

        districtCardsPile.reset();
        for (District district : districtCardsPile) {
            assertNull(district.getOwner());
        }

    }


}