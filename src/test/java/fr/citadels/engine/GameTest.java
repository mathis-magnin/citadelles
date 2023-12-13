package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.players.BotFirstStrategy;
import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();

    @BeforeEach
    void setUp() {
        game.initializeGame();
    }


    @Test
    void initializeGameTest() {
        assertEquals(59, game.getDistrictCardsPile().getPile().size());
        for (Player player : game.getPlayerList()) {
            assertEquals(4, player.getCardsInHand().size());
        }
    }


    @Test
    void playGameTest() {
        Game game1 = new Game();
        game1.playGame();
    }

}