package fr.citadels.gameelements.cards.charactercards;

import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {


    @Test
    void isDead() {
        CharacterCard c = new AssassinCard();
        assertFalse(c.isDead());
        c.getKilled();
        assertTrue(c.isDead());
    }

    @Test
    void testGetCardName() {
        assertEquals("Assassin", CharacterCardsList.allCharacterCards[0].getCardName());
        assertNotEquals("Assassin", CharacterCardsList.allCharacterCards[CharacterCardsList.allCharacterCards.length - 1].getCardName());
    }

    @Test
    void testGetRank() {
        assertEquals(1, CharacterCardsList.allCharacterCards[0].getRank());
        assertEquals(8, CharacterCardsList.allCharacterCards[CharacterCardsList.allCharacterCards.length - 1].getRank());
    }

    @Test
    void testToString() {
        for (CharacterCard c : CharacterCardsList.allCharacterCards) {
            assertEquals(c.getCardName() + " (" + c.getRank() + " - " + c.getCardFamily() + ")", c.toString());
        }
    }

    @Test
    void testEquals() {
        assertEquals(CharacterCardsList.allCharacterCards[0], (CharacterCardsList.allCharacterCards[0]));
        assertNotEquals(CharacterCardsList.allCharacterCards[0], (CharacterCardsList.allCharacterCards[CharacterCardsList.allCharacterCards.length - 1]));
        assertEquals(CharacterCardsList.allCharacterCards[0], (new AssassinCard()));
    }

}
