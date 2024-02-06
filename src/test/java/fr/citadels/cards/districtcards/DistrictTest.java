package fr.citadels.cards.districtcards;

import fr.citadels.cards.Family;
import fr.citadels.cards.districtcards.uniques.Library;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DistrictTest {

    @Test
    void testGetCardName() {
        Assertions.assertEquals("Manoir", DistrictsPile.allDistrictCards[0].getName());
        Assertions.assertNotEquals("Manoir", DistrictsPile.allDistrictCards[DistrictsPile.allDistrictCards.length - 1].getName());
    }

    @Test
    void testGetGoldCost() {
        assertEquals(3, DistrictsPile.allDistrictCards[0].getGoldCost());
        assertEquals(6, DistrictsPile.allDistrictCards[DistrictsPile.allDistrictCards.length - 1].getGoldCost());
    }

    @Test
    void testToString() {
        for (District d : DistrictsPile.allDistrictCards) {
            assertEquals(d.getName() + " (" + d.getGoldCost() + " - " + d.getFamily() + ")", d.toString());
        }
    }

    @Test
    void testEquals() {
        assertEquals(DistrictsPile.allDistrictCards[0], (DistrictsPile.allDistrictCards[0]));
        assertNotEquals(DistrictsPile.allDistrictCards[0], (DistrictsPile.allDistrictCards[DistrictsPile.allDistrictCards.length - 1]));
        assertEquals(DistrictsPile.allDistrictCards[0], (new District("Manoir", Family.NOBLE, 3)));
    }

    @Test
    void testIsBuilt() {
        District d = new District("Manoir", Family.NOBLE, 3);
        assertFalse(d.isBuilt());
    }

    @Test
    void isBuilt() {
        Player[] players = new Player[4];
        Game game = new Game(players, new Random());
        Monarchist kingBot = new Monarchist("KingBot", new ArrayList<>(), game);
        players[0] = kingBot;
        District unique = new Library();
        assertFalse(unique.isBuilt());
        unique.setOwner(kingBot);
        assertTrue(unique.isBuilt());
        assertEquals(unique.getOwner(), kingBot);
    }

}