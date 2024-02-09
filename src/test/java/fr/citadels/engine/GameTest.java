package fr.citadels.engine;

import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.cards.districts.City;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Hand;
import fr.citadels.players.Actions;
import fr.citadels.players.Memory;
import fr.citadels.players.Player;
import fr.citadels.players.bots.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[5];
        players[0] = new Uncertain("HASARDEUX", new Random());
        players[1] = new Spendthrift("DÉPENSIER", new Random());
        players[2] = new Thrifty("ÉCONOME", new Random());
        players[3] = new Monarchist("MONARCHISTE");
        players[4] = new Richard("RICHARD");

        game = new Game(players, new Random());
        game.initializeGame();
    }


    @Test
    void initializeGameTest() {
        assertEquals(47, game.getPile().size()); // 51 for 4 players
        for (Player player : game.getPlayers()) {
            assertEquals(4, player.getHand().size());
            assertEquals(2, player.getGold());
        }
    }

    @Test
    void playSelectionPhaseTest() {
        game.playSelectionPhase();
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getCharacter());
        }

        game.getPlayers()[0].setCharacter(new King());
        Memory memory = game.getPlayers()[0].getMemory();
        Actions actions = game.getPlayers()[0].getActions();
        game.getPlayers()[1].setGold(7);
        game.getPlayers()[2].setCity(new City(List.of(DistrictsPile.allDistrictCards[0])));
        game.getPlayers()[3].setHand(new Hand(List.of(DistrictsPile.allDistrictCards[5])));


        game.initializeGame();

        assertNull(game.getPlayers()[0].getCharacter());
        assertNotEquals(memory, game.getPlayers()[0].getMemory());
        assertNotEquals(actions, game.getPlayers()[0].getActions());
        assertEquals(2, game.getPlayers()[1].getGold());
        assertEquals(0, game.getPlayers()[2].getCity().size());
        assertEquals(4, game.getPlayers()[3].getHand().size());


    }

    @Test
    void checkAndMarkEndOfGame() {
        game.getPlayers()[0].setCharacter(new King());
        game.getPlayers()[1].setCharacter(new Bishop());
        game.getPlayers()[2].setCharacter(new Merchant());
        game.getPlayers()[3].setCharacter(new Architect());
        game.getPlayers()[4].setCharacter(new Warlord());

        assertFalse(game.isFinished());
        for (int i = 0; i < game.getPlayers().length - 1; i++) {
            game.checkAndMarkEndOfGame(game.getPlayers()[i].getCharacter());
            assertFalse(game.isFinished());
        }
        game.getPlayers()[3].setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[6])));
        game.checkAndMarkEndOfGame(game.getPlayers()[3].getCharacter());
        assertTrue(game.isFinished());
    }

    @Test
    void setNextCrownedPlayerIfPossible() {
        game.getPlayers()[0].setCharacter(new Assassin());
        game.getPlayers()[1].setCharacter(new King());
        game.getPlayers()[2].setCharacter(new Merchant());
        game.getPlayers()[3].setCharacter(new Architect());
        game.getPlayers()[4].setCharacter(new Warlord());

        game.setNextCrownedPlayerIfPossible(game.getPlayers()[0].getCharacter());
        game.setNextCrownedPlayerIfPossible(game.getPlayers()[1].getCharacter());
        game.setNextCrownedPlayerIfPossible(game.getPlayers()[2].getCharacter());
        game.setNextCrownedPlayerIfPossible(game.getPlayers()[3].getCharacter());
        game.setNextCrownedPlayerIfPossible(game.getPlayers()[4].getCharacter());
        assertEquals(game.getPlayers()[1], game.getCrownedPlayer());


    }

    @Test
    void initializePlayersTest() {
        game.initializePlayers();
        for (Player player : game.getPlayers()) {
            assertEquals(0, player.getGold());
            assertEquals(4, player.getHand().size());
            assertEquals(0, player.getCity().size());
            assertNull(player.getCharacter());
            assertNotNull(player.getActions());
            assertNotNull(player.getMemory());
        }
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

}