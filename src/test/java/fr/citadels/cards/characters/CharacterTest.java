package fr.citadels.cards.characters;

import fr.citadels.cards.characters.roles.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {


    @Test
    void isDead() {
        Character c = new Assassin();
        assertFalse(c.isDead());
        c.setDead(true);
        assertTrue(c.isDead());
        c.setDead(false);
        assertFalse(c.isDead());
    }

    @Test
    void testGetCardName() {
        Assertions.assertEquals("Assassin", CharactersList.allCharacterCards[0].getName());
        Assertions.assertNotEquals("Assassin", CharactersList.allCharacterCards[CharactersList.allCharacterCards.length - 1].getName());
    }

    @Test
    void testGetRank() {
        assertEquals(1, CharactersList.allCharacterCards[0].getRank());
        assertEquals(8, CharactersList.allCharacterCards[CharactersList.allCharacterCards.length - 1].getRank());
    }

    @Test
    void testToString() {
        for (Character c : CharactersList.allCharacterCards) {
            assertEquals(c.getName() + " (" + c.getRank() + " - " + c.getFamily() + ")", c.toString());
        }
    }

    @Test
    void testEquals() {
        assertEquals(CharactersList.allCharacterCards[0], (CharactersList.allCharacterCards[0]));
        assertNotEquals(CharactersList.allCharacterCards[0], (CharactersList.allCharacterCards[CharactersList.allCharacterCards.length - 1]));
        assertEquals(CharactersList.allCharacterCards[0], (new Assassin()));
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
