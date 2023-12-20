package fr.citadels.players;

import fr.citadels.cards.Card;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static fr.citadels.engine.Game.BANK;
import static org.junit.jupiter.api.Assertions.*;

class BotFirstStrategyTest {

    Player player;

    @BeforeEach
    void setUp() {
        BANK.reset();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new BotFirstStrategy("Hello", districts);
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
        player.addGold(5);
        List<DistrictCard> copy = player.getCardsInHand();
        DistrictCard card = player.chooseCardInHand();
        assertEquals(2, player.getCardsInHand().size());
        assertTrue(copy.contains(card));

        copy = player.getCardsInHand();
        card = player.chooseCardInHand();
        assertTrue(player.getCardsInHand().size() == 1 || player.getCardsInHand().size() == 2);
        if (player.getCardsInHand().size() == 1)
            assertTrue(copy.contains(card));
        else
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
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        String turn = player.play(pile);
        assertTrue(player.getCityCards().isEmpty() || player.getCityCards().get(0).equals(new DistrictCard("Temple", 1)));
        if (player.getCityCards().isEmpty()) {
            assertTrue(player.getCardsInHand().size() == 3 || player.getCardsInHand().size() == 4);
            assertTrue(player.getGold() == 2 || player.getGold() == 0);
            assertEquals("Hello n'a pas construit ce tour-ci", turn);
        } else {
            assertEquals(2, player.getCardsInHand().size());
            assertEquals(1, player.getGold());
            assertEquals("Hello a ajouté a sa ville : Temple", turn);
        }
    }

    @Test
    void playWith2GoldsTemple() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(2);
        List<DistrictCard> copy = player.getCardsInHand();
        String turn = player.play(pile);
        assertTrue(player.getCityCards().isEmpty() || copy.contains(player.getCityCards().get(0)));
        if (player.getCityCards().isEmpty()) {
            assertTrue(player.getCardsInHand().size() == 3 || player.getCardsInHand().size() == 4);
            assertTrue(player.getGold() == 2 || player.getGold() == 4);
            assertEquals("Hello n'a pas construit ce tour-ci", turn);
        } else {
            assertTrue(1 == player.getGold() || 3 == player.getGold());
            assertTrue(2 == player.getCardsInHand().size() || 3 == player.getCardsInHand().size());
            assertEquals("Hello a ajouté a sa ville : Temple", turn);

        }
    }

    @Test
    void playWith2GoldsManoir() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new BotFirstStrategy("Hello", districts);

        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(2);
        List<DistrictCard> copy = player.getCardsInHand();
        String turn = player.play(pile);
        assertTrue(player.getCityCards().isEmpty() || copy.contains(player.getCityCards().get(0)));
        if (player.getCityCards().isEmpty()) {
            assertTrue(player.getCardsInHand().size() == 2 || player.getCardsInHand().size() == 3);
            assertTrue(player.getGold() == 2 || player.getGold() == 4);
        } else {
            assertEquals(1, player.getGold());
            assertTrue(player.getCardsInHand().size() == 1 || player.getCardsInHand().size() == 2);
            assertEquals("Hello a ajouté a sa ville : Manoir", turn);

        }
    }


}