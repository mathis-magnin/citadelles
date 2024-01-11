package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Bank;
import fr.citadels.engine.Display;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ThriftyBotTest {

    ThriftyBot player;
    Bank bank;
    @Mock
    Random random = mock(Random.class);
    Display events = new Display();

    @BeforeEach
    void setUp() {
        bank = new Bank();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new ThriftyBot("Hello", districts, random);
        events.resetDisplay();
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getCardsInHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getCardsInHand().get(0).getCardName());
        assertEquals("Manoir", player.getCardsInHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getCardsInHand().get(2).getCardName());
        assertEquals(0, player.getCityCards().size());
    }

    @Test
    void getCheapestCardInHand() {
        player.cardsInHand = new Hand(List.of(DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[22]));
        int[] minCard = player.getCheapestCardInHand();
        assertEquals(2, minCard[0]);
        assertEquals(1, minCard[1]);
    }

    @Test
    void chooseCardInHand() {
        player.addGold(4, bank);

        DistrictCard card = player.chooseCardInHand();
        assertEquals(2, player.getCardsInHand().size());
        assertEquals("Temple", card.getCardName());

        card = player.chooseCardInHand();
        assertEquals(1, player.getCardsInHand().size());
        assertEquals("Manoir", card.getCardName());

        card = player.chooseCardInHand();
        assertEquals(1, player.getCardsInHand().size());
        assertNull(card);
    }

    @Test
    void chooseCardAmongDrawn() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        DistrictCard[] drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0]};
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(pile, drawnCards);
        assertEquals("Temple", cardToPlay.getCardName());

        drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(pile, drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());
    }

    @Test
    void playWithNoMoney() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.play(pile, bank, events);

        assertEquals(1, player.getCityCards().size());
        assertEquals(2, player.getCardsInHand().size());
        assertEquals(1, player.getGold());
        assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 2 pièces d'or.\n" +
                "Hello a construit dans sa ville : Temple\n" +
                "Hello a dans sa ville : Temple, \n", events.getEvents());
        events.resetDisplay();

        player.play(pile, bank, events);
        assertEquals(2, player.getCityCards().size());
        assertEquals(1, player.getCardsInHand().size());
        assertEquals(0, player.getGold());
        assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 3 pièces d'or.\n" +
                "Hello a construit dans sa ville : Manoir\n" +
                "Hello a dans sa ville : Temple, Manoir, \n", events.getEvents());
        events.resetDisplay();

        player.play(pile, bank, events);
        assertEquals(2, player.getCityCards().size());
        assertEquals(2, player.getCardsInHand().size());
        assertEquals(0, player.getGold());
        assertTrue(events.getEvents().contains("Hello n'a rien construit"));
        events.resetDisplay();
    }

    @Test
    void playWithGolds() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(25, bank);

        player.play(pile, bank, events);
        assertEquals(1, player.getCityCards().size());
        assertEquals(3, player.getCardsInHand().size());
        assertEquals(24, player.getGold());
        assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple"));
        events.resetDisplay();

        player.play(pile, bank, events);
        assertEquals(2, player.getCityCards().size());
        assertEquals(3, player.getCardsInHand().size());
        if (player.getCardsInHand().get(2).getGoldCost() < 3) {
            assertEquals(24 - player.getCardsInHand().get(2).getGoldCost(), player.getGold());
        } else {
            assertEquals(21, player.getGold());
            assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir"));
            events.resetDisplay();
        }
    }

}