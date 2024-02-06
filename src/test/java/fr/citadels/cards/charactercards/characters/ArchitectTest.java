package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArchitectTest {

    Player player;
    Game game;

    @BeforeEach
    void init() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        player = new Monarchist("Gustave Eiffel", Arrays.asList(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10]), game);
        players[0] = player;
        game.getPile().initializePile();
        player.setCharacter(new Architect());
        player.getActions().addGold(10);
    }


    @Test
    void usePower() {
        /* Power 1 */
        player.getMemory().setPowerToUse(1);

        int handSize = player.getHand().size();
        player.getCharacter().usePower();
        Assertions.assertEquals(handSize + 2, player.getHand().size());

        /* Power 2 */
        player.getMemory().setPowerToUse(2);

        int citySize = player.getCity().size();
        player.chooseDistrictToBuild();
        player.getCharacter().usePower();
        Assertions.assertEquals(citySize + 1, player.getCity().size());

        player.chooseDistrictToBuild();
        player.getCharacter().usePower();
        Assertions.assertEquals(citySize + 2, player.getCity().size());
    }

}