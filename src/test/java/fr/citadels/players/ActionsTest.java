package fr.citadels.players;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActionsTest {
    Player player;
    Player player2;
    Actions actions;
    Memory info;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.getPile().initializePile();
        info = new Memory(game);
        player = new Monarchist("test", List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[63], DistrictsPile.allDistrictCards[62]), game);
        player2 = new Monarchist("bot", new ArrayList<>(), game);
        actions = new Actions(player, info);

    }

    @Test
    void putRedundantCardAtTheEnd() {
        player.setHand(new Hand(List.of(DistrictsPile.allDistrictCards[24], DistrictsPile.allDistrictCards[35], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[26], DistrictsPile.allDistrictCards[30])));
        player.setCity(new City(List.of(DistrictsPile.allDistrictCards[27])));

        assertEquals("Taverne", player.getHand().get(0).getName());
        assertEquals("Marché", player.getHand().get(1).getName());
        assertEquals("Taverne", player.getHand().get(2).getName());
        assertEquals("Taverne", player.getHand().get(3).getName());
        assertEquals("Échoppe", player.getHand().get(4).getName());

        player.getActions().putRedundantCardsAtTheEnd();
        assertEquals("Marché", player.getHand().get(0).getName());
        assertEquals("Échoppe", player.getHand().get(1).getName());
        assertEquals("Taverne", player.getHand().get(2).getName());
        assertEquals("Taverne", player.getHand().get(3).getName());
        assertEquals("Taverne", player.getHand().get(4).getName());
    }


    @Test
    void putBack() {
        District[] drawnCards = new District[3];
        drawnCards[0] = DistrictsPile.allDistrictCards[12];
        drawnCards[1] = DistrictsPile.allDistrictCards[0];
        drawnCards[2] = DistrictsPile.allDistrictCards[22];
        actions.putBack(drawnCards, 1);
        for (int i = 0; i < drawnCards.length; i++) {
            if (i == 1) {
                assertEquals("Manoir", drawnCards[i].getName());
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


    }

    @Test
    void takeCardOrGoldEffect() {
        player.getHand().add(DistrictsPile.allDistrictCards[64]);
        player.getActions().addGold(6);
        player.getInformation().setDistrictToBuild(DistrictsPile.allDistrictCards[64]);
        player.getHand().remove(DistrictsPile.allDistrictCards[64]);
        player.getActions().build();
        actions.takeCardsOrGold(true);
        assertEquals(9, player.getHand().size());

        actions.takeCardsOrGold(false);
        assertEquals(9, player.getHand().size());
        assertEquals(2, player.getGold());

        player.getHand().add(DistrictsPile.allDistrictCards[59]);
        player.getActions().addGold(5);
        player.getInformation().setDistrictToBuild(DistrictsPile.allDistrictCards[59]);
        player.getHand().remove(DistrictsPile.allDistrictCards[59]);
        player.getActions().build();

        actions.takeCardsOrGold(true);
        assertEquals(12, player.getHand().size());
        DistrictsPile.allDistrictCards[64].setOwner(null);
        DistrictsPile.allDistrictCards[59].setOwner(null);
    }


    @Test
    void takeGoldFromCity() {
        Player playerSpy = spy(player);
        actions = new Actions(playerSpy, info);
        //NOBLE card
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[45], DistrictsPile.allDistrictCards[60]))));

        actions.takeGoldFromCity();

        //no character
        assertEquals(0, playerSpy.getGold());

        //choose NOBLE
        playerSpy.setCharacter(CharactersList.allCharacterCards[3]);

        actions.takeGoldFromCity();
        assertEquals(2, playerSpy.getGold());


        //choose RELIGIOUS
        playerSpy.setCharacter(CharactersList.allCharacterCards[4]);
        actions.takeGoldFromCity();
        //+1
        assertEquals(3, playerSpy.getGold());


        //choose TRADE
        playerSpy.setCharacter(CharactersList.allCharacterCards[5]);
        actions.takeGoldFromCity();
        //+2
        assertEquals(5, playerSpy.getGold());

        //choose MILITARY
        playerSpy.setCharacter(CharactersList.allCharacterCards[7]);
        actions.takeGoldFromCity();
        //+1
        assertEquals(6, playerSpy.getGold());

        //choose NEUTRAL
        playerSpy.setCharacter(CharactersList.allCharacterCards[1]);
        actions.takeGoldFromCity();
        //+0
        assertEquals(6, playerSpy.getGold());

        // choose MILITARY but with School Of Magic district
        when(playerSpy.getCity()).thenReturn(new City(new ArrayList<>(List.of(DistrictsPile.allDistrictCards[63], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[5], DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[25], DistrictsPile.allDistrictCards[30], DistrictsPile.allDistrictCards[60]))));
        playerSpy.setCharacter(CharactersList.allCharacterCards[7]);
        actions.takeGoldFromCity();
        // +1
        assertEquals(7, playerSpy.getGold());

        // choose RELIGIOUS but with School of Magic district
        playerSpy.setCharacter(CharactersList.allCharacterCards[4]);
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

        info.setDistrictToBuild(DistrictsPile.allDistrictCards[0]);
        actions.build();
        assertEquals(1, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(0).getName());

        info.setDistrictToBuild(DistrictsPile.allDistrictCards[66]);
        actions.build();
        assertEquals(2, player.getCity().size());
        assertEquals("Dracoport", player.getCity().get(1).getName());
    }

    @Test
    void getRobbed() {
        Character thief = CharactersList.allCharacterCards[1];
        Character king = CharactersList.allCharacterCards[3];

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