package fr.citadels.cards.districtcards;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.districtcards.uniques.Library;
import fr.citadels.engine.Game;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DistrictCardTest {

    @Test
    void testGetCardName() {
        Assertions.assertEquals("Manoir", DistrictCardsPile.allDistrictCards[0].getCardName());
        Assertions.assertNotEquals("Manoir", DistrictCardsPile.allDistrictCards[DistrictCardsPile.allDistrictCards.length - 1].getCardName());
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

    @Test
    void testIsBuilt() {
        DistrictCard d = new DistrictCard("Manoir", CardFamily.NOBLE, 3);
        assertFalse(d.isBuilt());
    }

    @Test
    void isBuilt() {
        KingBot kingBot = new KingBot("KingBot", new ArrayList<>(), new Game());
        DistrictCard unique = new Library();
        assertFalse(unique.isBuilt());
        unique.setOwner(kingBot);
        assertTrue(unique.isBuilt());
        assertEquals(unique.getOwner(), kingBot);
    }

}