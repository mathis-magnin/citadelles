package fr.citadels.players;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.citadels.engine.Game.BANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class ThriftyBotTest {
    ThriftyBot player;
    @Mock
    Random random = mock(Random.class);

    @BeforeEach
    void setUp() {
        BANK.reset();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new ThriftyBot("Hello", districts, random);
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
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[22]));
        player.cardsInHand = districts;
        int[] minCard = player.getCheapestCardInHand();
        assertEquals(2, minCard[0]);
        assertEquals(1, minCard[1]);
    }

    @Test
    void chooseCardInHand() {
        player.addGold(4);

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
        String turn = player.play(pile);

        assertEquals(1, player.getCityCards().size());
        assertEquals(2, player.getCardsInHand().size());
        assertEquals(1, player.getGold());
        assertEquals("Hello a ajouté a sa ville : Temple", turn);

        turn = player.play(pile);
        assertEquals(2, player.getCityCards().size());
        assertEquals(1, player.getCardsInHand().size());
        assertEquals(0, player.getGold());
        assertEquals("Hello a ajouté a sa ville : Manoir", turn);

        turn = player.play(pile);
        assertEquals(2, player.getCityCards().size());
        assertEquals(2, player.getCardsInHand().size());
        assertEquals(0, player.getGold());
        assertEquals("Hello n'a pas construit ce tour-ci", turn);
    }

    @Test
    void playWithGolds() {
        DistrictCardsPile pile = new DistrictCardsPile();
        pile.initializePile();
        player.addGold(25);

        String turn = player.play(pile);
        assertEquals(1, player.getCityCards().size());
        assertEquals(3, player.getCardsInHand().size());
        assertEquals(24, player.getGold());
        assertEquals("Hello a ajouté a sa ville : Temple", turn);

        turn = player.play(pile);
        assertEquals(2, player.getCityCards().size());
        assertEquals(3, player.getCardsInHand().size());
        if (player.getCardsInHand().get(2).getGoldCost() < 3) {
            assertEquals(24 - player.getCardsInHand().get(2).getGoldCost(), player.getGold());
        } else {
            assertEquals(21, player.getGold());
            assertEquals("Hello a ajouté a sa ville : Manoir", turn);
        }
    }

}