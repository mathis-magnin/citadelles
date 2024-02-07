package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagicianTest {

    Game game;

    Monarchist player1;
    Monarchist player2;
    Monarchist player3;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];

        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        game = new Game(players, new Random());
        player1 = new Monarchist("Tom", new ArrayList<>(), game);
        player2 = new Monarchist("Bob", new ArrayList<>(), game);
        player3 = new Monarchist("Mat", new ArrayList<>(), game);
        game.getPile().initializePile();

        for (Character characterCard : CharactersList.allCharacterCards) {
            characterCard.setPlayer(null);
        }
    }

    @Test
    void getPossibleTargets() {
        player1.setCharacter(CharactersList.allCharacterCards[3]);
        player2.setCharacter(CharactersList.allCharacterCards[4]);

        CharactersList targets = Magician.getPossibleTargets();
        assertEquals(new CharactersList(new Character[]{new King(), new Bishop()}), targets);
    }

    @AfterAll
    static void resetCharacterCards() {
        CharactersList.allCharacterCards[0] = new Assassin();
        CharactersList.allCharacterCards[1] = new Thief();
        CharactersList.allCharacterCards[2] = new Magician();
        CharactersList.allCharacterCards[3] = new King();
        CharactersList.allCharacterCards[4] = new Bishop();
        CharactersList.allCharacterCards[5] = new Merchant();
        CharactersList.allCharacterCards[6] = new Architect();
        CharactersList.allCharacterCards[7] = new Warlord();
    }

    @Test
    void bringIntoPlay() {
        player1.setCharacter(CharactersList.allCharacterCards[2]);
        Assertions.assertEquals(0, player1.getHand().size());
        player1.getCharacter().bringIntoPlay();
        Assertions.assertEquals(1, player1.getHand().size());
    }

    @Test
    void usePower() {
        player1.getMemory().setPowerToUse(1);
        player1.setCharacter(CharactersList.allCharacterCards[2]);
        player2.setCharacter(CharactersList.allCharacterCards[3]);
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[11]));
        player1.setHand(hand1);
        player2.setHand(hand2);

        Assertions.assertEquals(hand1, player1.getHand());
        Assertions.assertEquals(hand2, player2.getHand());

        player1.getMemory().setTarget(player2.getCharacter());
        player1.getCharacter().usePower();

        Assertions.assertEquals(hand2, player1.getHand());
        Assertions.assertEquals(hand1, player2.getHand());
    }

    @Test
    void usePower2() {
        player1.getMemory().setPowerToUse(2);
        player1.setCharacter(CharactersList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[20], DistrictsPile.allDistrictCards[30]));
        player1.setHand(hand1);

        player1.getMemory().setCardsToDiscard(0);
        Assertions.assertEquals(hand1, player1.getHand());
        player1.getCharacter().usePower();
        Assertions.assertEquals(hand1, player1.getHand());
        Assertions.assertEquals(4, player1.getHand().size());

        player1.getMemory().setCardsToDiscard(2);
        player1.getCharacter().usePower();
        Assertions.assertEquals(4, player1.getHand().size());
        Assertions.assertTrue(player1.getHand().contains(DistrictsPile.allDistrictCards[0]));
        Assertions.assertTrue(player1.getHand().contains(DistrictsPile.allDistrictCards[10]));
    }

    @Test
    void getPlayerWithMostCards() {
        player1.setCharacter(CharactersList.allCharacterCards[2]);
        player2.setCharacter(CharactersList.allCharacterCards[3]);
        player3.setCharacter(CharactersList.allCharacterCards[4]);
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[11]));
        Hand hand3 = new Hand(List.of(DistrictsPile.allDistrictCards[20]));
        player1.setHand(hand1);
        player2.setHand(hand2);
        player3.setHand(hand3);

        Assertions.assertEquals(CharactersList.allCharacterCards[3], Magician.getCharacterWithMostCards());

        player1.setCharacter(CharactersList.allCharacterCards[3]);
        player2.setCharacter(CharactersList.allCharacterCards[2]);

        Assertions.assertEquals(CharactersList.allCharacterCards[3], Magician.getCharacterWithMostCards());
    }

}
