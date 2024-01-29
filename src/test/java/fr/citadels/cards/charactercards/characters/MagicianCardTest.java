package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.Hand;
import fr.citadels.engine.Game;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagicianCardTest {

    Game game;

    KingBot player1;
    KingBot player2;
    KingBot player3;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();

        player1 = new KingBot("Tom", new ArrayList<>(), game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);
        player3 = new KingBot("Mat", new ArrayList<>(), game);

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
        Assertions.assertEquals(0, player1.getHand().size());
        player1.getCharacter().bringIntoPlay();
        Assertions.assertEquals(1, player1.getHand().size());
    }

    @Test
    void usePower() {
        player1.getInformation().setPowerToUse(1);
        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[11]));
        player1.setHand(hand1);
        player2.setHand(hand2);

        Assertions.assertEquals(hand1, player1.getHand());
        Assertions.assertEquals(hand2, player2.getHand());

        player1.getInformation().setTarget(player2.getCharacter());
        player1.getCharacter().usePower();

        Assertions.assertEquals(hand2, player1.getHand());
        Assertions.assertEquals(hand1, player2.getHand());
    }

    @Test
    void usePower2() {
        player1.getInformation().setPowerToUse(2);
        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[20], DistrictCardsPile.allDistrictCards[30]));
        player1.setHand(hand1);

        player1.getInformation().setCardsToDiscard(0);
        Assertions.assertEquals(hand1, player1.getHand());
        player1.getCharacter().usePower();
        Assertions.assertEquals(hand1, player1.getHand());
        Assertions.assertEquals(4, player1.getHand().size());

        player1.getInformation().setCardsToDiscard(2);
        player1.getCharacter().usePower();
        Assertions.assertEquals(4, player1.getHand().size());
        Assertions.assertTrue(player1.getHand().contains(DistrictCardsPile.allDistrictCards[0]));
        Assertions.assertTrue(player1.getHand().contains(DistrictCardsPile.allDistrictCards[10]));
    }

    @Test
    void getPlayerWithMostCards() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        player3.setCharacter(CharacterCardsList.allCharacterCards[4]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[11]));
        Hand hand3 = new Hand(List.of(DistrictCardsPile.allDistrictCards[20]));
        player1.setHand(hand1);
        player2.setHand(hand2);
        player3.setHand(hand3);

        Assertions.assertEquals(CharacterCardsList.allCharacterCards[3], MagicianCard.getCharacterWithMostCards());

        player1.setCharacter(CharacterCardsList.allCharacterCards[3]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[2]);

        Assertions.assertEquals(CharacterCardsList.allCharacterCards[3], MagicianCard.getCharacterWithMostCards());
    }

}
