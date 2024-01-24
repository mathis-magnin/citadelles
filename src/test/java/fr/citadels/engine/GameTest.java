package fr.citadels.engine;

import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import fr.citadels.players.bots.RandomBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();

    @BeforeEach
    void setUp() {
        game.initializeGame();
    }


    @Test
    void initializeGameTest() {
        assertEquals(51, game.getPile().size());
        for (Player player : game.getPlayerList()) {
            assertEquals(4, player.getHand().size());
            assertEquals(2, player.getGold());
        }
    }

    @Test
    void playSelectionPhaseTest() {
        game.playSelectionPhase();
        for (Player player : game.getPlayerList()) {
            assertNotNull(player.getCharacter());
        }
    }

    @Test
    void showCharacterKilledTest() {
        //no player has been killed
        game.showCharacterKilled(null);

        //the card was not attributed to any player
        CharacterCardsList.allCharacterCards[2].setDead(true);
        game.showCharacterKilled(CharacterCardsList.allCharacterCards[2]);
        assertFalse(CharacterCardsList.allCharacterCards[2].isDead());

        //the card was attributed to a player
        CharacterCardsList.allCharacterCards[6].setPlayer(new KingBot("MONARCHISTE", Arrays.asList(game.getPile().draw(4)), game.getPile(), game.getBank(), game.getDisplay()));
        CharacterCardsList.allCharacterCards[6].setDead(true);
        game.showCharacterKilled(CharacterCardsList.allCharacterCards[6]);
        assertFalse(CharacterCardsList.allCharacterCards[2].isDead());

    }
}