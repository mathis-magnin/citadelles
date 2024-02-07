package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.characters.King;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {

    Laboratory laboratory = new Laboratory();

    @Test
    void build() {
        Player[] players = new Player[4];
        Game game = new Game(players, new Random());
        laboratory.setOwner(new Monarchist("KingBot", List.of(laboratory), game));
        players[0] = laboratory.getOwner();
        assertTrue(laboratory.isBuilt());
        assertEquals(laboratory.getOwner().getName(), "KingBot");

    }

    @Test
    void useEffect() {
        Player[] players = new Player[4];
        Game game = new Game(players, new Random());
        Player king = new Monarchist("KingBot", List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[40]), game);
        players[0] = king;
        game.getPile().initializePile();
        king.setCharacter(new King());
        laboratory.setOwner(king);
        king.getCity().add(laboratory);
        laboratory.useEffect();
        assertEquals(laboratory.getOwner(), king);
        assertEquals(2, king.getHand().size());
        assertEquals(0, king.getGold());

        king.getCity().add(DistrictsPile.allDistrictCards[0]);
        laboratory.useEffect();
        assertEquals(laboratory.getOwner(), king);
        assertEquals(1, king.getHand().size());
        assertEquals(1, king.getGold());
    }
}