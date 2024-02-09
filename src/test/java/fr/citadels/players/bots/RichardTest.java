package fr.citadels.players.bots;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.cards.districts.City;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Hand;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RichardTest {

    Game game;
    Random rand;

    Player bob;
    Player lou;
    Player dan;
    Richard richard;

    Character assassin = CharactersList.allCharacterCards[0];
    Character thief = CharactersList.allCharacterCards[1];
    Character magician = CharactersList.allCharacterCards[2];
    Character king = CharactersList.allCharacterCards[3];
    Character bishop = CharactersList.allCharacterCards[4];
    Character merchant = CharactersList.allCharacterCards[5];
    Character architect = CharactersList.allCharacterCards[6];
    Character warlord = CharactersList.allCharacterCards[7];


    @BeforeEach
    void setUp() {
        Player[] players = new Player[]{bob, lou, dan, richard};
        game = new Game(players, rand);
        game.getPile().initializePile();
        bob = new Monarchist("bob", new Hand(new ArrayList<>()), game);
        lou = new Monarchist("lou", new Hand(new ArrayList<>()), game);
        dan = new Monarchist("dan", new Hand(new ArrayList<>()), game);
        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);
    }


    @AfterEach
    void resetCharacterCards() {
        CharactersList.allCharacterCards[0] = new Assassin();
        CharactersList.allCharacterCards[1] = new Thief();
        CharactersList.allCharacterCards[2] = new Magician();
        CharactersList.allCharacterCards[3] = new King();
        CharactersList.allCharacterCards[4] = new Bishop();
        CharactersList.allCharacterCards[5] = new Merchant();
        CharactersList.allCharacterCards[6] = new Architect();
        CharactersList.allCharacterCards[7] = new Warlord();
    }


    @Test
    void chooseTargetToRob() {
        Player[] players = new Player[]{bob, lou, richard, dan};
        richard.setCharacter(CharactersList.allCharacterCards[1]);  // Thief
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[6], CharactersList.allCharacterCards[3]}));     // Architect and King
        richard.getMemory().setPossibleCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[4], CharactersList.allCharacterCards[5]}));     // Bishop and Merchant
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(2);

        richard.chooseTargetToRob();
        assertEquals(CharactersList.allCharacterCards[7], richard.getMemory().getTarget());

        bob.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4])));
        richard.chooseTargetToRob();
        assertEquals(CharactersList.allCharacterCards[7], richard.getMemory().getTarget());
        // remove one in bob citys ?

        bob.getCity().remove(0);
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[7], DistrictsPile.allDistrictCards[8], DistrictsPile.allDistrictCards[9])));
        richard.chooseTargetToRob();
        assertEquals(CharactersList.allCharacterCards[4], richard.getMemory().getTarget());
    }

    void chooseCharacter() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        richard.chooseCharacter(characters);
        assertEquals(magician, richard.getCharacter());

        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));
        richard.getMemory().setPreviousArchitect(bob);
        bob.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));

        richard.chooseCharacter(characters);
        assertEquals(magician, richard.getCharacter());

        richard.getMemory().setPreviousArchitect(null);
        bob.setHand(new Hand(new ArrayList<>()));

        richard.chooseCharacter(characters);
        assertEquals(assassin, richard.getCharacter());

        bob.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[29], DistrictsPile.allDistrictCards[24])));
        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[25])));

        richard.chooseCharacter(characters);
        assertEquals(thief, richard.getCharacter());

        richard.getMemory().setTurnNumber(7);

        richard.chooseCharacter(characters);
        assertEquals(characters.get(0), richard.getCharacter());
    }


    @Test
    void richardHasSixDistricts() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]
        richard.setCity(new City(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));

        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        assertTrue(richard.richardHasSixDistricts());
        assertEquals(assassin, richard.getCharacter());

        players = new Player[]{dan, richard, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);
        richard.getMemory().getPossibleCharacters().remove(assassin);

        assertTrue(richard.richardHasSixDistricts());
        assertEquals(warlord, richard.getCharacter());

        players = new Player[]{dan, bob, richard, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(2);

        assertFalse(richard.richardHasSixDistricts());
    }


    @Test
    void anotherPlayerHasSixDistricts() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        List<Player> playersWithSixDistricts = richard.getPlayersWithMinCity(Arrays.asList(richard.getMemory().getPlayers()), 6);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts));
        assertEquals(assassin, richard.getCharacter());

        players = new Player[]{richard, bob, dan, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts));
        assertEquals(warlord, richard.getCharacter());

        players = new Player[]{bob, richard, dan, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts));
        assertEquals(assassin, richard.getCharacter());

        players = new Player[]{dan, richard, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        assertFalse(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts));
    }


    @Test
    void anotherPlayerHasFiveDistricts() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        assertTrue(richard.anotherPlayerHasFiveDistricts());
        assertEquals(king, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(king);
        assertTrue(richard.anotherPlayerHasFiveDistricts());
        assertEquals(assassin, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(assassin);
        assertTrue(richard.anotherPlayerHasFiveDistricts());
        assertEquals(warlord, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(warlord);
        assertTrue(richard.anotherPlayerHasFiveDistricts());
        assertEquals(bishop, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(bishop);
        assertFalse(richard.anotherPlayerHasFiveDistricts());
    }


    @Test
    void aPlayerHasFourGoldsAndOneCardInHand() {
        List<Player> playersWithFourGoldsAndOneHand = new ArrayList<>(List.of(richard));
        List<Player> playersWithFourGoldsOneHandFourDistricts = new ArrayList<>();

        assertTrue(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts));
        assertEquals(architect, richard.getCharacter());

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);

        playersWithFourGoldsAndOneHand = new ArrayList<>();
        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>();
        assertFalse(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts));

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);

        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>(List.of(richard));
        assertFalse(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts));

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);

        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>(List.of(bob));
        assertTrue(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts));
        assertEquals(assassin, richard.getCharacter());
    }


    @Test
    void chooseTargetToDestroy1() {
        // Lou played the king
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[2], CharactersList.allCharacterCards[4]}));     // Magician and Bishop
        bob.setCharacter(assassin);
        lou.setCharacter(king);
        dan.setCharacter(architect);
        richard.setCharacter(warlord);

        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        richard.chooseTargetToDestroy();
        assertEquals(CharactersList.allCharacterCards[3], richard.getMemory().getTarget());
        assertEquals(DistrictsPile.allDistrictCards[12], richard.getMemory().getDistrictToDestroy());

    }


    @Test
    void chooseTargetToDestroy2() {
        // Same but no one played the king
        richard.getMemory().setTarget(null);
        richard.getMemory().setDistrictToDestroy(null);

        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[2], CharactersList.allCharacterCards[4]}));     // Magician and Bishop
        bob.setCharacter(assassin);
        lou.setCharacter(thief);
        dan.setCharacter(architect);
        richard.setCharacter(warlord);

        bob.setCity(new City(List.of(DistrictsPile.allDistrictCards[29], DistrictsPile.allDistrictCards[24]))); // Cheapest [24] 1
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        richard.setGold(20);
        richard.chooseTargetToDestroy();
        assertEquals(assassin, richard.getMemory().getTarget());
        assertEquals(DistrictsPile.allDistrictCards[24], richard.getMemory().getDistrictToDestroy());
    }


    @Test
    void triangularCharacterChoice() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        assertTrue(richard.triangularCharacterChoice(assassin, thief, magician, king, bishop, merchant, architect));
        assertEquals(king, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(assassin);
        assertTrue(richard.triangularCharacterChoice(assassin, thief, magician, king, bishop, merchant, architect));
        assertEquals(bishop, richard.getCharacter());

        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(thief);
        assertTrue(richard.triangularCharacterChoice(assassin, thief, magician, king, bishop, merchant, architect));
        assertEquals(merchant, richard.getCharacter());

        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(magician);
        assertTrue(richard.triangularCharacterChoice(assassin, thief, magician, king, bishop, merchant, architect));
        assertEquals(architect, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(assassin);
        assertFalse(richard.triangularCharacterChoice(assassin, thief, magician, king, bishop, merchant, architect));
    }


    @Test
    void chooseInOrder() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        assertTrue(richard.chooseInOrder(assassin, thief, magician));
        assertEquals(assassin, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(assassin);
        assertTrue(richard.chooseInOrder(assassin, thief, magician));
        assertEquals(thief, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(thief);
        assertTrue(richard.chooseInOrder(assassin, thief, magician));
        assertEquals(magician, richard.getCharacter());

        richard.getMemory().getPossibleCharacters().remove(magician);
        assertFalse(richard.chooseInOrder(assassin, thief, magician));
    }


    @Test
    void getPlayersAboutToWin() {
        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        assertEquals(2, richard.getPlayersWithMinCity(List.of(bob, lou, dan), 5).size());
    }

}