package fr.citadels.players;

import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.characters.KingCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInformationTest {
    Game game = new Game();
    PlayerInformation info = new PlayerInformation(game);

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
        info.setTarget(new KingCard());
        assertEquals(new KingCard(), info.getTarget());

    }

}