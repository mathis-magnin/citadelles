package fr.citadels.cards.characters.roles;

import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.Role;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Hand;
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

        for (Character characterCard : game.getCharactersDeck().get()) {
            characterCard.setPlayer(null);
        }
    }

    @Test
    void getPossibleTargets() {
        player1.setCharacter(game.getCharactersDeck().get(Role.KING));
        player2.setCharacter(game.getCharactersDeck().get(Role.BISHOP));

        List<Character> targets = new Magician().getPossibleTargets();
        assertEquals(List.of(new Character[]{new King(), new Bishop()}), targets);
    }

    @Test
    void bringIntoPlay() {
        player1.setCharacter(game.getCharactersDeck().get(Role.MAGICIAN));
        Assertions.assertEquals(0, player1.getHand().size());
        player1.getCharacter().bringIntoPlay();
        Assertions.assertEquals(1, player1.getHand().size());
    }

    @Test
    void usePower() {
        player1.getMemory().setPowerToUse(Power.SWAP);
        player1.setCharacter(game.getCharactersDeck().get(Role.MAGICIAN));
        player2.setCharacter(game.getCharactersDeck().get(Role.KING));
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
        player1.getMemory().setPowerToUse(Power.RECYCLE);
        player1.setCharacter(game.getCharactersDeck().get(Role.MAGICIAN));
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[20], DistrictsPile.allDistrictCards[30]));
        player1.setHand(hand1);

        player1.getMemory().setNumberCardsToDiscard(0);
        Assertions.assertEquals(hand1, player1.getHand());
        player1.getCharacter().usePower();
        Assertions.assertEquals(hand1, player1.getHand());
        Assertions.assertEquals(4, player1.getHand().size());

        player1.getMemory().setNumberCardsToDiscard(2);
        player1.getCharacter().usePower();
        Assertions.assertEquals(4, player1.getHand().size());
        Assertions.assertTrue(player1.getHand().contains(DistrictsPile.allDistrictCards[0]));
        Assertions.assertTrue(player1.getHand().contains(DistrictsPile.allDistrictCards[10]));
    }

    @Test
    void getPlayerWithMostCards() {
        player1.setCharacter(game.getCharactersDeck().get(Role.MAGICIAN));
        player2.setCharacter(game.getCharactersDeck().get(Role.KING));
        player3.setCharacter(game.getCharactersDeck().get(Role.BISHOP));
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[11]));
        Hand hand3 = new Hand(List.of(DistrictsPile.allDistrictCards[20]));
        player1.setHand(hand1);
        player2.setHand(hand2);
        player3.setHand(hand3);

        Assertions.assertEquals(game.getCharactersDeck().get(Role.KING), player1.getCharacterWithMostCards());

        player1.setCharacter(game.getCharactersDeck().get(Role.KING));
        player2.setCharacter(game.getCharactersDeck().get(Role.MAGICIAN));

        Assertions.assertEquals(game.getCharactersDeck().get(Role.KING), player2.getCharacterWithMostCards());
    }

}
