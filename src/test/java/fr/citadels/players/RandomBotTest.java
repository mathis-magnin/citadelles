package fr.citadels.players;

import fr.citadels.cards.Card;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.characters.KingCard;
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
import static org.mockito.Mockito.*;

class RandomBotTest {

    @Mock
    Random random = mock(Random.class);
    Player player;
    Bank bank;
    Display events = new Display();

    @BeforeEach
    void setUp() {
        bank = new Bank();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new RandomBot("Hello", districts, random);
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
    void chooseCardInHand() {

        player.addGold(4, bank);
        DistrictCard card = player.chooseCardInHand();
        assertEquals(2, player.getCardsInHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[12], card);

        card = player.chooseCardInHand();
        assertEquals(1, player.getCardsInHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[0], card);

        card = player.chooseCardInHand();
        assertEquals(1, player.getCardsInHand().size());
        assertNull(card);

        /*test if the player has the card he wants in his city*/
        player.addGold(1, bank);
        Player playerSpy = spy(player);
        when(playerSpy.hasCardInCity(any())).thenReturn(true);
        card = playerSpy.chooseCardInHand();
        assertEquals(1, playerSpy.getCardsInHand().size());
        assertNull(card);

        player.chooseCardInHand();
        card = player.chooseCardInHand();
        assertEquals(0, player.getCardsInHand().size());
        assertNull(card);

    }

    @Test
    void chooseCardAmongDrawn() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        DistrictCard[] drawnCards = pile.draw(2);
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(pile, drawnCards);
        for (Card card : drawnCards)
            if (card != null) assertEquals(cardToPlay, card);

    }

    @Test
    void playWithNoMoney() {
        /*case 1 : take card and don't place*/
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        when(random.nextBoolean()).thenReturn(false);
        player.play(pile, bank, events);
        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(0, player.getCityCards().size());
        assertEquals(0, player.getGold());
        events.resetDisplay();

        /*case 2 : takes gold and don't place*/
        when(random.nextBoolean()).thenReturn(true, false);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(0, player.getCityCards().size());
        assertEquals(2, player.getGold());
        events.resetDisplay();

        /*case 3 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple\n"));
        assertEquals(3, player.getCardsInHand().size());
        assertEquals(1, player.getCityCards().size());
        assertEquals(3, player.getGold());
        events.resetDisplay();

    }

    @Test
    void playWith2GoldsTemple() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(2, bank);

        /*case 1 : take card and don't place*/
        when(random.nextBoolean()).thenReturn(false, false);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(0, player.getCityCards().size());
        assertEquals(2, player.getGold());
        events.resetDisplay();

        /*case 2 : doesn't take gold and place*/

        when(random.nextBoolean()).thenReturn(false, true);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple\n"));
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(1, player.getCityCards().size());
        assertEquals(1, player.getGold());
        events.resetDisplay();

        /*case 3 : takes gold and don't place*/
        when(random.nextBoolean()).thenReturn(true, false);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(1, player.getCityCards().size());
        assertEquals(3, player.getGold());
        events.resetDisplay();

        /*case 4 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true);
        player.play(pile, bank, events);

        assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir\n"));
        assertEquals(3, player.getCardsInHand().size());
        assertEquals(2, player.getCityCards().size());
        assertEquals(2, player.getGold());
        events.resetDisplay();


    }

    @Test
    void playWith2GoldsCardAlreadyIn() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[13]));
        player = new RandomBot("Hello", districts, random);

        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(2, bank);
        when(random.nextBoolean()).thenReturn(true, true);
        player.play(pile, bank, events);
        assertEquals(1, player.getCardsInHand().size());
        assertEquals(1, player.getCityCards().size());
        assertEquals(3, player.getGold());
        events.resetDisplay();

        player.play(pile, bank, events);
        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        assertEquals(1, player.getCardsInHand().size());
        assertEquals(1, player.getCityCards().size());
        assertEquals(5, player.getGold());
    }

    @Test
    void playWithWrongBooleanGenerated() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        when(random.nextBoolean()).thenThrow(IllegalArgumentException.class);
        player.play(pile, bank, events);
        assertEquals(3, player.getCardsInHand().size());
        assertEquals(0, player.getCityCards().size());
        assertEquals(2, player.getGold()); //took money
        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
        events.resetDisplay();

        player.addGold(23, bank); //no money in bank anymore
        player.play(pile, bank, events);
        assertEquals(4, player.getCardsInHand().size());
        assertEquals(0, player.getCityCards().size());
        assertEquals(25, player.getGold()); //took money
        assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));
    }

    @Test
    void chooseCharacterTest() {
        CharacterCardsList characters = new CharacterCardsList();
        when(random.nextInt(anyInt())).thenReturn(3); // king
        player.chooseCharacter(characters, events);
        verify(random, times(1)).nextInt(anyInt());
        assertEquals(new KingCard(), player.getCharacter());
        assertFalse(characters.contains(new KingCard()));
        assertEquals("Hello a choisi le personnage : Roi\n", events.getEvents());
        events.resetDisplay();

        when(random.nextInt(anyInt())).thenReturn(20, 3);
        player.chooseCharacter(characters, events);
        verify(random, times(3)).nextInt(anyInt());


        //throw exception
        when(random.nextInt(anyInt())).thenThrow(IllegalStateException.class).thenReturn(3);
        player.chooseCharacter(characters, events);
        verify(random, times(5)).nextInt(anyInt());

    }

}