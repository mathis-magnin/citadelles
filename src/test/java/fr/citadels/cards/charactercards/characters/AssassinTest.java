package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssassinTest {
    Player player = new Monarchist("Hello1", List.of(DistrictsPile.allDistrictCards[0]), new Game());
    Assassin assassin;
    King king;

    @Test
    void usePower() {
        assassin = new Assassin();
        king = new King();
        assassin.setPlayer(player);
        player.getMemory().setTarget(king);
        assassin.usePower();
        Assertions.assertTrue(king.isDead());
    }

    @Test
    void getPossibleTargets() {
        CharactersList targets = Assassin.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new Thief(), new Magician(), new King(), new Bishop(), new Merchant(), new Architect(), new Warlord()}), targets);
    }
}