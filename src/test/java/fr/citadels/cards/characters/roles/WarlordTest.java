package fr.citadels.cards.characters.roles;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.districts.City;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WarlordTest {

    Game game;

    Monarchist player1;
    Monarchist player2;
    Monarchist player3;

    @BeforeEach
    void setUp() {

        Player[] players = new Player[4];

        game = new Game(players, new Random());
        player1 = new Monarchist("Tom", new ArrayList<>(), game);
        player2 = new Monarchist("Bob", new ArrayList<>(), game);
        player3 = new Monarchist("Sam", new ArrayList<>(), game);

        player1.setCharacter(CharactersList.allCharacterCards[0]);
        player2.setCharacter(CharactersList.allCharacterCards[7]);
        player3.setCharacter(CharactersList.allCharacterCards[1]);
    }

    @Test
    void getPossibleTargets() {
        CharactersList targets = Warlord.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new Assassin(), new Thief(), new Warlord()}), targets);

        player1.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[20], DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[50], DistrictsPile.allDistrictCards[60])));

        targets = Warlord.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new Thief(), new Warlord()}), targets);
    }

    @Test
    void getCharacterWithBiggestCity() {
        player1.setCity(new City(List.of(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[35], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[55], DistrictsPile.allDistrictCards[65])));
        player3.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[20], DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[50], DistrictsPile.allDistrictCards[60])));
        Character characterWithBiggestCity = Warlord.getOtherCharacterWithBiggestCity();
        assertNull(characterWithBiggestCity);

        player1.setCity(new City());
        player3.setCity(new City(List.of(DistrictsPile.allDistrictCards[1])));
        characterWithBiggestCity = Warlord.getOtherCharacterWithBiggestCity();
        assertEquals(new Thief(), characterWithBiggestCity);
    }

    @Test
    void usePower() {
        /* Power 1 : Destroy district */
        player1.setCity(new City(new ArrayList<>(List.of(DistrictsPile.allDistrictCards[0]))));
        DistrictsPile.allDistrictCards[0].setOwner(player1);
        player2.getActions().addGold(2);
        player2.getMemory().setTarget(CharactersList.allCharacterCards[0]);
        player2.getMemory().setDistrictToDestroy(DistrictsPile.allDistrictCards[0]);
        player2.getMemory().setPowerToUse(Power.DESTROY);
        player2.getCharacter().usePower();
        assertFalse(DistrictsPile.allDistrictCards[0].isBuilt());
        assertEquals(0, player2.getGold());
        Assertions.assertEquals(new City(), player1.getCity());

        /* Power 2 : Gain golds with city */
        player2.setCity(new City(List.of(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[35], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[55], DistrictsPile.allDistrictCards[65])));
        // Noble, Religious, Trade, Trade, Military, Military, Unique
        player2.getMemory().setPowerToUse(Power.INCOME);
        player2.getCharacter().usePower();
        assertEquals(2, player2.getGold()); // +2

        player2.setCity(new City(List.of(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[35], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[55], DistrictsPile.allDistrictCards[63])));
        // Noble, Religious, Trade, Trade, Military, Military, Unique(SchoolOfMagic)
        DistrictsPile.allDistrictCards[63].setOwner(player2);
        player2.getCharacter().usePower();
        assertEquals(5, player2.getGold()); // +3
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
