package fr.citadels.cards.characters;

import fr.citadels.cards.characters.roles.*;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharactersListTest {

    CharactersList characterCards = new CharactersList(CharactersList.allCharacterCards);

    @Test
    void testRemoveCharactersFaceUp() {
        if (Game.NB_PLAYERS == 4) {
            assertEquals(2, characterCards.removeCharactersFaceUp().length);
        } else if (Game.NB_PLAYERS == 5) {
            assertEquals(1, characterCards.removeCharactersFaceUp().length);
        } else {
            assertEquals(0, characterCards.removeCharactersFaceUp().length);
        }
    }

    @Test
    void testRemoveCharactersFaceDown() {
        assertEquals(1, characterCards.removeCharactersFaceDown().length);
    }

    @Test
    void reset() {
        Player player = new Monarchist("hello");
        for (Character character : characterCards) {
            character.setPlayer(player);
            character.setRobbed(true);
            character.setDead(true);
            assertEquals(character.getPlayer(), player);
            assertTrue(character.isRobbed());
            assertTrue(character.isDead());
        }
        characterCards.reset();
        for (Character character : characterCards) {
            assertNull(character.getPlayer());
            assertFalse(character.isRobbed());
            assertFalse(character.isDead());
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
