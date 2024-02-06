package fr.citadels.players;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    Game game;

    @BeforeEach
    void setUp() {
        List<District> districts = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[63], DistrictsPile.allDistrictCards[62]));
        game = new Game(null, null);
        player = new Player("Hello", districts, game) {
            @Override
            public District chooseCardAmongDrawn(District[] drawnCards) {
                return drawnCards[0];
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharactersList characters) {
                this.setCharacter(characters.get(1));
            }

            @Override
            public void chooseTargetToKill() {
            }

            @Override
            public void chooseTargetToRob() {
            }

            @Override
            public int chooseMagicianPower() {
                return 0;
            }

            @Override
            public void chooseTargetToDestroy() {
            }

            @Override
            public void playResourcesPhase() {
                this.chooseCharacter(new CharactersList(CharactersList.allCharacterCards));
            }

            @Override
            public void playBuildingPhase() {
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public boolean activateFactoryEffect() {
                return false;
            }

            @Override
            public boolean activateLaboratoryEffect() {
                return false;
            }

            @Override
            public boolean activateGraveyardEffect(District removedDistrict) {
                return false;
            }
        };
    }

    @Test
    void getName() {
        assertEquals("Hello", player.getName());
    }


    @Test
    void getHand() {
        assertEquals(7, player.getHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getHand().get(0).getName());
        assertEquals("Manoir", player.getHand().get(1).getName());
        assertEquals("Cathédrale", player.getHand().get(2).getName());
    }

    @Test
    void getCity() {
        assertTrue(player.getCity().isEmpty());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals("Temple", player.getCity().get(0).getName());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(1).getName());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(3, player.getCity().size());
        assertEquals("Cathédrale", player.getCity().get(2).getName());

    }

    @Test
    void hasCompleteCity() {
        while (player.getCity().size() < 7) {
            assertFalse(player.hasCompleteCity());
            player.playResourcesPhase();
            player.playBuildingPhase();
        }
        assertTrue(player.hasCompleteCity());
    }

    @Test
    void hasCardInHand() {
        player.playResourcesPhase();
        player.playBuildingPhase();
        assertTrue(player.hasCardInCity(DistrictsPile.allDistrictCards[12]));
        assertFalse(player.hasCardInCity(DistrictsPile.allDistrictCards[58]));
    }

    @Test
    void getGold() {
        assertEquals(0, player.getGold());
    }


    @Test
    void compareToTest() {
        List<District> districts2 = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[63], DistrictsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, game) {
            @Override
            public District chooseCardAmongDrawn(District[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharactersList characters) {
                this.setCharacter(characters.get(2));
            }

            @Override
            public void chooseTargetToKill() {
            }

            @Override
            public void chooseTargetToRob() {
            }

            @Override
            public int chooseMagicianPower() {
                return 0;
            }

            @Override
            public void chooseTargetToDestroy() {
            }

            @Override
            public void playResourcesPhase() {
                this.chooseCharacter(new CharactersList(CharactersList.allCharacterCards));
            }

            @Override
            public void playBuildingPhase() {
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public boolean activateFactoryEffect() {
                return false;
            }

            @Override
            public boolean activateLaboratoryEffect() {
                return false;
            }

            @Override
            public boolean activateGraveyardEffect(District removedDistrict) {
                return false;
            }
        };
        player.playResourcesPhase();
        player.playBuildingPhase();
        player2.playResourcesPhase();
        player2.playBuildingPhase();

        assertTrue(player.compareTo(player2) < 0);
        assertTrue(player2.compareTo(player) > 0);
    }


    @Test
    void equals() {
        List<District> districts2 = new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12], DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[22], DistrictsPile.allDistrictCards[15], DistrictsPile.allDistrictCards[18], DistrictsPile.allDistrictCards[63], DistrictsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, game) {
            @Override
            public District chooseCardAmongDrawn(District[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharactersList characters) {
                this.setCharacter(characters.get(2));
            }

            @Override
            public void chooseTargetToKill() {
            }

            @Override
            public void chooseTargetToRob() {
            }

            @Override
            public int chooseMagicianPower() {
                return 0;
            }

            @Override
            public void chooseTargetToDestroy() {
            }

            @Override
            public void playResourcesPhase() {
                this.chooseCharacter(new CharactersList(CharactersList.allCharacterCards));
            }

            @Override
            public void playBuildingPhase() {
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public boolean activateFactoryEffect() {
                return false;
            }

            @Override
            public boolean activateLaboratoryEffect() {
                return false;
            }

            @Override
            public boolean activateGraveyardEffect(District removedDistrict) {
                return false;
            }
        };
        player.playResourcesPhase();
        player.playBuildingPhase();
        player2.playResourcesPhase();
        player2.playBuildingPhase();
        assertEquals(player, player2);
        assertEquals(player2, player);

        //test with different name
        player2 = new Player("Hello2", districts2, game) {
            @Override
            public District chooseCardAmongDrawn(District[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharactersList characters) {
                this.setCharacter(characters.get(2));
            }

            @Override
            public void chooseTargetToKill() {
            }

            @Override
            public void chooseTargetToRob() {
            }

            @Override
            public int chooseMagicianPower() {
                return 0;
            }

            @Override
            public void chooseTargetToDestroy() {
            }

            @Override
            public void playResourcesPhase() {
                this.chooseCharacter(new CharactersList(CharactersList.allCharacterCards));
            }

            @Override
            public void playBuildingPhase() {
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public boolean activateFactoryEffect() {
                return false;
            }

            @Override
            public boolean activateLaboratoryEffect() {
                return false;
            }

            @Override
            public boolean activateGraveyardEffect(District removedDistrict) {
                return false;
            }
        };


        assertNotEquals(player, player2);
        assertNotEquals(player2, player);

    }

    @Test
    void initPlayer() {

        player.initPlayer(new ArrayList<>(List.of(DistrictsPile.allDistrictCards[12])), game);
        assertEquals(0, player.getGold());
        assertEquals(1, player.getHand().size());
        assertEquals(player.getHand().get(0).getName(), "Temple");
        assertEquals(0, player.getCity().size());
        assertNull(player.getCharacter());
        assertNotNull(player.getActions());
        assertNotNull(player.getMemory());
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