package fr.citadels.cards.districtcards.unique;

import fr.citadels.cards.charactercards.characters.KingCard;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {

    Laboratory laboratory = new Laboratory();

    @Test
    void build() {
        laboratory.build(new KingBot("KingBot", List.of(laboratory), new Game()));
        assertTrue(laboratory.isBuilt());
        assertEquals(laboratory.getOwner().getName(), "KingBot");

    }

    @Test
    void useEffect() {
        Game game = new Game();
        Player king = new KingBot("KingBot", List.of(laboratory), game);
        game.getPile().initializePile();
        king.setCharacter(new KingCard());
        laboratory.useEffect();
        assertEquals(1, king.getHand().size());
        assertEquals(0, king.getGold());
    }
}