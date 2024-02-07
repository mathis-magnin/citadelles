package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    Player player;
    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        player = new Monarchist("Monarchist", new ArrayList<>(), game);
        players[0] = player;
        player.setCharacter(new Bishop());
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[14], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[3])));
    }

    @Test
    void usePower() {
        assertEquals(0, player.getGold());
        player.getCharacter().usePower();
        assertEquals(3, player.getGold());

        player.getCity().add(DistrictsPile.allDistrictCards[63]);
        DistrictsPile.allDistrictCards[63].setOwner(player);
        player.getCharacter().usePower();
        assertEquals(7, player.getGold());

        DistrictsPile.allDistrictCards[63].setOwner(null);

    }

}