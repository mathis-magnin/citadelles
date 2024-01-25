package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagicianCardTest {

    Game game;

    KingBot player1;
    KingBot player2;

    @BeforeEach
    void setUp() {
        game = new Game();

        player1 = new KingBot("Tom", new ArrayList<>(), game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);
    }

    @Test
    void getPossibleTargets() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[3]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[4]);

        CharacterCardsList targets = MagicianCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new KingCard(), new BishopCard()}), targets);
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
