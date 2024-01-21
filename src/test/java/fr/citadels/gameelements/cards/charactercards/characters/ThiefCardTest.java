package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThiefCardTest {

    CharacterCard thief = CharacterCardsList.allCharacterCards[1];
    CharacterCard king = CharacterCardsList.allCharacterCards[3];

    DistrictCardsPile pile = new DistrictCardsPile();
    Bank bank = new Bank();
    Display events = new Display();
    List<DistrictCard> hand1 = Arrays.asList(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]);
    List<DistrictCard> hand2 = Arrays.asList(DistrictCardsPile.allDistrictCards[3], DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5]);

    Player player1 = new KingBot("Tom", hand1, pile, bank, events);
    Player player2 = new KingBot("Bob", hand2, pile, bank, events);

    @BeforeEach
    void setUp() {
        thief.setPlayer(player1);
        player1.setCharacter(thief);

        king.setPlayer(player2);
        player2.setCharacter(king);

        player1.setTarget(king);

        player1.setGold(10);
        player2.setGold(10);
    }

    @Test
    void thiefTest() {
        assertEquals("Voleur", thief.getCardName());
        assertEquals(2, thief.getRank());
        assertEquals("Neutre", thief.getCardFamily().toString());
        assertFalse(thief.isRobbed());
        assertEquals(player1, thief.getPlayer());
        assertEquals(player1.getTarget(), king);
    }

    @Test
    void bringIntoPlay() {
        assertEquals(0, player1.getCity().size());
        thief.bringIntoPlay();
        assertEquals(1, player1.getCity().size());

    }

    @Test
    void usePower() {
        thief.usePower();
        assertTrue(player2.getCharacter().isRobbed());
    }

}