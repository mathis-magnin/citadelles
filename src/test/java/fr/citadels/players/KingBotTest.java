package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.citadels.engine.Game.BANK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class KingBotTest {
    /*same as random bot for now*/

    @Mock
    Random random = mock(Random.class);
    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        BANK.reset();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player1 = new KingBot("Hello1", districts, random);
    }

    @Test
    void chooseCharacter() {
        CharacterCardsList characters=new CharacterCardsList();
        player1.chooseCharacter(characters);
        assertEquals("Roi",player1.getCharacter().getCardName());
        assertEquals(7,characters.size());

        player1.chooseCharacter(characters);
        assertEquals("Assassin",player1.getCharacter().getCardName());
        assertEquals(6,characters.size());
    }

}