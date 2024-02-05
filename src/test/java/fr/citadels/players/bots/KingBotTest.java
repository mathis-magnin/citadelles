package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.Hand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class KingBotTest {

    KingBot player1;
    KingBot player2;
    KingBot player3;

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player1 = new KingBot("Hello1", districts, game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);
        player3 = new KingBot("Tom", new ArrayList<>(), game);
    }

    @Test
    void createBot() {
        assertEquals("Hello1", player1.getName());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getHand().get(2).getCardName());
    }


    @Test
    void chooseCardAmongDrawn() {

        //two NOBLE cards
        DistrictCard[] drawnCards = new DistrictCard[2];
        drawnCards[0] = DistrictCardsPile.allDistrictCards[0];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[5];
        DistrictCard cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());
        assertEquals(1, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getCardName());

        //two cards, one NOBLE
        drawnCards[0] = DistrictCardsPile.allDistrictCards[30];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[6];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Château", cardToPlay.getCardName());
        assertEquals(2, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getCardName());
        assertEquals("Échoppe", game.getPile().get(1).getCardName());

        //two cards, no NOBLE
        drawnCards[0] = DistrictCardsPile.allDistrictCards[31];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[65];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Échoppe", cardToPlay.getCardName());
        assertEquals(3, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getCardName());
        assertEquals("Échoppe", game.getPile().get(1).getCardName());
        assertEquals("Université", game.getPile().get(2).getCardName());

    }

    @Test
    void chooseCardInHand() {
        //no money
        player1.chooseDistrictToBuild();
        assertNull(player1.getInformation().getDistrictToBuild());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getHand().get(2).getCardName());

        //Manoir

        player1.getActions().addGold(3);

        player1.chooseDistrictToBuild();
        assertEquals("Manoir", player1.getInformation().getDistrictToBuild().getCardName());
        assertEquals(2, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getHand().get(1).getCardName());
    }

    @Test
    void play() {
        this.player1.setCharacter(new KingCard());
        game.getPile().initializePile();

        //take gold because no money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(1, player1.getGold());
        assertEquals(2, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());

        //take cards because money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(1, player1.getGold()); // 1+2-3+1(gold from family)
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());

        //take money because money needed
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(4, player1.getGold());
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());

        //take cards because money needed
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(2, player1.getGold()); // 4+2-5+1
        assertEquals(0, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());
        assertEquals("Cathédrale", player1.getCity().get(2).getCardName());

        //take cards because no money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(3, player1.getGold());
        assertEquals(1, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());
        assertEquals("Cathédrale", player1.getCity().get(2).getCardName());
    }

    @Test
    void chooseCharacter() {
        CharacterCardsList characters = new CharacterCardsList(CharacterCardsList.allCharacterCards);
        player1.chooseCharacter(characters);
        assertEquals("Roi", player1.getCharacter().getCardName());
        assertEquals(7, characters.size());

        player1.chooseCharacter(characters);
        assertEquals("Assassin", player1.getCharacter().getCardName());
        assertEquals(6, characters.size());
    }

    @Test
    void playAsAssassin() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player1.playAsAssassin();
        assertEquals(player1.getInformation().getTarget(), CharacterCardsList.allCharacterCards[3]);
    }

    @Test
    void playAsThief() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[1]);
        player1.playAsThief();
        assertEquals(player1.getInformation().getTarget(), CharacterCardsList.allCharacterCards[3]);
        assertTrue(CharacterCardsList.allCharacterCards[3].isRobbed());

        CharacterCardsList.allCharacterCards[3].setRobbed(false);
        CharacterCardsList.allCharacterCards[3].setDead(true);
        player1.playAsThief();
        assertEquals(player1.getInformation().getTarget(), CharacterCardsList.allCharacterCards[6]);
        assertTrue(CharacterCardsList.allCharacterCards[6].isRobbed());
    }

    @Test
    void playAsMagician() {
        game.getPile().initializePile();

        player1.setCharacter(CharacterCardsList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictCardsPile.allDistrictCards[15]));
        player1.setHand(hand1);

        player2.setCharacter(CharacterCardsList.allCharacterCards[3]);
        Hand hand2 = new Hand(List.of(DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[2]));
        player2.setHand(hand2);

        player3.setCharacter(CharacterCardsList.allCharacterCards[4]);
        Hand hand3 = new Hand(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[3], DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[1]));
        player3.setHand(hand3);

        player1.playAsMagician();
        assertEquals(1, player1.getInformation().getPowerToUse());
        assertEquals(player3.getCharacter(), player1.getInformation().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player1.getHand());

        player1.getActions().addGold(3);
        player1.playAsMagician();
        assertEquals(2, player1.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(4, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Manoir", player1.getHand().get(1).getCardName());
        assertEquals("Manoir", player1.getHand().get(2).getCardName());
        assertEquals("Manoir", player1.getHand().get(3).getCardName());

        assertEquals(1, player1.getCity().size());
        assertEquals("Manoir", player1.getCity().get(0).getCardName());
        assertEquals(1, player1.getInformation().getCardsToDiscard());

        player1.playAsMagician();
        assertEquals(2, player1.getInformation().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Manoir", player1.getHand().get(1).getCardName());
        assertEquals("Château", player1.getHand().get(2).getCardName());
        assertEquals(2, player1.getCity().size());
        assertEquals("Manoir", player1.getCity().get(0).getCardName());
        assertEquals("Château", player1.getCity().get(1).getCardName());
        assertEquals(5, player1.getInformation().getCardsToDiscard());
    }

    @Test
    void playAsMerchant() {
        player1.getInformation().getPile().initializePile();
        player1.setCharacter(CharacterCardsList.allCharacterCards[5]);

        // First, the bot takes 2 gold coins because it hasn't money.
        // It has 3 cards in hand with a manor that is its only noble district and costs 3.
        // Finally, as it has 3 gold coins with the merchant power, it builds it.
        // Since he doesn't embody the king, its noble district doesn't give it gold.
        // He ends its turn with 0 gold.
        player1.playAsMerchant();
        assertEquals(0, player1.getGold());
    }

    @Test
    void playAsWarlord() {
        player1.setCharacter(CharacterCardsList.allCharacterCards[7]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player3.setCharacter(CharacterCardsList.allCharacterCards[1]);

        player1.setGold(3);
        player1.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[0])));
        player2.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[1])));

        // First, the bot takes 2 gold coins because it has a card in hand that it can build.
        // It has 1 card in hand with a manor that costs 3.
        // It builds the manor and has 2 remaining gold coins.
        // The other player with the biggest city is the assassin, with 1 district.
        // The assassin cheapest district is a manor that costs 3, so 2 for the warlord to destroy it.
        // As he has 2 gold coins, he destroys the assassin's manor, who doesn't have any district left.
        // Since he doesn't embody the king, its noble district doesn't give it gold.
        // He ends its turn with 0 gold.
        player1.playAsWarlord();

        assertEquals(0, player1.getGold());
        assertEquals(new City(), player2.getCity());
    }

    @Test
    void activateFactoryEffect() {
        player1.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[61])));
        player1.getInformation().setDistrictToBuild(DistrictCardsPile.allDistrictCards[61]);
        player1.getActions().addGold(6);
        player1.getActions().build();
        assertFalse(player1.activateFactoryEffect());

        player1.getActions().addGold(3);
        assertTrue(player1.activateFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        player1.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2], DistrictCardsPile.allDistrictCards[3])));
        assertTrue(player1.activateLaboratoryEffect());

        player1.getActions().addGold(2);
        assertFalse(player1.activateLaboratoryEffect());

        player1.getHand().addAll(List.of(DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5]));
        assertTrue(player1.activateLaboratoryEffect());

        player1.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[1])));
        player1.setGold(0);
        assertFalse(player1.activateLaboratoryEffect());
        player1.getCity().add(DistrictCardsPile.allDistrictCards[0]);
        assertTrue(player1.activateLaboratoryEffect());
    }

    @Test
    void activateGraveyardEffect() {
        player1.setGold(1);
        player1.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[5])));

        assertTrue(player1.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[10]));
        assertFalse(player1.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[5]));
        assertFalse(player1.activateGraveyardEffect(DistrictCardsPile.allDistrictCards[15]));
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