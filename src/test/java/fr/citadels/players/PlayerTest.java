package fr.citadels.players;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
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
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        game = new Game();
        player = new Player("Hello", districts, game) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return drawnCards[0];
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
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
                this.chooseCharacter(new CharacterCardsList(CharacterCardsList.allCharacterCards));
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
        assertEquals("Temple", player.getHand().get(0).getCardName());
        assertEquals("Manoir", player.getHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getHand().get(2).getCardName());
    }

    @Test
    void getCity() {
        assertTrue(player.getCity().isEmpty());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(1, player.getCity().size());
        assertEquals("Temple", player.getCity().get(0).getCardName());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(1).getCardName());

        player.playResourcesPhase();
        player.playBuildingPhase();
        assertEquals(3, player.getCity().size());
        assertEquals("Cathédrale", player.getCity().get(2).getCardName());

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
        assertTrue(player.hasCardInCity(DistrictCardsPile.allDistrictCards[12]));
        assertFalse(player.hasCardInCity(DistrictCardsPile.allDistrictCards[58]));
    }

    @Test
    void getGold() {
        assertEquals(0, player.getGold());
    }


    @Test
    void compareToTest() {
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, game) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
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
                this.chooseCharacter(new CharacterCardsList(CharacterCardsList.allCharacterCards));
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
        List<DistrictCard> districts2 = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        Player player2 = new Player("Hello", districts2, game) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
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
                this.chooseCharacter(new CharacterCardsList(CharacterCardsList.allCharacterCards));
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
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return null;
            }

            @Override
            public void chooseDistrictToBuild() {
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
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
                this.chooseCharacter(new CharacterCardsList(CharacterCardsList.allCharacterCards));
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

        };


        assertNotEquals(player, player2);
        assertNotEquals(player2, player);

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