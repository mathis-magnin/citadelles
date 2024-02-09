package fr.citadels.players.bots;

import com.sun.source.tree.CaseTree;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
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
    Player[] players;

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
        players = new Player[]{bob, lou, dan, richard};
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
    void chooseDistrictToBuild() {
        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15])));
        richard.setGold(10);
        richard.setCharacter(bishop);

        richard.chooseDistrictToBuild();
        assertEquals(DistrictsPile.allDistrictCards[15], richard.getMemory().getDistrictToBuild());

        richard.setCharacter(thief);
        richard.chooseDistrictToBuild();
        assertEquals(DistrictsPile.allDistrictCards[10], richard.getMemory().getDistrictToBuild());
    }


    @Test
    void chooseTargetToKill() {
        richard.setCharacter(assassin);
        players = new Player[]{bob, lou, dan, richard};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);
        richard.getMemory().setPossibleCharacters(new CharactersList(CharactersList.allCharacterCards));
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{magician, merchant}));

        richard.chooseTargetToKill();
        assertEquals(thief, richard.getMemory().getTarget());
    }

    @Test
    void killAtTheEnd() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        players = new Player[]{bob, lou, dan, richard};
        richard.setCharacter(assassin);  // Assassin

        characters.remove(warlord);
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);
        richard.getMemory().setPossibleCharacters(characters);
        bob.getMemory().setPlayerIndex(3);
        bob.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5])));
        List<Player> playersAboutToWin = richard.getPlayersWithMinCity(Arrays.asList(richard.getMemory().getPlayers()), 6);
        assertEquals(magician, richard.killAtTheEnd(playersAboutToWin));

        bob.getMemory().setPlayerIndex(1);
        assertEquals(bishop, richard.killAtTheEnd(playersAboutToWin));

        characters = new CharactersList(CharactersList.allCharacterCards);
        characters.remove(4);
        richard.getMemory().setPossibleCharacters(characters);
        assertEquals(warlord, richard.killAtTheEnd(playersAboutToWin));

        bob.getMemory().setPlayerIndex(4);
        assertNull(richard.killAtTheEnd(playersAboutToWin));
    }

    @Test
    void killPlayerAboutToWin() {
        List<Player> playersAboutToWin = new ArrayList<>(List.of(dan, lou));
        richard.setCharacter(CharactersList.allCharacterCards[0]);  // Assassin
        players = new Player[]{bob, lou, dan, richard};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);
        richard.getMemory().setPossibleCharacters(new CharactersList(CharactersList.allCharacterCards));
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[2]}));     // Magician and Bishop

        assertEquals(king, richard.killPlayerAboutToWin(playersAboutToWin));

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

        bob.getCity().remove(0);
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[7], DistrictsPile.allDistrictCards[8], DistrictsPile.allDistrictCards[9])));
        richard.chooseTargetToRob();
        assertEquals(CharactersList.allCharacterCards[4], richard.getMemory().getTarget());
    }

    @Test
    void chooseCharacter() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        richard.chooseCharacter(characters);
        assertEquals(magician, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));
        richard.getMemory().setPreviousArchitect(bob);
        bob.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));

        richard.chooseCharacter(characters);
        assertEquals(magician, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().setPreviousArchitect(null);
        bob.setHand(new Hand(new ArrayList<>()));

        richard.chooseCharacter(characters);
        assertEquals(assassin, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        bob.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[29], DistrictsPile.allDistrictCards[24])));
        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[25])));

        richard.chooseCharacter(characters);
        assertEquals(thief, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().setTurnNumber(7);

        richard.chooseCharacter(characters);
        assertEquals(assassin, richard.getCharacter());
    }


    @Test
    void richardHasSixDistricts() {
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]
        richard.setCity(new City(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));

        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        assertTrue(richard.richardHasSixDistricts(characters));
        assertEquals(assassin, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        players = new Player[]{dan, richard, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);
        richard.getMemory().getPossibleCharacters().remove(assassin);

        assertTrue(richard.richardHasSixDistricts(characters));
        assertEquals(warlord, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        players = new Player[]{dan, bob, richard, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(2);

        assertFalse(richard.richardHasSixDistricts(characters));
    }


    @Test
    void anotherPlayerHasSixDistricts() {
        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        Player[] players = new Player[]{richard, dan, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        List<Player> playersWithSixDistricts = richard.getPlayersWithMinCity(Arrays.asList(richard.getMemory().getPlayers()), 6);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts, characters));
        assertEquals(assassin, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        players = new Player[]{richard, bob, dan, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(0);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts, characters));
        assertEquals(warlord, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        players = new Player[]{bob, richard, dan, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        assertTrue(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts, characters));
        assertEquals(assassin, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        players = new Player[]{dan, richard, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        assertFalse(richard.anotherPlayerHasSixDistricts(playersWithSixDistricts, characters));
    }


    @Test
    void anotherPlayerHasFiveDistricts() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        assertTrue(richard.anotherPlayerHasFiveDistricts(characters));
        assertEquals(king, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(king);
        assertTrue(richard.anotherPlayerHasFiveDistricts(characters));
        assertEquals(assassin, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(king);
        richard.getMemory().getPossibleCharacters().remove(assassin);
        assertTrue(richard.anotherPlayerHasFiveDistricts(characters));
        assertEquals(warlord, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(king);
        richard.getMemory().getPossibleCharacters().remove(assassin);
        richard.getMemory().getPossibleCharacters().remove(warlord);
        assertTrue(richard.anotherPlayerHasFiveDistricts(characters));
        assertEquals(bishop, richard.getCharacter());

        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().getPossibleCharacters().remove(king);
        richard.getMemory().getPossibleCharacters().remove(assassin);
        richard.getMemory().getPossibleCharacters().remove(warlord);
        richard.getMemory().getPossibleCharacters().remove(bishop);
        assertFalse(richard.anotherPlayerHasFiveDistricts(characters));
    }


    @Test
    void aPlayerHasFourGoldsAndOneCardInHand() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        List<Player> playersWithFourGoldsAndOneHand = new ArrayList<>(List.of(richard));
        List<Player> playersWithFourGoldsOneHandFourDistricts = new ArrayList<>();

        assertTrue(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts, characters));
        assertEquals(architect, richard.getCharacter());

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);
        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        playersWithFourGoldsAndOneHand = new ArrayList<>();
        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>();
        assertFalse(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts, characters));

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);
        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>(List.of(richard));
        assertFalse(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts, characters));

        richard = new Richard("Richard", new Hand(new ArrayList<>()), game);
        characters = new CharactersList(CharactersList.allCharacterCards);
        richard.getMemory().setPossibleCharacters(characters);

        playersWithFourGoldsOneHandFourDistricts = new ArrayList<>(List.of(bob));
        assertTrue(richard.aPlayerHasFourGoldsAndOneCardInHand(playersWithFourGoldsAndOneHand, playersWithFourGoldsOneHandFourDistricts, characters));
        assertEquals(assassin, richard.getCharacter());
    }


    @Test
    void targetPlayerWithSixDistrictsAndBiggestHand() {
        CharactersList magicianTarget = new CharactersList(CharactersList.allCharacterCards);

        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[23])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        bob.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));
        dan.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6])));
        lou.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1])));

        Player[] players = new Player[]{bob, richard, dan, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        dan.setCharacter(assassin);
        richard.setCharacter(thief);
        bob.setCharacter(king);
        lou.setCharacter(warlord);

        assertTrue(richard.targetPlayerWithSixDistrictsAndBiggestHand(magicianTarget));
        assertEquals(assassin, richard.getMemory().getTarget());

        magicianTarget.remove(assassin);

        assertTrue(richard.targetPlayerWithSixDistrictsAndBiggestHand(magicianTarget));
        assertEquals(warlord, richard.getMemory().getTarget());

        richard.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[6], DistrictsPile.allDistrictCards[11], DistrictsPile.allDistrictCards[16], DistrictsPile.allDistrictCards[19], DistrictsPile.allDistrictCards[23])));

        assertFalse(richard.targetPlayerWithSixDistrictsAndBiggestHand(magicianTarget));
    }


    @Test
    void targetAssassinOrBishopOrWarlord() {
        CharactersList magicianTarget = new CharactersList(CharactersList.allCharacterCards);

        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        warlord.setDead(true);
        characters.remove(assassin);
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{architect, warlord}));

        Player[] players = new Player[]{dan, richard, bob, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(1);

        dan.setCharacter(assassin);
        richard.setCharacter(thief);
        lou.setCharacter(magician);
        bob.setCharacter(king);

        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        assertTrue(richard.targetAssassinOrBishopOrWarlord(magicianTarget));
        assertEquals(assassin, richard.getMemory().getTarget());

        warlord.setDead(false);
        characters = new CharactersList(new Character[]{bishop});
        richard.getMemory().setPossibleCharacters(characters);

        players = new Player[]{dan, bob, richard, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(2);

        king.setDead(true);

        assertTrue(richard.targetAssassinOrBishopOrWarlord(magicianTarget));
        assertEquals(bishop, richard.getMemory().getTarget());

        magicianTarget.remove(bishop);

        characters = new CharactersList(new Character[]{warlord});
        richard.getMemory().setPossibleCharacters(characters);
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{merchant, architect}));

        dan.setCity(new City());

        players = new Player[]{dan, bob, richard, lou};
        richard.getMemory().setPlayers(players);
        richard.getMemory().setPlayerIndex(2);

        assertTrue(richard.targetAssassinOrBishopOrWarlord(magicianTarget));
        assertEquals(warlord, richard.getMemory().getTarget());
    }


    @Test
    void searchBishopOrWarlordInTargets() {
        bob.setCharacter(assassin);
        lou.setCharacter(thief);
        dan.setCharacter(magician);

        CharactersList magicianTarget = new CharactersList(CharactersList.allCharacterCards);
        List<Player> playerWithFiveDistricts = new ArrayList<>(List.of(bob, lou));
        List<Player> possibleBishopPlayers = new ArrayList<>(List.of(dan, lou));
        List<Player> possibleWarlordPlayers = new ArrayList<>(List.of(bob));

        assertTrue(richard.searchBishopOrWarlordInTargets(magicianTarget, playerWithFiveDistricts, possibleBishopPlayers, possibleWarlordPlayers));
        assertEquals(bob.getCharacter(), richard.getMemory().getTarget());

        magicianTarget.remove(assassin);
        playerWithFiveDistricts = new ArrayList<>(List.of(lou));
        possibleBishopPlayers = new ArrayList<>(List.of(dan, lou));
        possibleWarlordPlayers = new ArrayList<>(List.of(bob));

        assertTrue(richard.searchBishopOrWarlordInTargets(magicianTarget, playerWithFiveDistricts, possibleBishopPlayers, possibleWarlordPlayers));
        assertEquals(lou.getCharacter(), richard.getMemory().getTarget());

        magicianTarget.remove(assassin);
        playerWithFiveDistricts = new ArrayList<>(List.of(lou));
        possibleBishopPlayers = new ArrayList<>(List.of(dan));
        possibleWarlordPlayers = new ArrayList<>(List.of(bob));

        assertFalse(richard.searchBishopOrWarlordInTargets(magicianTarget, playerWithFiveDistricts, possibleBishopPlayers, possibleWarlordPlayers));
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