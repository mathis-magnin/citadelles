package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArchitectCardTest {

    Player player;

    @BeforeEach
    void init() {
        player = new KingBot("Gustave Eiffel", Arrays.asList(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]), new Game());
        player.setCharacter(new ArchitectCard());
    }


    @Test
    void usePower() {
        /* Power 1
        player.getInformation().setPowerToUse(1);

        int handSize = player.getHand().size();
        player.getCharacter().usePower();
        assertEquals(handSize + 2, player.getHand().size());

        /* Power 2
        player.getInformation().setPowerToUse(2);

        int citySize = player.getCity().size();
        player.chooseCardInHand();
        player.getCharacter().usePower();
        assertEquals(citySize + 1, player.getCity().size());

        player.chooseCardInHand();
        player.getCharacter().usePower();
        assertEquals(citySize + 2, player.getCity().size());
        */
    }

}
