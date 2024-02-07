package fr.citadels.engine;

import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
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
        for (Player player : game.getPlayersTab()) {
            assertEquals(4, player.getHand().size());
            assertEquals(2, player.getGold());
        }
    }

    @Test
    void playSelectionPhaseTest() {
        game.playSelectionPhase();
        for (Player player : game.getPlayersTab()) {
            assertNotNull(player.getCharacter());
        }

        game.getPlayersTab()[0].setCharacter(new King());
        Memory memory = game.getPlayersTab()[0].getMemory();
        Actions actions = game.getPlayersTab()[0].getActions();
        game.getPlayersTab()[1].setGold(7);
        game.getPlayersTab()[2].setCity(new City(List.of(DistrictsPile.allDistrictCards[0])));
        game.getPlayersTab()[3].setHand(new Hand(List.of(DistrictsPile.allDistrictCards[5])));


        game.initializeGame();

        assertNull(game.getPlayersTab()[0].getCharacter());
        assertNotEquals(memory, game.getPlayersTab()[0].getMemory());
        assertNotEquals(actions, game.getPlayersTab()[0].getActions());
        assertEquals(2, game.getPlayersTab()[1].getGold());
        assertEquals(0, game.getPlayersTab()[2].getCity().size());
        assertEquals(4, game.getPlayersTab()[3].getHand().size());


    }

    @Test
    void initializePlayersTest() {
        game.initializePlayers();
        for (Player player : game.getPlayersTab()) {
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