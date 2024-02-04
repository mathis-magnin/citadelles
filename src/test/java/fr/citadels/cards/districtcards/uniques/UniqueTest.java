package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.districtcards.uniques.Unique;

import fr.citadels.engine.Game;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UniqueTest {

    @Test
    void isBuilt() {
        KingBot kingBot = new KingBot("KingBot", new ArrayList<>(), new Game());
        Unique unique = new Library();
        assertFalse(unique.isBuilt());
        unique.build(kingBot);
        assertTrue(unique.isBuilt());
        assertEquals(unique.getOwner(), kingBot);
    }

}