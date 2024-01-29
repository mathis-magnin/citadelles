package fr.citadels.gameelements.cards.charactercards;

import fr.citadels.gameelements.cards.charactercards.characters.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterCardsListTest {

    CharacterCardsList characterCards = new CharacterCardsList(CharacterCardsList.allCharacterCards);

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
