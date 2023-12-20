package fr.citadels.cards.characters;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

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
            assertEquals("Carte personnage " + c.getCardName(), c.toString());
        }
    }

    @Test
    void testEquals() {
        assertTrue(CharacterCardsList.allCharacterCards[0].equals(CharacterCardsList.allCharacterCards[0]));
        assertFalse(CharacterCardsList.allCharacterCards[0].equals(CharacterCardsList.allCharacterCards[CharacterCardsList.allCharacterCards.length - 1]));
        assertTrue(CharacterCardsList.allCharacterCards[0].equals(new AssassinCard()));
    }

}
