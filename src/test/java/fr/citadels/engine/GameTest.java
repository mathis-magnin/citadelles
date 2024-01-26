package fr.citadels.engine;

import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.*;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
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


    @AfterAll
    static void resetCharacterCards() {
        CharacterCardsList.allCharacterCards[0] = new AssassinCard();
        CharacterCardsList.allCharacterCards[1] = new ThiefCard();
        CharacterCardsList.allCharacterCards[2] = new MagicianCard();
        CharacterCardsList.allCharacterCards[3] = new KingCard();
        CharacterCardsList.allCharacterCards[4] = new BishopCard();
        CharacterCardsList.allCharacterCards[5] = new MerchantCard();
        CharacterCardsList.allCharacterCards[6] = new ArchitectCard();
        CharacterCardsList.allCharacterCards[7] = new WarlordCard();
    }

}