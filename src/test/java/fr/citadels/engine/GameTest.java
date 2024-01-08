package fr.citadels.engine;

import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.citadels.engine.Game.BANK;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();

    @BeforeEach
    void setUp() {
        BANK.reset();
        game.initializeGame();
    }


    @Test
    void initializeGameTest() {
        assertEquals(51, game.getDistrictCardsPile().size());
        for (Player player : game.getPlayerList()) {
            assertEquals(4, player.getCardsInHand().size());
        }
    }

    @Test
    void playSelectionPhaseTest() {
        game.playSelectionPhase();
        for (Player player : game.getPlayerList()) {
            assertNotNull(player.getCharacter());
        }
    }
}