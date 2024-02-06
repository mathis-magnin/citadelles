package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.characters.King;
import fr.citadels.cards.districtcards.City;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    Factory factory = new Factory();

    @Test
    void useEffect() {
        Player[] players = new Player[4];
        Game game = new Game(players, new Random());
        Player king = new Monarchist("KingBot", new ArrayList<>(), game);
        players[0] = king;

        game.getPile().initializePile();
        king.setCharacter(new King());
        factory.useEffect();
        assertEquals(0, king.getHand().size());

        king.setCity(new City(List.of(factory)));
        factory.setOwner(king);
        assertEquals(1, king.getCity().size());

        king.getActions().addGold(3);
        factory.useEffect();
        assertEquals(3, king.getHand().size());

        factory.useEffect();
        assertEquals(3, king.getHand().size());
    }
}