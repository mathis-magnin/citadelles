package fr.citadels.players.bots;

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
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RichardTest {

    Game game;
    Random rand = mock(Random.class);

    Player bob;
    Player lou;
    Player dan;
    Richard richard;

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
    void chooseTargetToDestroy1() {
        // Lou played the king
        richard.getMemory().setFaceUpCharacters(new CharactersList(new Character[]{CharactersList.allCharacterCards[2], CharactersList.allCharacterCards[4]}));     // Magician and Bishop
        bob.setCharacter(CharactersList.allCharacterCards[0]);      // Assassin
        lou.setCharacter(CharactersList.allCharacterCards[3]);      // King
        dan.setCharacter(CharactersList.allCharacterCards[6]);      // Architect
        richard.setCharacter(CharactersList.allCharacterCards[7]);  // Warlord

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
        bob.setCharacter(CharactersList.allCharacterCards[0]);      // Assassin
        lou.setCharacter(CharactersList.allCharacterCards[1]);      // thief
        dan.setCharacter(CharactersList.allCharacterCards[6]);      // Architect
        richard.setCharacter(CharactersList.allCharacterCards[7]);  // Warlord

        bob.setCity(new City(List.of(DistrictsPile.allDistrictCards[29], DistrictsPile.allDistrictCards[24]))); // Cheapest [24] 1
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        richard.setGold(20);
        richard.chooseTargetToDestroy();
        assertEquals(CharactersList.allCharacterCards[0], richard.getMemory().getTarget());
        assertEquals(DistrictsPile.allDistrictCards[24], richard.getMemory().getDistrictToDestroy());
    }


    @Test
    void getPlayersAboutToWin() {
        bob.setCity(new City());
        lou.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[15])));   // Cheapest district 1 [12]
        dan.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[22])));   // Cheapest district 2 [15]

        assertEquals(2, richard.getPlayersAboutToWin(List.of(bob, lou, dan)).size());
    }


    @Test
    void getCharactersWhoPlayed() {
        bob.setCharacter(CharactersList.allCharacterCards[0]);      // Assassin
        lou.setCharacter(CharactersList.allCharacterCards[3]);      // King
        dan.setCharacter(CharactersList.allCharacterCards[6]);      // Architect

        richard.setCharacter(CharactersList.allCharacterCards[7]);  // Warlord
        assertEquals(3, richard.getCharactersWhoPlayed().size());

        richard.setCharacter(CharactersList.allCharacterCards[1]);  // Thief
        assertEquals(1, richard.getCharactersWhoPlayed().size());
    }

}