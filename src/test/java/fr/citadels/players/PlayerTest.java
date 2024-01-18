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
    DistrictCardsPile pile;
    Bank bank;
    Display display;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        pile = new DistrictCardsPile();
        pile.initializePile();
        bank = new Bank();
        display = new Display();
        player = new Player("Hello", districts, pile, bank, display) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return drawnCards[0];
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
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
        assertTrue(player.getCity().isEmpty());

        player.play();
        assertEquals(1, player.getCity().size());
        assertEquals("Temple", player.getCity().get(0).getCardName());

        player.play();
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(1).getCardName());

        player.play();
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
        player.putBack(drawnCards, 1);
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
        while (player.getCity().size() < 7) {
            assertFalse(player.hasCompleteCity());
            player.play();
        }
        assertTrue(player.hasCompleteCity());
    }

    @Test
    void hasCardInHand() {
        player.play();
        assertTrue(player.hasCardInCity(DistrictCardsPile.allDistrictCards[12]));
        assertFalse(player.hasCardInCity(DistrictCardsPile.allDistrictCards[58]));
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
        assertEquals("""
                Not enough money
                expected : 4
                actual : 3""", thrown.getMessage());
        assertThrows(IllegalArgumentException.class, () -> {
            player.pay(-1);
        });
    }

    @Test
    void takeCardsOrGold() {

        player.takeCardsOrGold(false);
        assertEquals(7, player.getHand().size());
        assertEquals(2, player.getGold());

        player.takeCardsOrGold(true);
        assertEquals(8, player.getHand().size());
        assertEquals(2, player.getGold());

        player.addGold(23);
        player.takeCardsOrGold(false);
        assertEquals(9, player.getHand().size());
        assertEquals(25, player.getGold());

    }

    @Test
    void compareToTest() {
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, pile, bank, display) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                this.setCharacter(characters.get(2));
            }
        };
        player.play();
        player2.play();

        assertTrue(player.compareTo(player2) < 0);
        assertTrue(player2.compareTo(player) > 0);
    }


    @Test
    void equals() {
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, pile, bank, display) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                this.setCharacter(characters.get(2));
            }
        };
        player.play();
        player2.play();
        assertEquals(player, player2);
        assertEquals(player2, player);

        //test with different name
        player2 = new Player("Hello2", districts2, pile, bank, display) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                addCardToCity(getHand().get(0));
                removeCardFromHand(0);
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                this.setCharacter(characters.get(2));
            }
        };
        assertNotEquals(player, player2);
        assertNotEquals(player2, player);

    }

    @Test
    void takeGoldFromCity(){
        Player playerSpy=spy(player);
        //NOBLE card
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0],DistrictCardsPile.allDistrictCards[5],DistrictCardsPile.allDistrictCards[12],DistrictCardsPile.allDistrictCards[25],DistrictCardsPile.allDistrictCards[30],DistrictCardsPile.allDistrictCards[45],DistrictCardsPile.allDistrictCards[60]))));

        CharacterCardsList characters = new CharacterCardsList();
        playerSpy.takeGoldFromCity();

        //no character
        assertEquals(0, playerSpy.getGold());

        //choose NOBLE
        playerSpy.setCharacter(characters.get(3));

        playerSpy.takeGoldFromCity();
        assertEquals(2, playerSpy.getGold());


        //choose RELIGIOUS
        playerSpy.setCharacter(characters.get(4));
        playerSpy.takeGoldFromCity();
        //+1
        assertEquals(3, playerSpy.getGold());


        //choose TRADE
        playerSpy.setCharacter(characters.get(5));
        playerSpy.takeGoldFromCity();
        //+2
        assertEquals(5, playerSpy.getGold());

        //choose MILITARY
        playerSpy.setCharacter(characters.get(7));
        playerSpy.takeGoldFromCity();
        //+1
        assertEquals(6, playerSpy.getGold());

        //choose NEUTRAL
        playerSpy.setCharacter(characters.get(1));
        playerSpy.takeGoldFromCity();
        //+0
        assertEquals(6, playerSpy.getGold());

    }

}