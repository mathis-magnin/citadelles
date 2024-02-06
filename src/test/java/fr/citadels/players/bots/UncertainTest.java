package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.Card;
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
import static org.mockito.Mockito.*;

class UncertainTest {

    @Mock
    java.util.Random random = mock(java.util.Random.class);
    Uncertain player;
    Uncertain player2;
    Uncertain player3;
    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, random);
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22]));
        player = new Uncertain("Hello", districts, game, random);
        player2 = new Uncertain("Bob", new ArrayList<>(), game, random);
        player3 = new Uncertain("Bob", new ArrayList<>(), game, random);

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
    void chooseCardInHand() {


        player.getActions().addGold(4);

        player.chooseDistrictToBuild();
        assertEquals(2, player.getHand().size());
        assertEquals(DistrictsPile.allDistrictCards[12], player.getMemory().getDistrictToBuild());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertEquals(DistrictsPile.allDistrictCards[0], player.getMemory().getDistrictToBuild());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertNull(player.getMemory().getDistrictToBuild());

        /*test if the player has the card he wants in his city*/

        player.getActions().addGold(1);
        Player playerSpy = spy(player);
        when(playerSpy.hasCardInCity(any())).thenReturn(true);
        playerSpy.chooseDistrictToBuild();
        assertEquals(1, playerSpy.getHand().size());
        assertNull(playerSpy.getMemory().getDistrictToBuild());

        player.chooseDistrictToBuild();
        player.chooseDistrictToBuild();
        assertEquals(0, player.getHand().size());
        assertNull(playerSpy.getMemory().getDistrictToBuild());

    }

    @Test
    void chooseCardAmongDrawn() {
        player.getMemory().getPile().initializePile();
        District[] drawnCards = game.getPile().draw(2);
        District cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        for (Card card : drawnCards)
            if (card != null) assertEquals(cardToPlay, card);

    }

    @Test
    void playWithNoMoney() {
        /*case 1 : take card and don't place*/
        game.getPile().initializePile();

        when(random.nextBoolean()).thenReturn(false, false);

        player.playResourcesPhase();
        player.playBuildingPhase();
        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(0, player.getGold());

        game.getDisplay().reset();

        /*case 2 : takes gold and don't place*/

        when(random.nextBoolean()).thenReturn(true, false, false);
        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(2, player.getGold());

        game.getDisplay().reset();

        /*case 3 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true, true);

        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple\n"));

        assertEquals(3, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        game.getDisplay().reset();

    }

    @Test
    void playWith2GoldsTemple() {
        game.getPile().initializePile();

        player.getActions().addGold(2);


        /*case 1 : take card and don't place*/

        when(random.nextBoolean()).thenReturn(false, true, false);
        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(2, player.getGold());

        game.getDisplay().reset();

        /*case 2 : doesn't take gold and place*/

        when(random.nextBoolean()).thenReturn(false, false, true);
        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(1, player.getGold());

        player.getMemory().getDisplay().reset();

        /*case 3 : takes gold and don't place*/

        when(random.nextBoolean()).thenReturn(true, false, false);
        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getMemory().getDisplay().reset();

        /*case 4 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true, true);

        player.playResourcesPhase();
        player.playBuildingPhase();


        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir\n"));

        assertEquals(3, player.getHand().size());
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getGold());

        player.getMemory().getDisplay().reset();


    }

    @Test
    void playWith2GoldsCardAlreadyIn() {
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[13]));
        player = new Uncertain("Hello", districts, game, random);

        player.getMemory().getDisplay().reset();


        player.getActions().addGold(2);
        when(random.nextBoolean()).thenReturn(true, false, true);
        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getMemory().getDisplay().reset();


        player.playResourcesPhase();
        player.playBuildingPhase();
        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(5, player.getGold());


    }

    @Test
    void chooseCharacterTest() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        when(random.nextInt(anyInt())).thenReturn(3); // king
        player.chooseCharacter(characters);
        verify(random, times(1)).nextInt(anyInt());
        assertEquals("Roi", player.getCharacter().getName());
        assertFalse(characters.contains(new King()));
        // assertEquals("Hello a choisi le personnage : Roi\n", events.getEvents());
        player.getMemory().getDisplay().reset();

        when(random.nextInt(anyInt())).thenReturn(20, 3);
        player.chooseCharacter(characters);
        verify(random, times(3)).nextInt(anyInt());


    }

    @Test
    void playAsAssassin() {
        player.setCharacter(CharactersList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsAssassin();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[4]);

        player.setCharacter(CharactersList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(6);
        player.playAsAssassin();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[7]);

        player.setCharacter(CharactersList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(0);
        player.playAsAssassin();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[1]);
    }

    @Test
    void playAsThief() {
        player.setCharacter(CharactersList.allCharacterCards[1]);
        when(random.nextInt(anyInt())).thenReturn(0);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[2]);
        assertTrue(CharactersList.allCharacterCards[2].isRobbed());
        CharactersList.allCharacterCards[2].setRobbed(false);

        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[5]);
        assertTrue(CharactersList.allCharacterCards[5].isRobbed());
        CharactersList.allCharacterCards[5].setRobbed(false);

        when(random.nextInt(anyInt())).thenReturn(5);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[7]);
        assertTrue(CharactersList.allCharacterCards[7].isRobbed());
        CharactersList.allCharacterCards[7].setRobbed(false);

        CharactersList.allCharacterCards[5].setDead(true);
        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsThief();
        assertEquals(player.getMemory().getTarget(), CharactersList.allCharacterCards[6]);
        assertTrue(CharactersList.allCharacterCards[6].isRobbed());
        CharactersList.allCharacterCards[6].setRobbed(false);
    }

    @Test
    void playAsMagician() {
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[10], DistrictsPile.allDistrictCards[11]));
        player.setCharacter(CharactersList.allCharacterCards[2]);
        player2.setCharacter(CharactersList.allCharacterCards[3]);
        player.setHand(hand1);
        player2.setHand(hand2);

        when(random.nextInt(anyInt())).thenReturn(0, 0, 0);
        player.playAsMagician();
        assertEquals(1, player.getMemory().getPowerToUse());
        assertEquals(CharactersList.allCharacterCards[3], player.getMemory().getTarget());
        assertEquals(player.getHand(), hand2);
        assertEquals(player2.getHand(), hand1);

        when(random.nextInt(anyInt())).thenReturn(1, 2, 1);
        player.playAsMagician();
        assertEquals(2, player.getMemory().getPowerToUse());
        assertEquals(2, player.getMemory().getCardsToDiscard());
        assertEquals(2, player.getHand().size());

        when(random.nextInt(anyInt())).thenReturn(1, 10, 2);
        player.playAsMagician();
        assertEquals(2, player.getMemory().getPowerToUse());
        assertEquals(10, player.getMemory().getCardsToDiscard());
        assertEquals(2, player.getHand().size());
    }

    @Test
    void playAsMerchant() {
        player.getMemory().getPile().initializePile();
        player.setCharacter(CharactersList.allCharacterCards[5]);

        // Bot takes cards and doesn't build
        // At the end of its turn, it has 1 gold due to the merchant power
        when(random.nextBoolean()).thenReturn(false, false);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

    @Test
    void playAsWarlord() {
        player.setCharacter(CharactersList.allCharacterCards[7]);
        player2.setCharacter(CharactersList.allCharacterCards[0]);
        player3.setCharacter(CharactersList.allCharacterCards[1]);

        player2.setCity(new City(List.of(DistrictsPile.allDistrictCards[1])));

        when(random.nextBoolean()).thenReturn(true, true, false);
        when(random.nextInt(anyInt())).thenReturn(0, 0);

        // Bot takes 2 gold coins and doesn't build
        // It chooses the assassin as target, and his first district as district to destroy
        // The assassin first district is a manor that costs 3, so 2 for the warlord to destroy it.
        // As he has 2 gold coins, he destroys the assassin's manor, who doesn't have any district left.
        // He ends its turn with 0 gold.
        player.playAsWarlord();

        assertEquals(0, player.getGold());
        assertEquals(new City(), player2.getCity());
    }

    @Test
    void activateFactoryEffect() {
        player.setGold(6);
        player2.setGold(0);
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[61])));
        player.getMemory().setDistrictToBuild(DistrictsPile.allDistrictCards[61]);
        player.getActions().build();

        when(random.nextBoolean()).thenReturn(true, true);
        assertFalse(player.activateFactoryEffect());
        assertFalse(player.activateFactoryEffect());

        when(random.nextBoolean()).thenReturn(true, true);
        player.setGold(3);
        assertTrue(player.activateFactoryEffect());

        when(random.nextBoolean()).thenReturn(false, false);
        player.setGold(3);
        assertFalse(player.activateFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        when(random.nextBoolean()).thenReturn(false);
        assertFalse(player.activateLaboratoryEffect());

        when(random.nextBoolean()).thenReturn(true);
        assertTrue(player.activateLaboratoryEffect());

        player.setHand(new Hand(new ArrayList<>()));
        when(random.nextBoolean()).thenReturn(false);
        assertFalse(player.activateLaboratoryEffect());

        when(random.nextBoolean()).thenReturn(true);
        assertFalse(player.activateLaboratoryEffect());
    }

    @Test
    void activateGraveyardEffect() {
        player.setGold(1);
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5])));
        when(random.nextBoolean()).thenReturn(true, true);

        assertTrue(player.activateGraveyardEffect(DistrictsPile.allDistrictCards[10]));
        assertFalse(player.activateGraveyardEffect(DistrictsPile.allDistrictCards[5]));
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