package fr.citadels.players;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.Hand;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PlayerActionsTest {
    Player player;
    Player player2;
    PlayerActions actions;
    PlayerInformation info;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.getPile().initializePile();
        info = new PlayerInformation(game);
        player = new KingBot("test", List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]), game);
        player2 = new KingBot("bot", new ArrayList<>(), game);
        actions = new PlayerActions(player, info);

    }

    @Test
    void putRedundantCardAtTheEnd() {
        player.setHand(new Hand(List.of(DistrictCardsPile.allDistrictCards[24], DistrictCardsPile.allDistrictCards[35], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[26], DistrictCardsPile.allDistrictCards[30])));
        player.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[27])));

        assertEquals("Taverne", player.getHand().get(0).getCardName());
        assertEquals("Marché", player.getHand().get(1).getCardName());
        assertEquals("Taverne", player.getHand().get(2).getCardName());
        assertEquals("Taverne", player.getHand().get(3).getCardName());
        assertEquals("Échoppe", player.getHand().get(4).getCardName());

        player.getActions().putRedundantCardsAtTheEnd();
        assertEquals("Marché", player.getHand().get(0).getCardName());
        assertEquals("Échoppe", player.getHand().get(1).getCardName());
        assertEquals("Taverne", player.getHand().get(2).getCardName());
        assertEquals("Taverne", player.getHand().get(3).getCardName());
        assertEquals("Taverne", player.getHand().get(4).getCardName());
    }


    @Test
    void putBack() {
        DistrictCard[] drawnCards = new DistrictCard[3];
        drawnCards[0] = DistrictCardsPile.allDistrictCards[12];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[0];
        drawnCards[2] = DistrictCardsPile.allDistrictCards[22];
        actions.putBack(drawnCards, 1);
        for (int i = 0; i < drawnCards.length; i++) {
            if (i == 1) {
                assertEquals("Manoir", drawnCards[i].getCardName());
            } else {
                assertNull(drawnCards[i]);
            }
        }


    }

    @Test
    void takeCardsOrGold() {

        actions.takeCardsOrGold(false);
        assertEquals(7, player.getHand().size());
        assertEquals(2, player.getGold());

        actions.takeCardsOrGold(true);
        assertEquals(8, player.getHand().size());
        assertEquals(2, player.getGold());

        player.getHand().add(DistrictCardsPile.allDistrictCards[64]);
        player.getActions().addGold(6);
        player.getInformation().setDistrictToBuild(DistrictCardsPile.allDistrictCards[64]);
        player.getHand().remove(DistrictCardsPile.allDistrictCards[64]);
        player.getActions().build();
        actions.takeCardsOrGold(true);
        assertEquals(10, player.getHand().size());

        actions.takeCardsOrGold(false);
        assertEquals(10, player.getHand().size());
        assertEquals(4, player.getGold());

    }

    @Test
    void takeGoldFromCity() {
        Player playerSpy = spy(player);
        actions = new PlayerActions(playerSpy, info);
        //NOBLE card
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[60]))));

        actions.takeGoldFromCity();

        //no character
        assertEquals(0, playerSpy.getGold());

        //choose NOBLE
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[3]);

        actions.takeGoldFromCity();
        assertEquals(2, playerSpy.getGold());


        //choose RELIGIOUS
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[4]);
        actions.takeGoldFromCity();
        //+1
        assertEquals(3, playerSpy.getGold());


        //choose TRADE
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[5]);
        actions.takeGoldFromCity();
        //+2
        assertEquals(5, playerSpy.getGold());

        //choose MILITARY
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[7]);
        actions.takeGoldFromCity();
        //+1
        assertEquals(6, playerSpy.getGold());

        //choose NEUTRAL
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[1]);
        actions.takeGoldFromCity();
        //+0
        assertEquals(6, playerSpy.getGold());

        // choose NEUTRAL but with School Of Magic district
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[60]))));
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[1]);
        actions.takeGoldFromCity();
        // +1
        assertEquals(7, playerSpy.getGold());

        // choose RELIGIOUS but with School of Magic district
        playerSpy.setCharacter(CharacterCardsList.allCharacterCards[4]);
        actions.takeGoldFromCity();
        assertEquals(9, playerSpy.getGold()); // +1 and +1

    }


    @Test
    void addGold() {
        actions.addGold(5);
        assertEquals(5, player.getGold());
        actions.addGold(2);
        assertEquals(7, player.getGold());
    }

    @Test
    void removeGold() {
        actions.addGold(5);
        assertEquals(5, player.getGold());
        actions.removeGold(2);
        assertEquals(3, player.getGold());
        actions.removeGold(10);
        assertEquals(0, player.getGold());
    }

    @Test
    void build() {
        info.setDistrictToBuild(null);
        actions.build();
        assertEquals(0, player.getCity().size());

        info.setDistrictToBuild(DistrictCardsPile.allDistrictCards[0]);
        actions.build();
        assertEquals(1, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(0).getCardName());

        info.setDistrictToBuild(DistrictCardsPile.allDistrictCards[66]);
        actions.build();
        assertEquals(2, player.getCity().size());
        assertEquals("Dracoport", player.getCity().get(1).getCardName());
    }

    @Test
    void getRobbed() {
        CharacterCard thief = CharacterCardsList.allCharacterCards[1];
        CharacterCard king = CharacterCardsList.allCharacterCards[3];

        thief.setPlayer(player);
        player.setCharacter(thief);
        player.setGold(10);

        king.setPlayer(player2);
        player2.setCharacter(king);
        player2.setGold(8);

        player.getInformation().setTarget(king);
        thief.usePower();

        assertEquals(10, player.getGold());
        assertEquals(8, player2.getGold());
        assertTrue(player2.getCharacter().isRobbed());
        player2.getActions().getRobbed();
        assertEquals(18, player.getGold());
        assertEquals(0, player2.getGold());
        assertFalse(player.getCharacter().isRobbed());
    }


    @Test
    void draw() {
        int handSize = player.getHand().size();
        actions.draw(0);
        assertEquals(handSize, player.getHand().size());

        actions.draw(10);
        assertEquals(handSize + 10, player.getHand().size());
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