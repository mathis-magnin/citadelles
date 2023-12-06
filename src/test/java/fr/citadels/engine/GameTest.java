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

    @BeforeEach
    void setUp() {
        Player player1 = new BotFirstStrategy("Player 1", new ArrayList<DistrictCard>());
        Player player2 = new BotFirstStrategy("Player 2", new ArrayList<DistrictCard>());

        Game game = new Game(player1, player2);
    }

    @Test
    void playGameTest() {
        Player player1 = new BotFirstStrategy("Player 1", new ArrayList<DistrictCard>());
        Player player2 = new BotFirstStrategy("Player 2", new ArrayList<DistrictCard>());

        Game game = new Game(player1, player2);
        game.playGame();
    }

}