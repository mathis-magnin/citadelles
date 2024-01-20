package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.Card;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.KingCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import fr.citadels.players.Player;
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
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new RandomBot("Hello", districts, game, random);
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

        DistrictCard card = player.chooseCardInHand();
        assertEquals(2, player.getHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[12], card);

        card = player.chooseCardInHand();
        assertEquals(1, player.getHand().size());
        assertEquals(DistrictCardsPile.allDistrictCards[0], card);

        card = player.chooseCardInHand();
        assertEquals(1, player.getHand().size());
        assertNull(card);

        /*test if the player has the card he wants in his city*/

        player.getActions().addGold(1);
        Player playerSpy = spy(player);
        when(playerSpy.hasCardInCity(any())).thenReturn(true);
        card = playerSpy.chooseCardInHand();
        assertEquals(1, playerSpy.getHand().size());
        assertNull(card);

        player.chooseCardInHand();
        card = player.chooseCardInHand();
        assertEquals(0, player.getHand().size());
        assertNull(card);

    }

    @Test
    void chooseCardAmongDrawn() {
        player.getGame().getPile().initializePile();
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
        player.play();
        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(0, player.getGold());

        game.getDisplay().reset();

        /*case 2 : takes gold and don't place*/
        when(random.nextBoolean()).thenReturn(false, true, false);
        player.play();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(2, player.getGold());

        game.getDisplay().reset();

        /*case 3 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true, true);
        player.play();

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
        when(random.nextBoolean()).thenReturn(true, false, false);
        player.play();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(0, player.getCity().size());
        assertEquals(2, player.getGold());

        game.getDisplay().reset();

        /*case 2 : doesn't take gold and place*/

        when(random.nextBoolean()).thenReturn(false, false, true);
        player.play();

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Temple\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(1, player.getGold());

        player.getGame().getDisplay().reset();

        /*case 3 : takes gold and don't place*/
        when(random.nextBoolean()).thenReturn(false, true, false);
        player.play();

        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(4, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getGame().getDisplay().reset();

        /*case 4 : takes gold and place*/
        when(random.nextBoolean()).thenReturn(true, true, true);
        player.play();

        // assertTrue(events.getEvents().contains("Hello a construit dans sa ville : Manoir\n"));

        assertEquals(3, player.getHand().size());
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getGold());

        player.getGame().getDisplay().reset();


    }

    @Test
    void playWith2GoldsCardAlreadyIn() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[13]));
        player = new RandomBot("Hello", districts, game, random);
        Player playerSpy = spy(player);

        player.getGame().getDisplay().reset();


        playerSpy.getActions().addGold(2);
        when(random.nextBoolean()).thenReturn(false, true, true);
        player.play();
        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(3, player.getGold());

        player.getGame().getDisplay().reset();
        ;

        player.play();
        // assertTrue(events.getEvents().contains("Hello n'a rien construit.\n"));

        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getCity().size());
        assertEquals(5, player.getGold());


    }

    @Test
    void chooseCharacterTest() {
        CharacterCardsList characters = new CharacterCardsList();
        when(random.nextInt(anyInt())).thenReturn(3); // king
        player.chooseCharacter(characters);
        verify(random, times(1)).nextInt(anyInt());
        assertEquals("Roi", player.getCharacter().getCardName());
        assertFalse(characters.contains(new KingCard()));
        // assertEquals("Hello a choisi le personnage : Roi\n", events.getEvents());
        player.getGame().getDisplay().reset();

        when(random.nextInt(anyInt())).thenReturn(20, 3);
        player.chooseCharacter(characters);
        verify(random, times(3)).nextInt(anyInt());


    }

    @Test
    void playAsAssassin() {
        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(3);
        player.playAsAssassin();
        assertEquals(player.getTarget(), CharacterCardsList.allCharacterCards[4]);

        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(6);
        player.playAsAssassin();
        assertEquals(player.getTarget(), CharacterCardsList.allCharacterCards[7]);

        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextInt(anyInt())).thenReturn(0);
        player.playAsAssassin();
        assertEquals(player.getTarget(), CharacterCardsList.allCharacterCards[1]);
    }

}