package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThiefTest {

    Character thief = new Thief();
    Character king = new King();

    Game game;
    List<District> hand1 = Arrays.asList(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]);
    List<District> hand2 = Arrays.asList(DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5]);

    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();

        player1 = new Monarchist("Tom", hand1, game);
        player2 = new Monarchist("Bob", hand2, game);

        player1.setCharacter(thief);
        player2.setCharacter(king);

        player1.getMemory().setTarget(king);

        player1.setGold(10);
        player2.setGold(10);
    }

    @Test
    void thiefTest() {
        Assertions.assertEquals("Voleur", thief.getName());
        assertEquals(2, thief.getRank());
        Assertions.assertEquals("Neutre", thief.getFamily().toString());
        assertFalse(thief.isRobbed());
        assertEquals(player1, thief.getPlayer());
        Assertions.assertEquals(player1.getMemory().getTarget(), king);
    }

    @Test
    void bringIntoPlay() {
        Assertions.assertEquals(0, player1.getCity().size());
        thief.bringIntoPlay();
        Assertions.assertEquals(1, player1.getCity().size());
    }

    @Test
    void usePower() {
        thief.usePower();
        Assertions.assertTrue(player2.getCharacter().isRobbed());
    }

    @Test
    void getPossibleTargets() {
        CharactersList targets = Thief.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new Magician(), new King(), new Bishop(), new Merchant(), new Architect(), new Warlord()}), targets);
        CharactersList.allCharacterCards[3].setDead(true);
        targets = Thief.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new Magician(), new Bishop(), new Merchant(), new Architect(), new Warlord()}), targets);
    }

    @AfterAll
    static void resetCharacterCards() {
        CharactersList.allCharacterCards[0] = new Assassin();
        CharactersList.allCharacterCards[1] = new Thief();
        CharactersList.allCharacterCards[2] = new Magician();
        CharactersList.allCharacterCards[3] = new King();
        CharactersList.allCharacterCards[4] = new Bishop();
        CharactersList.allCharacterCards[5] = new Merchant();
        CharactersList.allCharacterCards[6] = new Architect();
        CharactersList.allCharacterCards[7] = new Warlord();
    }

}