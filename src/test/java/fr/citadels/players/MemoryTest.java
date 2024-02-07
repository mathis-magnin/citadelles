package fr.citadels.players;

import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.characters.King;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {
    Game game = new Game(null, null);
    Memory info = new Memory(game);

    @Test
    void getPile() {
        assertEquals(game.getPile(), info.getPile());
    }

    @Test
    void getDisplay() {
        assertEquals(game.getDisplay(), info.getDisplay());
    }

    @Test
    void getTarget() {
        assertNull(info.getTarget());
    }

    @Test
    void setTarget() {
        info.setTarget(new King());
        assertEquals(new King(), info.getTarget());

    }

}