package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.unique.Unique;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.Hand;
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

class SpendthriftBotTest {

    SpendthriftBot player;
    SpendthriftBot player2;
    SpendthriftBot player3;
    @Mock
    Random random = mock(Random.class);
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new SpendthriftBot("Hello", districts, game, random);
        player2 = new SpendthriftBot("Bob", new ArrayList<>(), game, random);
        player3 = new SpendthriftBot("Tom", new ArrayList<>(), game, random);
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
    void getCheapestCardInHand() {
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[22])));
        int[] minCard = player.getCheapestCardInHand();
        assertEquals(2, minCard[0]);
        assertEquals(1, minCard[1]);
    }

    @Test
    void chooseCardInHand() {

        player.getActions().addGold(4);


        player.chooseDistrictToBuild();
        assertEquals(2, player.getHand().size());
        assertEquals("Temple", player.getInformation().getDistrictToBuild().getCardName());
        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertEquals("Manoir", player.getInformation().getDistrictToBuild().getCardName());

        player.chooseDistrictToBuild();
        assertEquals(1, player.getHand().size());
        assertNull(player.getInformation().getDistrictToBuild());
    }

    @Test
    void chooseCardAmongDrawn() {
        game.getPile().initializePile();
        DistrictCard[] drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0]};
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Temple", cardToPlay.getCardName());

        drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());
    }

    @Test
    void playWithNoMoney() {

        player.getInformation().getPile().initializePile();
        player.playResourcesPhase();
        player.playBuildingPhase();


        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(1, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 2 pièces d'or.\n" +
                "Hello a construit dans sa ville : Temple\n" +
                "Hello a dans sa ville : Temple, \n", events.getEvents());*/

        player.getInformation().getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals(1, player.getHand().size());
        assertEquals(0, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 3 pièces d'or.\n" +
                "Hello a construit dans sa ville : Manoir\n" +
                "Hello a dans sa ville : Temple, Manoir, \n", events.getEvents());*/
        game.getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(0, player.getGold());
        // assertTrue(events.getEvents().contains("Hello n'a rien construit"));
        game.getDisplay().reset();
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
        player.setCharacter(CharacterCardsList.allCharacterCards[0]);
        when(random.nextBoolean()).thenReturn(true, false);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[5]);
        player.playAsAssassin();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
    }

    @Test
    void playAsThief() {
        player.setCharacter(CharacterCardsList.allCharacterCards[1]);
        when(random.nextBoolean()).thenReturn(true, false);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[3]);
        assertTrue(CharacterCardsList.allCharacterCards[3].isRobbed());
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
        assertTrue(CharacterCardsList.allCharacterCards[3].isRobbed());

        CharacterCardsList.allCharacterCards[3].setRobbed(false);
        CharacterCardsList.allCharacterCards[6].setRobbed(false);
        CharacterCardsList.allCharacterCards[3].setDead(true);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
        assertTrue(CharacterCardsList.allCharacterCards[6].isRobbed());

        CharacterCardsList.allCharacterCards[6].setRobbed(false);
        CharacterCardsList.allCharacterCards[6].setDead(true);
        CharacterCardsList.allCharacterCards[3].setDead(false);
        player.playAsThief();
        assertEquals(player.getInformation().getTarget(), CharacterCardsList.allCharacterCards[3]);
        assertTrue(CharacterCardsList.allCharacterCards[3].isRobbed());
    }

    @Test
    void playAsMagician() {
        game.getPile().initializePile();

        player.setCharacter(CharacterCardsList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[15]));
        player.setHand(hand1);

        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]));
        player2.setHand(hand2);

        player3.setCharacter(CharacterCardsList.allCharacterCards[4]);
        Hand hand3 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[3], DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5]));
        player3.setHand(hand3);

        player.playAsMagician();
        assertEquals(1, player.getInformation().getPowerToUse());
        assertEquals(player3.getCharacter(), player.getInformation().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player.getHand());

        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Manoir", player.getHand().get(2).getCardName());
        assertEquals(1, player.getCity().size());
        assertEquals(1, player.getInformation().getCardsToDiscard());

        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(2, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(0).getCardName());
        assertEquals("Manoir", player.getCity().get(1).getCardName());
        assertEquals(4, player.getInformation().getCardsToDiscard());
    }

    @Test
    void playAsMerchant() {
        player.setCharacter(CharacterCardsList.allCharacterCards[5]);
        // Bot has in its hand a Temple that costs 1 gold.
        // Its takes 2 gold coins at the beginning of its turn and then builds the Temple.
        // At the end of its turn, as he has taken a gold due to the merchant power, he has 2 gold coins.
        player.playAsMerchant();
        assertEquals(2, player.getGold());
    }

    @Test
    void playAsWarlord() {
        player.setCharacter(CharacterCardsList.allCharacterCards[7]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player3.setCharacter(CharacterCardsList.allCharacterCards[1]);

        player.setGold(3);
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[0])));
        player2.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[1])));

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
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[61])));
        player.getInformation().setDistrictToBuild(DistrictCardsPile.allDistrictCards[61]);
        player.getActions().addGold(6);
        player.getActions().build();
        assertFalse(player.activateFactoryEffect());

        player.getActions().addGold(3);
        assertTrue(player.activateFactoryEffect());
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