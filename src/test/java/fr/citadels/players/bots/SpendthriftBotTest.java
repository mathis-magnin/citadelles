package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import fr.citadels.gameelements.cards.districtcards.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpendthriftBotTest {

    SpendthriftBot player;
    @Mock
    Random random = mock(Random.class);
    Game game = new Game();

    @BeforeEach
    void setUp() {

        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new SpendthriftBot("Hello", districts, game, random);
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getHand().get(2).getCardName());
        assertEquals(0, player.getCity().size());
    }

    @Test
    void getCheapestCardInHand() {
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[22])));
        int[] minCard = player.getCheapestCardInHand();
        assertEquals(2, minCard[0]);
        assertEquals(1, minCard[1]);
    }

    @Test
    void chooseCardInHand() {

        player.getActions().addGold(4);


        DistrictCard card = player.chooseCardInHand();
        assertEquals(2, player.getHand().size());
        assertEquals("Temple", card.getCardName());
        card = player.chooseCardInHand();
        assertEquals(1, player.getHand().size());
        assertEquals("Manoir", card.getCardName());

        card = player.chooseCardInHand();
        assertEquals(1, player.getHand().size());
        assertNull(card);
    }

    @Test
    void chooseCardAmongDrawn() {
        game.getPile().initializePile();
        DistrictCard[] drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0]};
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Temple", cardToPlay.getCardName());

        drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());
    }

    @Test
    void playWithNoMoney() {
        game.getPile().initializePile();
        player.play();

        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(1, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 2 pièces d'or.\n" +
                "Hello a construit dans sa ville : Temple\n" +
                "Hello a dans sa ville : Temple, \n", events.getEvents());*/
        game.getDisplay().reset();
        player.play();
        assertEquals(2, player.getCity().size());
        assertEquals(1, player.getHand().size());
        assertEquals(0, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 3 pièces d'or.\n" +
                "Hello a construit dans sa ville : Manoir\n" +
                "Hello a dans sa ville : Temple, Manoir, \n", events.getEvents());*/
        game.getDisplay().reset();

        player.play();
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(0, player.getGold());
        // assertTrue(events.getEvents().contains("Hello n'a rien construit"));
        game.getDisplay().reset();
    }

    @Test
    void playWithGolds() {
        game.getPile().initializePile();

        player.getActions().addGold(25);

        player.play();
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(24, player.getGold());

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple"));
        game.getDisplay().reset();

        player.play();
        assertEquals(2, player.getCity().size());
        assertEquals(3, player.getHand().size());
        if (player.getHand().get(2).getGoldCost() < 3) {
            assertEquals(24 - player.getHand().get(2).getGoldCost(), player.getGold());
        } else {
            assertEquals(21, player.getGold());
            // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir"));
            game.getDisplay().reset();
        }
    }

    @Test
    void playAsAssassin() {
        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextBoolean()).thenReturn(true, false);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[5]);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
    }

}