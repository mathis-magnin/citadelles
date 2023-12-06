package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.players.BotFirstStrategy;
import fr.citadels.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CrownTest {
    @Test
    void crownTest() {
        Crown crown = new Crown();
        Player player = new BotFirstStrategy("player", new ArrayList<DistrictCard>());
        Player player2 = new BotFirstStrategy("player2", new ArrayList<DistrictCard>());

        crown.setPlayerWithCrown(player);
        assertEquals(player, crown.getPlayerWithCrown());
        crown.setPlayerIndexWithCrown(1);
        assertEquals(1, crown.getPlayerIndexWithCrown());

        crown.setPlayerWithCrown(player2);
        assertEquals(player2, crown.getPlayerWithCrown());
        crown.setPlayerIndexWithCrown(2);
        assertEquals(2, crown.getPlayerIndexWithCrown());
    }
}