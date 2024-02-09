package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
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

class ThriftyTest {
    @Mock
    Random random = mock(Random.class);
    Thrifty player;
    Thrifty player2;
    Thrifty player3;
    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, random);
        game.getPile().initializePile();
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[22]));
        player = new Thrifty("Hello", districts, game, random);
        player.setCharacter(new Assassin());
        player2 = new Thrifty("Bob", new ArrayList<>(), game, random);
        player2.setCharacter(new Assassin());
        player3 = new Thrifty("Bob", new ArrayList<>(), game, random);
        player3.setCharacter(new Assassin());
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Manoir", player.getHand().get(0).getName());
        assertEquals("Temple", player.getHand().get(1).getName());
        assertEquals("Cathédrale", player.getHand().get(2).getName());
        assertEquals(0, player.getCity().size());
    }

    @Test
    void getMostExpensiveCardInHand() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[40], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[22])));
        int[] minCard = player.getMostExpensiveCardInHand();
        assertEquals(4, minCard[0]);
        assertEquals(5, minCard[1]);
    }

    @Test
    void chooseDistrictToBuild() {

        player.getActions().addGold(4);

        player.chooseDistrictToBuild();
        assertEquals(3, player.getHand().size());
        assertNull(player.getMemory().getDistrictToBuild());

        player.getActions().addGold(1);
        player.chooseDistrictToBuild();
        assertEquals(3, player.getHand().size());
        assertEquals("Cathédrale", player.getMemory().getDistrictToBuild().getName());

    }

    @Test
    void chooseCardAmongDrawn() {
        game.getPile().initializePile();
        District[] drawnCards = new District[]{DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0]};
        District cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getName());

        drawnCards = new District[]{DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Cathédrale", cardToPlay.getName());
    }

    @Test
    void playWithNoMoney() {

        player.getMemory().getPile().initializePile();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(0, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(2, player.getGold());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(0, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(4, player.getGold());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(1, player.getGold());

    }

    @Test
    void playWithGolds() {
        game.getPile().initializePile();
        player.getActions().addGold(25);

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(20, player.getGold());
        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Cathédrale"));
        game.getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals(3, player.getHand().size());

        if (player.getHand().get(2).getGoldCost() > 3) {
            assertEquals(24 - player.getHand().get(2).getGoldCost(), player.getGold());
        } else {
            assertEquals(17, player.getGold());
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
        Hand hand3 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[6]));
        player3.setHand(hand3);

        player.playAsMagician();
        assertEquals(Power.SWAP, player.getMemory().getPowerToUse());
        assertEquals(player3.getCharacter(), player.getMemory().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player.getHand());

        player.getActions().addGold(3);
        player.playAsMagician();
        assertEquals(Power.RECYCLE, player.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(4, player.getHand().size());
        assertEquals("Château", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals("Manoir", player.getHand().get(2).getName());
        assertEquals("Manoir", player.getHand().get(3).getName());
        assertEquals(1, player.getCity().size());
        assertEquals("Château", player.getCity().get(0).getName());
        assertEquals(1, player.getMemory().getNumberCardsToDiscard());

        player.playAsMagician();
        assertEquals(Power.RECYCLE, player.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals("Manoir", player.getHand().get(2).getName());
        assertEquals(2, player.getCity().size());
        assertEquals("Château", player.getCity().get(0).getName());
        assertEquals("Manoir", player.getCity().get(1).getName());
        assertEquals(2, player.getMemory().getNumberCardsToDiscard());
    }

    @Test
    void playAsMerchant() {
        player.setCharacter(CharactersList.allCharacterCards[5]);
        // The most expensive district in the bot's hand is Cathedral, which costs 5.
        // As hit doesn't have any money, it takes 2 gold coins.
        // Then, the merchant power gives it a third gold.
        // As it doesn't have enough money to build its most expensive district, it doesn't build.
        player.playAsMerchant();
        assertEquals(3, player.getGold());
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
        assertFalse(player.chooseFactoryEffect());

        player.getActions().addGold(6);
        assertTrue(player.chooseFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3])));
        assertTrue(player.chooseLaboratoryEffect());

        player.getActions().addGold(5);
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

        assertFalse(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[12])); // cost 1 + 1
        assertFalse(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[30])); // cost 2 + 1
        assertTrue(player.chooseGraveyardEffect(DistrictsPile.allDistrictCards[10])); // cost 5 + 1
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