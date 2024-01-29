package fr.citadels.cards.charactercards;

import fr.citadels.cards.charactercards.characters.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {


    @Test
    void isDead() {
        CharacterCard c = new AssassinCard();
        assertFalse(c.isDead());
        c.setDead(true);
        assertTrue(c.isDead());
        c.setDead(false);
        assertFalse(c.isDead());
    }

    @Test
    void testGetCardName() {
        Assertions.assertEquals("Assassin", CharacterCardsList.allCharacterCards[0].getCardName());
        Assertions.assertNotEquals("Assassin", CharacterCardsList.allCharacterCards[CharacterCardsList.allCharacterCards.length - 1].getCardName());
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

    @AfterEach
    void resetCharacterCards() {
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
