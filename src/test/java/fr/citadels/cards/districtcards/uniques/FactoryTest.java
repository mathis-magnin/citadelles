package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.characters.King;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    Factory factory = new Factory();

    @Test
    void useEffect() {
        Player[] players = new Player[4];
        Game game = new Game(players, new Random());
        Player king = new Monarchist("KingBot", List.of(factory), game);
        players[0] = king;
        game.getPile().initializePile();
        king.setCharacter(new King());
        factory.useEffect();
        assertEquals(1, king.getHand().size());

        king.getActions().addGold(6);
        king.getMemory().setDistrictToBuild(factory);
        king.getActions().build();
        assertEquals(1, king.getCity().size());

        king.getActions().addGold(3);
        factory.useEffect();
        assertEquals(4, king.getHand().size());
    }
}