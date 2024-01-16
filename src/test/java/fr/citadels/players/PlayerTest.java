package fr.citadels.players;

import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PlayerTest {

    Player player;
    Bank bank;
    Display display;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        bank = new Bank();
        display = new Display();
        player = new Player("Hello", districts) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
                return drawnCards[0];
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play(DistrictCardsPile pile, Bank bank, Display events) {
                this.chooseCharacter(new CharacterCardsList(), events);
                DistrictCard card = getHand().get(0);
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
                events.addDistrictBuilt(this, card);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters, Display events) {
                this.setCharacter(characters.get(1));
            }
        };
    }

    @Test
    void getName() {
        assertEquals("Hello", player.getName());
    }


    @Test
    void getHand() {
        assertEquals(7, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getHand().get(2).getCardName());
    }

    @Test
    void getCity() {
        DistrictCardsPile pile = new DistrictCardsPile();
        Display events = new Display();
        assertTrue(player.getCity().isEmpty());

        player.play(pile, bank, events);
        assertEquals(1, player.getCity().size());
        assertEquals("Temple", player.getCity().get(0).getCardName());

        player.play(pile, bank, events);
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(1).getCardName());

        player.play(pile, bank, events);
        assertEquals(3, player.getCity().size());
        assertEquals("Cathédrale", player.getCity().get(2).getCardName());

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
        Display events = new Display();
        while (player.getCity().size() < 7) {
            assertFalse(player.hasCompleteCity());
            player.play(pile, bank, events);
        }
        assertTrue(player.hasCompleteCity());
    }

    @Test
    void hasCardInHand() {
        player.play(new DistrictCardsPile(), bank, new Display());
        assertTrue(player.hasCardInCity(DistrictCardsPile.allDistrictCards[12]));
        assertFalse(player.hasCardInCity(DistrictCardsPile.allDistrictCards[58]));
    }

    @Test
    void getGold() {
        assertEquals(0, player.getGold());
    }


    @Test
    void takeCardsOrGold() {
        DistrictCardsPile pile = new DistrictCardsPile();
        Display events = new Display();
        pile.initializePile();

        player.takeCardsOrGold(pile, bank, false, events);
        assertEquals(7, player.getHand().size());
        assertEquals(2, player.getGold());
        // assertEquals("Hello a pris 2 pièces d'or.\nHello a 2 pièces d'or.\n", events.getEvents());
        events.reset();

        player.takeCardsOrGold(pile, bank, true, events);
        assertEquals(8, player.getHand().size());
        assertEquals(2, player.getGold());
        // assertTrue(events.getEvents().contains("Hello a choisi : " + player.getHand().get(7).getCardName()));
        events.reset();

        player.addGold(bank.take(23));
        player.takeCardsOrGold(pile, bank, false, events);
        assertEquals(9, player.getHand().size());
        assertEquals(25, player.getGold());
        // assertTrue(events.getEvents().contains("Hello a choisi : " + player.getHand().get(8).getCardName()));
        events.reset();

    }

    @Test
    void compareToTest() {
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play(DistrictCardsPile pile, Bank bank, Display events) {
                this.chooseCharacter(new CharacterCardsList(), events);
                DistrictCard card = getHand().get(0);
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
                events.addDistrictBuilt(this, card);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters, Display events) {
                this.setCharacter(characters.get(2));
            }
        };
        player.play(new DistrictCardsPile(), bank, new Display());
        player2.play(new DistrictCardsPile(), bank, new Display());

        assertTrue(player.compareTo(player2) < 0);
        assertTrue(player2.compareTo(player) > 0);
    }


    @Test
    void equals() {
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play(DistrictCardsPile pile, Bank bank, Display events) {
                this.chooseCharacter(new CharacterCardsList(), events);
                DistrictCard card = getHand().get(0);
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
                events.addDistrictBuilt(this, card);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters, Display events) {
                this.setCharacter(characters.get(2));
            }
        };
        player.play(new DistrictCardsPile(), bank, new Display());
        player2.play(new DistrictCardsPile(), bank, new Display());
        assertEquals(player, player2);
        assertEquals(player2, player);

        //test with different name
        player2 = new Player("Hello2", districts2) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play(DistrictCardsPile pile, Bank bank, Display events) {
                this.chooseCharacter(new CharacterCardsList(), events);
                DistrictCard card = getHand().get(0);
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
                events.addDistrictBuilt(this, card);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters, Display events) {
                this.setCharacter(characters.get(2));
            }
        };
        assertNotEquals(player, player2);
        assertNotEquals(player2, player);

    }

    @Test
    void takeGoldFromCity() {
        Player playerSpy = spy(player);
        //NOBLE card
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[60]))));

        CharacterCardsList characters = new CharacterCardsList();
        playerSpy.takeGoldFromCity(bank, display);

        //no character
        assertEquals(0, playerSpy.getGold());

        //choose NOBLE
        playerSpy.setCharacter(characters.get(3));

        playerSpy.takeGoldFromCity(bank, display);
        assertEquals(2, playerSpy.getGold());


        //choose RELIGIOUS
        playerSpy.setCharacter(characters.get(4));
        playerSpy.takeGoldFromCity(bank, display);
        //+1
        assertEquals(3, playerSpy.getGold());


        //choose TRADE
        playerSpy.setCharacter(characters.get(5));
        playerSpy.takeGoldFromCity(bank, display);
        //+2
        assertEquals(5, playerSpy.getGold());

        //choose MILITARY
        playerSpy.setCharacter(characters.get(7));
        playerSpy.takeGoldFromCity(bank, display);
        //+1
        assertEquals(6, playerSpy.getGold());

        //choose NEUTRAL
        playerSpy.setCharacter(characters.get(1));
        playerSpy.takeGoldFromCity(bank, display);
        //+0
        assertEquals(6, playerSpy.getGold());

    }

    @Test
    void addGold() {
        player.addGold(5);
        assertEquals(5, player.getGold());
        player.addGold(2);
        assertEquals(7, player.getGold());
    }


    @Test
    void removeGold() {
        player.addGold(5);
        assertEquals(5, player.getGold());
        player.removeGold(2);
        assertEquals(3, player.getGold());
        player.removeGold(10);
        assertEquals(0, player.getGold());

    }


}