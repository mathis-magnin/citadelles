package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.characters.KingCard;
import fr.citadels.cards.charactercards.characters.ThiefCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
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
        laboratory.setOwner(new KingBot("KingBot", List.of(laboratory), new Game()));
        assertTrue(laboratory.isBuilt());
        assertEquals(laboratory.getOwner().getName(), "KingBot");

    }

    @Test
    void useEffect() {
        Game game = new Game();
        Player king = new KingBot("KingBot", List.of(DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[40]), game);
        game.getPile().initializePile();
        king.setCharacter(new KingCard());
        laboratory.setOwner(king);
        king.getCity().add(laboratory);
        laboratory.useEffect();
        assertEquals(laboratory.getOwner(), king);
        assertEquals(2, king.getHand().size());
        assertEquals(0, king.getGold());

        king.getCity().add(DistrictCardsPile.allDistrictCards[0]);
        laboratory.useEffect();
        assertEquals(laboratory.getOwner(), king);
        assertEquals(1, king.getHand().size());
        assertEquals(1, king.getGold());
    }
}