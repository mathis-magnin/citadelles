package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Game.BANK.reset();
        player = new Player("Hello", districts) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public String play(DistrictCardsPile pile) {
                DistrictCard card = cardsInHand.get(0);
                cityCards.add(cardsInHand.get(0));
                cardsInHand.remove(0);
                return player.getName() + " played " + card.getCardName();
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                return;
            }
        };
    }

    @Test
    void getName() {
        assertEquals("Hello", player.getName());
    }


    @Test
    void getCardsInHand() {
        assertEquals(7, player.getCardsInHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getCardsInHand().get(0).getCardName());
        assertEquals("Manoir", player.getCardsInHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getCardsInHand().get(2).getCardName());
    }

    @Test
    void getCityCards() {
        DistrictCardsPile pile = new DistrictCardsPile();
        assertTrue(player.getCityCards().isEmpty());

        player.play(pile);
        assertEquals(1, player.getCityCards().size());
        assertEquals("Temple", player.getCityCards().get(0).getCardName());

        player.play(pile);
        assertEquals(2, player.getCityCards().size());
        assertEquals("Manoir", player.getCityCards().get(1).getCardName());

        player.play(pile);
        assertEquals(3, player.getCityCards().size());
        assertEquals("Cathédrale", player.getCityCards().get(2).getCardName());

    }

    @Test
    void putBack() {
        DistrictCard[] drawnCards = new DistrictCard[3];
        drawnCards[0] = DistrictCardsPile.allDistrictCards[12];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[0];
        drawnCards[2] = DistrictCardsPile.allDistrictCards[22];
        DistrictCardsPile pile = new DistrictCardsPile();
        player.putBack(drawnCards, pile, 1);
        for (int i = 0; i < drawnCards.length; i++) {
            if (i == 1) {
                assertEquals("Manoir", drawnCards[i].getCardName());
            } else {
                assertNull(drawnCards[i]);
            }
        }


    }

    @Test
    void hasCompleteCity() {
        DistrictCardsPile pile = new DistrictCardsPile();
        while (player.getCityCards().size() < 7) {
            assertFalse(player.hasCompleteCity());
            player.play(pile);
        }
        assertTrue(player.hasCompleteCity());
    }

    @Test
    void hasCardInHand() {
        player.play(new DistrictCardsPile());
        assertTrue(player.hasCardInCity(new DistrictCard("Temple", 1)));
        assertFalse(player.hasCardInCity(new DistrictCard("Donjon", 3)));
    }

    @Test
    void getGold() {
        assertEquals(0, player.getGold());
    }

    @Test
    void addGold() {
        player.addGold(2);
        assertEquals(2, player.getGold());
        player.addGold(7);
        assertEquals(9, player.getGold());
        assertThrows(IllegalArgumentException.class, () -> {
            player.addGold(17);
        });
    }

    @Test
    void pay() {
        player.addGold(10);
        player.pay(2);
        assertEquals(8, player.getGold());
        player.pay(5);
        assertEquals(3, player.getGold());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            player.pay(4);
        });
        assertEquals("Not enough money\n" + "expected : " + 4 + "\nactual : " + 3, thrown.getMessage());
        assertThrows(IllegalArgumentException.class, () -> {
            player.pay(-1);
        });
    }
}