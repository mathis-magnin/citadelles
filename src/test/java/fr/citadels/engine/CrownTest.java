package fr.citadels.engine;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.players.BotFirstStrategy;
import fr.citadels.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CrownTest {
    @Test
    void crownTest() {
        Crown crown = new Crown();
        Player player = new BotFirstStrategy("player", new ArrayList<DistrictCard>(),new Random());
        Player player2 = new BotFirstStrategy("player2", new ArrayList<DistrictCard>(),new Random());

        crown.initializeCrown();
        assertNotEquals(-1, crown.getCrownedPlayerIndex());

        crown.setCrownedPlayerIndex(1);
        assertEquals(1, crown.getCrownedPlayerIndex());
    }
}