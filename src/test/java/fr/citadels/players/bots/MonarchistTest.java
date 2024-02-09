package fr.citadels.players.bots;

import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.districts.City;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.cards.districts.Hand;
import fr.citadels.players.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MonarchistTest {

    Monarchist player1;
    Monarchist player2;
    Monarchist player3;

    Game game;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22]));
        player1 = new Monarchist("Hello1", districts, game);
        player2 = new Monarchist("Bob", new ArrayList<>(), game);
        player3 = new Monarchist("Tom", new ArrayList<>(), game);
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
    }

    @Test
    void createBot() {
        assertEquals("Hello1", player1.getName());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getName());
        assertEquals("Cathédrale", player1.getHand().get(1).getName());
        assertEquals("Temple", player1.getHand().get(2).getName());
    }


    @Test
    void chooseCardAmongDrawn() {

        //two NOBLE cards
        District[] drawnCards = new District[2];
        drawnCards[0] = DistrictsPile.allDistrictCards[0];
        drawnCards[1] = DistrictsPile.allDistrictCards[5];
        District cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getName());
        assertEquals(1, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getName());

        //two cards, one NOBLE
        drawnCards[0] = DistrictsPile.allDistrictCards[30];
        drawnCards[1] = DistrictsPile.allDistrictCards[6];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Château", cardToPlay.getName());
        assertEquals(2, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getName());
        assertEquals("Échoppe", game.getPile().get(1).getName());

        //two cards, no NOBLE
        drawnCards[0] = DistrictsPile.allDistrictCards[31];
        drawnCards[1] = DistrictsPile.allDistrictCards[65];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Échoppe", cardToPlay.getName());
        assertEquals(3, game.getPile().size());
        assertEquals("Château", game.getPile().get(0).getName());
        assertEquals("Échoppe", game.getPile().get(1).getName());
        assertEquals("Université", game.getPile().get(2).getName());

    }

    @Test
    void chooseCardInHand() {
        //no money
        player1.chooseDistrictToBuild();
        assertNull(player1.getMemory().getDistrictToBuild());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getName());
        assertEquals("Cathédrale", player1.getHand().get(1).getName());
        assertEquals("Temple", player1.getHand().get(2).getName());

        //Manoir

        player1.getActions().addGold(3);

        player1.chooseDistrictToBuild();
        assertEquals("Manoir", player1.getMemory().getDistrictToBuild().getName());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getName());
        assertEquals("Cathédrale", player1.getHand().get(1).getName());
        assertEquals("Temple", player1.getHand().get(2).getName());
    }

    @Test
    void play() {
        this.player1.setCharacter(new King());
        game.getPile().initializePile();

        //take gold because no money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(1, player1.getGold());
        assertEquals(2, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getName());
        assertEquals("Cathédrale", player1.getHand().get(1).getName());
        assertEquals("Temple", player1.getCity().get(0).getName());

        //take cards because money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(0, player1.getGold()); // 1+2-3
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getName());
        assertEquals("Temple", player1.getCity().get(0).getName());
        assertEquals("Manoir", player1.getCity().get(1).getName());

        //take money because money needed
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(2, player1.getGold());
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getName());
        assertEquals("Temple", player1.getCity().get(0).getName());
        assertEquals("Manoir", player1.getCity().get(1).getName());

        //take cards because money needed
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(4, player1.getGold()); // 2+2
        assertEquals(1, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getName());
        assertEquals("Manoir", player1.getCity().get(1).getName());

        //take cards because no money
        player1.playResourcesPhase();
        player1.playBuildingPhase();
        assertEquals(1, player1.getGold());
        assertEquals(0, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getName());
        assertEquals("Manoir", player1.getCity().get(1).getName());
        assertEquals("Cathédrale", player1.getCity().get(2).getName());
    }

    @Test
    void chooseCharacter() {
        CharactersList characters = new CharactersList(CharactersList.allCharacterCards);
        player1.chooseCharacter(characters);
        assertEquals("Roi", player1.getCharacter().getName());
        assertEquals(7, characters.size());

        player1.chooseCharacter(characters);
        assertEquals("Assassin", player1.getCharacter().getName());
        assertEquals(characters, player1.getMemory().getPossibleCharacters());
        assertEquals(6, characters.size());
    }

    @Test
    void playAsAssassin() {
        player1.setCharacter(CharactersList.allCharacterCards[0]);
        player1.playAsAssassin();
        assertEquals(player1.getMemory().getTarget(), CharactersList.allCharacterCards[3]);
    }

    @Test
    void playAsThief() {
        player1.setCharacter(CharactersList.allCharacterCards[1]);
        player1.playAsThief();
        assertEquals(player1.getMemory().getTarget(), CharactersList.allCharacterCards[3]);
        assertTrue(CharactersList.allCharacterCards[3].isRobbed());

        CharactersList.allCharacterCards[3].setRobbed(false);
        CharactersList.allCharacterCards[3].setDead(true);
        player1.playAsThief();
        assertEquals(player1.getMemory().getTarget(), CharactersList.allCharacterCards[6]);
        assertTrue(CharactersList.allCharacterCards[6].isRobbed());
    }

    @Test
    void playAsMagician() {
        game.getPile().initializePile();

        player1.setCharacter(CharactersList.allCharacterCards[2]);
        Hand hand1 = new Hand(List.of(DistrictsPile.allDistrictCards[15]));
        player1.setHand(hand1);

        player2.setCharacter(CharactersList.allCharacterCards[3]);
        Hand hand2 = new Hand(List.of(DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[2]));
        player2.setHand(hand2);

        player3.setCharacter(CharactersList.allCharacterCards[4]);
        Hand hand3 = new Hand(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[3], DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[1]));
        player3.setHand(hand3);

        player1.playAsMagician();
        assertEquals(Power.SWAP, player1.getMemory().getPowerToUse());
        assertEquals(player3.getCharacter(), player1.getMemory().getTarget());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(hand3, player1.getHand());

        player1.getActions().addGold(3);
        player1.playAsMagician();
        assertEquals(Power.RECYCLE, player1.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(4, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getName());
        assertEquals("Manoir", player1.getHand().get(1).getName());
        assertEquals("Manoir", player1.getHand().get(2).getName());
        assertEquals("Manoir", player1.getHand().get(3).getName());

        assertEquals(1, player1.getCity().size());
        assertEquals("Manoir", player1.getCity().get(0).getName());
        assertEquals(1, player1.getMemory().getCardsToDiscard());

        player1.playAsMagician();
        assertEquals(Power.RECYCLE, player1.getMemory().getPowerToUse());
        assertEquals(hand1, player3.getHand());
        assertEquals(hand2, player2.getHand());
        assertEquals(3, player1.getHand().size());
        assertEquals("Château", player1.getHand().get(0).getName());
        assertEquals("Manoir", player1.getHand().get(1).getName());
        assertEquals("Manoir", player1.getHand().get(2).getName());
        assertEquals(2, player1.getCity().size());
        assertEquals("Manoir", player1.getCity().get(0).getName());
        assertEquals("Château", player1.getCity().get(1).getName());
        assertEquals(5, player1.getMemory().getCardsToDiscard());
    }

    @Test
    void playAsMerchant() {
        player1.getMemory().getPile().initializePile();
        player1.setCharacter(CharactersList.allCharacterCards[5]);

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
        player1.setCharacter(CharactersList.allCharacterCards[7]);
        player2.setCharacter(CharactersList.allCharacterCards[0]);
        player3.setCharacter(CharactersList.allCharacterCards[1]);

        player1.setGold(3);
        player1.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[0])));
        player2.setCity(new City(List.of(DistrictsPile.allDistrictCards[1])));

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
        player1.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[61])));
        player1.getMemory().setDistrictToBuild(DistrictsPile.allDistrictCards[61]);
        player1.getActions().addGold(6);
        player1.getActions().build();
        assertFalse(player1.chooseFactoryEffect());

        player1.getActions().addGold(3);
        assertTrue(player1.chooseFactoryEffect());
    }

    @Test
    void activateLaboratoryEffect() {
        player1.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1], DistrictsPile.allDistrictCards[2], DistrictsPile.allDistrictCards[3])));
        assertTrue(player1.chooseLaboratoryEffect());

        player1.getActions().addGold(2);
        assertFalse(player1.chooseLaboratoryEffect());

        player1.getHand().addAll(List.of(DistrictsPile.allDistrictCards[4], DistrictsPile.allDistrictCards[5]));
        assertTrue(player1.chooseLaboratoryEffect());

        player1.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[1])));
        player1.setGold(0);
        assertFalse(player1.chooseLaboratoryEffect());
        player1.getCity().add(DistrictsPile.allDistrictCards[0]);
        assertTrue(player1.chooseLaboratoryEffect());
    }

    @Test
    void activateGraveyardEffect() {
        player1.setGold(1);
        player1.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5])));

        assertTrue(player1.chooseGraveyardEffect(DistrictsPile.allDistrictCards[10]));
        assertFalse(player1.chooseGraveyardEffect(DistrictsPile.allDistrictCards[5]));
        assertFalse(player1.chooseGraveyardEffect(DistrictsPile.allDistrictCards[15]));
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