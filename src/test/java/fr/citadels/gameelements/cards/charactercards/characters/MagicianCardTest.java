package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.cards.districtcards.Hand;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagicianCardTest {

    Game game;

    KingBot player1;
    KingBot player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();

        player1 = new KingBot("Tom", new ArrayList<>(), game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);

        for (CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            characterCard.setPlayer(null);
        }
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

    @Test
    void bringIntoPlay() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        assertEquals(0, player1.getHand().size());
        player1.getCharacter().bringIntoPlay();
        assertEquals(1, player1.getHand().size());
    }

    @Test
    void usePower() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[11]));
        player1.setHand(hand1);
        player2.setHand(hand2);

        assertEquals(hand1, player1.getHand());
        assertEquals(hand2, player2.getHand());

        player1.getInformation().setTarget(player2.getCharacter());
        player1.getCharacter().usePower();

        assertEquals(hand2, player1.getHand());
        assertEquals(hand1, player2.getHand());
    }

}
