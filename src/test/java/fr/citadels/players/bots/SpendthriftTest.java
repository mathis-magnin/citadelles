package fr.citadels.players.bots;

import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.districts.City;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Hand;
import fr.citadels.players.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpendthriftTest {

    Spendthrift player;
    Spendthrift player2;
    Spendthrift player3;
    @Mock
    Random random = mock(Random.class);
    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22]));
        player = new Spendthrift("Hello", districts, game, random);
        player.setCharacter(new Assassin());
        player2 = new Spendthrift("Bob", new ArrayList<>(), game, random);
        player2.setCharacter(new Assassin());
        player3 = new Spendthrift("Tom", new ArrayList<>(), game, random);
        player3.setCharacter(new Assassin());
        players[0] = player;
        players[1] = player2;
        players[2] = player3;
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals("Cathédrale", player.getHand().get(2).getName());
        assertEquals(0, player.getCity().size());
    }

    @Test
    void getCheapestCardInHand() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[22])));
        int[] minCard = player.getCheapestCardInHand();
        assertEquals(2, minCard[0]);
        assertEquals(1, minCard[1]);
    }

    @Test
    void chooseDistrictToBuild() {
        player.setCharacter(new Assassin());

        player.getActions().addGold(4);


        player.chooseDistrictToBuild();
        player.getHand().remove(player.getMemory().getDistrictToBuild());
        assertEquals(2, player.getHand().size());
        assertEquals("Temple", player.getMemory().getDistrictToBuild().getName());

        player.chooseDistrictToBuild();
        player.getHand().remove(player.getMemory().getDistrictToBuild());
        assertEquals(1, player.getHand().size());
        assertEquals("Manoir", player.getMemory().getDistrictToBuild().getName());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertNull(player.getMemory().getDistrictToBuild());
    }

    @Test
    void chooseCardAmongDrawn() {
        game.getPile().initializePile();
        District[] drawnCards = new District[]{DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0]};
        District cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Temple", cardToPlay.getName());

        drawnCards = new District[]{DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getName());
    }

    @Test
    void playWithNoMoney() {

        player.getMemory().getPile().initializePile();
        player.playResourcesPhase();
        player.playBuildingPhase();

        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(1, player.getGold());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals(1, player.getHand().size());
        assertEquals(0, player.getGold());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(0, player.getGold());
    }

    @Test
    void playWithGolds() {
        game.getPile().initializePile();

        player.getActions().addGold(25);

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(24, player.getGold());

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple"));
        game.getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
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
        player.setCharacter(CharactersList.allCharacterCards[0]);
        when(random.nextBoolean()).thenReturn(true, false);
        player.playAsAssassin();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[5]);
        player.playAsAssassin();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[6]);
    }

    @Test
    void playAsThief() {
        player.setCharacter(CharactersList.allCharacterCards[1]);
        when(random.nextBoolean()).thenReturn(true, false);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[3]);
        assertTrue(CharactersList.allCharacterCards[3].isRobbed());
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[6]);
        assertTrue(CharactersList.allCharacterCards[3].isRobbed());

        CharactersList.allCharacterCards[3].setRobbed(false);
        CharactersList.allCharacterCards[6].setRobbed(false);
        CharactersList.allCharacterCards[3].setDead(true);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[6]);
        assertTrue(CharactersList.allCharacterCards[6].isRobbed());

        CharactersList.allCharacterCards[6].setRobbed(false);
        CharactersList.allCharacterCards[6].setDead(true);
        CharactersList.allCharacterCards[3].setDead(false);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[3]);
        assertTrue(CharactersList.allCharacterCards[3].isRobbed());
    }

    @Test
    void playAsMagician() {
        game.getPile().initializePile();

        player.setCharacter(CharactersList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[15]));
        player.setHand(hand1);

        player2.setCharacter(CharactersList.allCharacterCards[3]);
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]));
        player2.setHand(hand2);

        player3.setCharacter(CharactersList.allCharacterCards[4]);
        Hand hand3 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5]));
        player3.setHand(hand3);

        player.playAsMagician();
        assertEquals(Power.SWAP, player.getMemory().getPowerToUse());
        assertEquals(player3.getCharacter(), player.getMemory().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player.getHand());

        player.playAsMagician();
        assertEquals(Power.RECYCLE, player.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals("Manoir", player.getHand().get(2).getName());
        assertEquals(1, player.getCity().size());
        assertEquals(1, player.getMemory().getNumberCardsToDiscard());

        player.playAsMagician();
        assertEquals(Power.RECYCLE, player.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals(1, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(0).getName());
        assertEquals(4, player.getMemory().getNumberCardsToDiscard());
    }

    @Test
    void playAsKing() {
        player.setCharacter(CharactersList.allCharacterCards[3]);

        player.setGold(0);
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[5])));
        player.playAsKing();
        // The bot take 2 golds, receive 1 gold from the castle.
        // It builds the temple wich costs 1 gold.
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals("Château", player.getCity().get(0).getName());
        assertEquals("Temple", player.getCity().get(1).getName());
        assertEquals(2, player.getGold());
    }

    @Test
    void playAsBishop() {
        player.setCharacter(CharactersList.allCharacterCards[4]);

        player.setGold(0);
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[20])));
        player.playAsBishop();
        // The bot take 2 golds, receive 1 gold from the monastery.
        // It builds the temple wich costs 1 gold.
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals("Monastère", player.getCity().get(0).getName());
        assertEquals("Temple", player.getCity().get(1).getName());
        assertEquals(3, player.getGold());

        player.setGold(0);
        player.getHand().remove(1);
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[20])));
        player.playAsBishop();
        // The bot take 2 golds, receive 1 gold from the castle.
        // It cannot build because it doesn't have enough gold.
        assertEquals(1, player.getCity().size());
        assertEquals(1, player.getHand().size());
        assertEquals("Monastère", player.getCity().get(0).getName());
        assertEquals(3, player.getGold());
    }

    @Test
    void playAsMerchant() {
        player.setCharacter(CharactersList.allCharacterCards[5]);
        // Bot has in its hand a Temple that costs 1 gold.
        // Its takes 2 gold coins at the beginning of its turn and then builds the Temple.
        // At the end of its turn, as he has taken a gold due to the merchant power, he has 2 gold coins.
        player.playAsMerchant();
        assertEquals(2, player.getGold());
    }

    @Test
    void playAsArchitect() {
        player.setCharacter(CharactersList.allCharacterCards[6]);

        player.getHand().remove(0);
        player.setGold(20);
        player.playAsArchitect();
        // During its turn, the bot builds 2 districts because it has 2 in its hand.
        assertEquals(2, player.getCity().size());
    }

    @Test
    void playAsWarlord() {
        player.setCharacter(CharactersList.allCharacterCards[7]);
        player2.setCharacter(CharactersList.allCharacterCards[0]);
        player3.setCharacter(CharactersList.allCharacterCards[1]);

        player.setGold(3);
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0])));
        player2.setCity(new City(List.of(DistrictsPile.allDistrictCards[1])));

        // First, the bot takes 2 gold coins because it has a card in hand that it can build.
        // It has 1 card in hand with a manor that costs 3.
        // It builds the manor and has 2 remaining gold coins.
        // The other player with the biggest city is the assassin, with 1 district.
        // The assassin cheapest district is a manor that costs 3, so 2 for the warlord to destroy it.
        // As he has 2 gold coins, he destroys the assassin's manor, who doesn't have any district left.
        // Since he doesn't embody the king, its noble district doesn't give it gold.
        // He ends its turn with 0 gold.
        player.playAsWarlord();

        assertEquals(0, player.getGold());
        assertEquals(new City(), player2.getCity());
    }


    @Test
    void activateFactoryEffect() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[61])));
        player.getMemory().setDistrictToBuild(DistrictsPile.allDistrictCards[61]);
        player.getActions().addGold(6);
        player.getActions().build();
        assertFalse(player.chooseFactoryEffect());

        player.getActions().addGold(3);
        assertTrue(player.chooseFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3])));
        assertTrue(player.chooseLaboratoryEffect());

        player.getActions().addGold(2);
        assertFalse(player.chooseLaboratoryEffect());

        player.getHand().addAll(List.of(DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5]));
        assertTrue(player.chooseLaboratoryEffect());

        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1])));
        player.setGold(0);
        assertFalse(player.chooseLaboratoryEffect());
        player.getCity().add(DistrictsPile.allDistrictCards[0]);
        assertTrue(player.chooseLaboratoryEffect());
    }


    @Test
    void activateGraveyardEffect() {
        player.setGold(1);
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[18]))); // cost 3 and 4
        assertFalse(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[18]));

        player.setHand(new Hand(new ArrayList<>()));
        assertTrue(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[25]));

        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5]))); // cost 3 and 4

        assertTrue(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[12])); // cost 1 + 1
        assertTrue(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[30])); // cost 2 + 1
        assertFalse(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[10])); // cost 5 + 1
    }


    @AfterEach
    void resetCharacterCards() {
        CharactersList.allCharacterCards[0] = new Assassin();
        CharactersList.allCharacterCards[1] = new Thief();
        CharactersList.allCharacterCards[2] = new Magician();
        CharactersList.allCharacterCards[3] = new King();
        CharactersList.allCharacterCards[4] = new Bishop();
        CharactersList.allCharacterCards[5] = new Merchant();
        CharactersList.allCharacterCards[6] = new Architect();
        CharactersList.allCharacterCards[7] = new Warlord();
    }

}