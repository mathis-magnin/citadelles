package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArchitectCardTest {

    Player player;
    Game game;

    @BeforeEach
    void init() {
        game = new Game();
        game.getPile().initializePile();
        player = new KingBot("Gustave Eiffel", Arrays.asList(DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[10]), game);
        player.setCharacter(new ArchitectCard());
        player.getActions().addGold(10);
    }


    @Test
    void usePower() {
        /* Power 1 */
        player.getInformation().setPowerToUse(1);

        int handSize = player.getHand().size();
        player.getCharacter().usePower();
        Assertions.assertEquals(handSize + 2, player.getHand().size());

        /* Power 2 */
        player.getInformation().setPowerToUse(2);

        int citySize = player.getCity().size();
        player.chooseDistrictToBuild();
        player.getCharacter().usePower();
        Assertions.assertEquals(citySize + 1, player.getCity().size());

        player.chooseDistrictToBuild();
        player.getCharacter().usePower();
        Assertions.assertEquals(citySize + 2, player.getCity().size());
    }

}