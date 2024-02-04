package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.uniques.Unique;
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

class ThriftyBotTest {
    @Mock
    Random random = mock(Random.class);
    ThriftyBot player;
    ThriftyBot player2;
    ThriftyBot player3;
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player = new ThriftyBot("Hello", districts, game, random);
        player2 = new ThriftyBot("Bob", new ArrayList<>(), game, random);
        player3 = new ThriftyBot("Bob", new ArrayList<>(), game, random);
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
    void getMostExpensiveCardInHand() {
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[22])));
        int[] minCard = player.getMostExpensiveCardInHand();
        assertEquals(4, minCard[0]);
        assertEquals(5, minCard[1]);
    }

    @Test
    void chooseCardInHand() {

        player.getActions().addGold(4);

        player.chooseDistrictToBuild();
        assertEquals(2, player.getHand().size());
        assertEquals("Manoir", player.getInformation().getDistrictToBuild().getCardName());
        player.chooseDistrictToBuild();
        assertEquals(2, player.getHand().size());

        assertNull(player.getInformation().getDistrictToBuild());
    }

    @Test
    void chooseCardAmongDrawn() {
        game.getPile().initializePile();
        DistrictCard[] drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0]};
        DistrictCard cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());

        drawnCards = new DistrictCard[]{DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[0]};
        cardToPlay = player.chooseCardAmongDrawn(drawnCards);
        assertEquals("Cathédrale", cardToPlay.getCardName());
    }

    @Test
    void playWithNoMoney() {

        player.getInformation().getPile().initializePile();
        player.playResourcesPhase();
        player.playBuildingPhase();


        assertEquals(0, player.getCity().size());
        assertEquals(3, player.getHand().size());
        assertEquals(2, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 2 pièces d'or.\n" +
                "Hello n'a rien construit.\n" +
                "Hello a dans sa ville : \n", events.getEvents());*/
        game.getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());

        assertEquals(1, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 4 pièces d'or.\n" +
                "Hello a construit dans sa ville : Manoir\n" +
                "Hello a dans sa ville : Manoir, \n", events.getEvents());*/
        game.getDisplay().reset();

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals(2, player.getHand().size());
        assertEquals(3, player.getGold());
        /*assertEquals("Hello a pris 2 pièces d'or.\n" +
                "Hello a 3 pièces d'or.\n" +
                "Hello n'a rien construit.\n" +
                "Hello a dans sa ville : Manoir, \n", events.getEvents());*/
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
        Hand hand3 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[3], DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[6]));
        player3.setHand(hand3);

        player.playAsMagician();
        assertEquals(1, player.getInformation().getPowerToUse());
        assertEquals(player3.getCharacter(), player.getInformation().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player.getHand());

        player.getActions().addGold(3);
        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(4, player.getHand().size());
        assertEquals("Château", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Manoir", player.getHand().get(2).getCardName());
        assertEquals("Manoir", player.getHand().get(3).getCardName());
        assertEquals(1, player.getCity().size());
        assertEquals("Château", player.getCity().get(0).getCardName());
        assertEquals(1, player.getInformation().getCardsToDiscard());

        player.playAsMagician();
        assertEquals(2, player.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player.getHand().size());
        assertEquals("Manoir", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Manoir", player.getHand().get(2).getCardName());
        assertEquals(2, player.getCity().size());
        assertEquals("Château", player.getCity().get(0).getCardName());
        assertEquals("Manoir", player.getCity().get(1).getCardName());
        assertEquals(2, player.getInformation().getCardsToDiscard());
    }

    @Test
    void playAsMerchant() {
        player.setCharacter(CharacterCardsList.allCharacterCards[5]);
        // The most expensive district in the bot's hand is Cathedral, which costs 5.
        // As hit doesn't have any money, it takes 2 gold coins.
        // Then, the merchant power gives it a third gold.
        // As it doesn't have enough money to build its most expensive district, it doesn't build.
        player.playAsMerchant();
        assertEquals(3, player.getGold());
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
        assertFalse(player.activateFactoryEffect());

        player.getActions().addGold(6);
        assertTrue(player.activateFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2], DistrictCardsPile.allDistrictCards[3])));
        assertTrue(player.activateLaboratoryEffect());

        player.getActions().addGold(5);
        assertFalse(player.activateLaboratoryEffect());

        player.getHand().addAll(List.of(DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5]));
        assertTrue(player.activateLaboratoryEffect());

        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[1])));
        player.setGold(0);
        assertFalse(player.activateLaboratoryEffect());
        player.getCity().add(DistrictCardsPile.allDistrictCards[0]);
        assertTrue(player.activateLaboratoryEffect());
    }

    @Test
    void activateGraveyardEffect() {
        player.setGold(1);
        player.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[18]))); // cost 3 and 4
        assertFalse(player.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[18]));

        player.setHand(new Hand(new ArrayList<>()));
        assertTrue(player.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[25]));

        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[5]))); // cost 3 and 4

        assertFalse(player.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[12])); // cost 1 + 1
        assertFalse(player.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[30])); // cost 2 + 1
        assertTrue(player.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[10])); // cost 5 + 1
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