package fr.citadels.cards.characters.roles;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssassinTest {

    Player[] players = new Player[4];
    Game game = new Game(players, new Random());
    Player player;
    Assassin assassin;
    King king;

    @BeforeEach
    void setUp() {
        player = new Monarchist("Hello1", List.of(DistrictsPile.allDistrictCards[0]), game);
        players[0] = player;
    }

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