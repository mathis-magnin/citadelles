package fr.citadels.cards.charactercards;

import fr.citadels.cards.charactercards.characters.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersListTest {

    CharactersList characterCards = new CharactersList(CharactersList.allCharacterCards);

    @Test
    void testRemoveCharactersFaceUp() {
        assertEquals(2, characterCards.removeCharactersFaceUp().length);
    }

    @Test
    void testRemoveCharactersFaceDown() {
        assertEquals(1, characterCards.removeCharactersFaceDown().length);
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