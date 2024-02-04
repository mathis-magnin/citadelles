package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.Card;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
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

class RandomBotTest {

    @Mock
    Random random = mock(Random.class);
    RandomBot player;
    RandomBot player2;
    RandomBot player3;
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new RandomBot("Hello", districts, game, random);
        player2 = new RandomBot("Bob", new ArrayList<>(), game, random);
        player3 = new RandomBot("Bob", new ArrayList<>(), game, random);
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getHand().get(2).getCardName());
        assertEquals(0, player.getCity().size());

    }

    @Test
    void chooseCardInHand() {


        player.getActions().addGold(4);

        player.chooseDistrictToBuild();
        assertEquals(2, player.getHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[12], player.getInformation().getDistrictToBuild());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[0], player.getInformation().getDistrictToBuild());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertNull(player.getInformation().getDistrictToBuild());

        /*test if the player has the card he wants in his city*/

        player.getActions().addGold(1);
        Player playerSpy = spy(player);
        when(playerSpy.hasCardInCity(any())).thenReturn(true);
        playerSpy.chooseDistrictToBuild();
        assertEquals(1, playerSpy.getHand().size());
        assertNull(playerSpy.getInformation().getDistrictToBuild());

        player.chooseDistrictToBuild();
        player.chooseDistrictToBuild();
        assertEquals(0, player.getHand().size());
        assertNull(playerSpy.getInformation().getDistrictToBuild());

    }

    @Test
    void chooseCardAmongDrawn() {
        player.getInformation().getPile().initializePile();
        DistrictCard[] drawnCards = game.getPile().draw(2);
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(drawnCards);
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

        player.getInformation().getDisplay().reset();

        /*case 3 : takes gold and don't place*/

        when(random.nextBoolean()).thenReturn(true, false, false);
        player.playResourcesPhase();
        player.playBuildingPhase();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getInformation().getDisplay().reset();

        /*case 4 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true, true);

        player.playResourcesPhase();
        player.playBuildingPhase();


        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir\n"));

        assertEquals(3, player.getHand().size());
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getGold());

        player.getInformation().getDisplay().reset();


    }

    @Test
    void playWith2GoldsCardAlreadyIn() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[13]));
        player = new RandomBot("Hello", districts, game, random);

        player.getInformation().getDisplay().reset();


        player.getActions().addGold(2);
        when(random.nextBoolean()).thenReturn(true, false, true);
        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getInformation().getDisplay().reset();


        player.playResourcesPhase();
        player.playBuildingPhase();
        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(5, player.getGold());


    }

    @Test
    void chooseCharacterTest() {
        CharacterCardsList characters = new CharacterCardsList(CharacterCardsList.allCharacterCards);
        when(random.nextInt(anyInt())).thenReturn(3); // king
        player.chooseCharacter(characters);
        verify(random, times(1)).nextInt(anyInt());
        assertEquals("Roi", player.getCharacter().getCardName());
        assertFalse(characters.contains(new KingCard()));
        // assertEquals("Hello a choisi le personnage : Roi\n", events.getEvents());
        player.getInformation().getDisplay().reset();

        when(random.nextInt(anyInt())).thenReturn(20, 3);
        player.chooseCharacter(characters);
        verify(random, times(3)).nextInt(anyInt());


    }

    @Test
    void playAsAssassin() {
        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[4]);

        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(6);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[7]);

        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(0);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[1]);
    }

    @Test
    void playAsThief() {
        player.setCharacter(CharacterCardsList.allCharacterCards[1]);
        when(random.nextInt(anyInt())).thenReturn(0);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[2]);
        assertTrue(CharacterCardsList.allCharacterCards[2].isRobbed());
        CharacterCardsList.allCharacterCards[2].setRobbed(false);

        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[5]);
        assertTrue(CharacterCardsList.allCharacterCards[5].isRobbed());
        CharacterCardsList.allCharacterCards[5].setRobbed(false);

        when(random.nextInt(anyInt())).thenReturn(5);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[7]);
        assertTrue(CharacterCardsList.allCharacterCards[7].isRobbed());
        CharacterCardsList.allCharacterCards[7].setRobbed(false);

        CharacterCardsList.allCharacterCards[5].setDead(true);
        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
        assertTrue(CharacterCardsList.allCharacterCards[6].isRobbed());
        CharacterCardsList.allCharacterCards[6].setRobbed(false);
    }

    @Test
    void playAsMagician() {
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]));
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[11]));
        player.setCharacter(CharacterCardsList.allCharacterCards[2]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        player.setHand(hand1);
        player2.setHand(hand2);

        when(random.nextInt(anyInt())).thenReturn(0, 0, 0);
        player.playAsMagician();
        assertEquals(1, player.getInformation().getPowerToUse());
        assertEquals(CharacterCardsList.allCharacterCards[3], player.getInformation().getTarget());
        assertEquals(player.getHand(), hand2);
        assertEquals(player2.getHand(), hand1);

        when(random.nextInt(anyInt())).thenReturn(1, 2, 1);
        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(2, player.getInformation().getCardsToDiscard());
        assertEquals(2, player.getHand().size());

        when(random.nextInt(anyInt())).thenReturn(1, 10, 2);
        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(10, player.getInformation().getCardsToDiscard());
        assertEquals(2, player.getHand().size());
    }

    @Test
    void playAsMerchant() {
        player.getInformation().getPile().initializePile();
        player.setCharacter(CharacterCardsList.allCharacterCards[5]);

        // Bot takes cards and doesn't build
        // At the end of its turn, it has 1 gold due to the merchant power
        when(random.nextBoolean()).thenReturn(false, false);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

    @Test
    void playAsWarlord() {
        player.setCharacter(CharacterCardsList.allCharacterCards[7]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player3.setCharacter(CharacterCardsList.allCharacterCards[1]);

        player2.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[1])));

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
        when(random.nextBoolean()).thenReturn(true, true);
        player.setGold(0);
        assertFalse(player.activateFactoryEffect());

        player.setGold(3);
        assertTrue(player.activateFactoryEffect());

        when(random.nextBoolean()).thenReturn(false);
        player.setGold(3);
        assertFalse(player.activateFactoryEffect());
    }

    @AfterEach
    void resetCharacterCards() {
        CharacterCardsList.allCharacterCards[0] = new AssassinCard();
        CharacterCardsList.allCharacterCards[1] = new ThiefCard();
        CharacterCardsList.allCharacterCards[2] = new MagicianCard();
        CharacterCardsList.allCharacterCards[3] = new KingCard();
        CharacterCardsList.allCharacterCards[4] = new BishopCard();
        CharacterCardsList.allCharacterCards[5] = new MerchantCard();
        CharacterCardsList.allCharacterCards[6] = new ArchitectCard();
        CharacterCardsList.allCharacterCards[7] = new WarlordCard();
    }

}