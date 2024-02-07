package fr.citadels.engine.score;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreboardTest {

    CharactersList characters = new CharactersList(CharactersList.allCharacterCards);

    /* Initialize cards */

    List<District> cardsPlayer1 = Arrays.asList(
            DistrictsPile.allDistrictCards[0], //cost 3
            DistrictsPile.allDistrictCards[5], //cost 4
            DistrictsPile.allDistrictCards[10], //cost 5
            DistrictsPile.allDistrictCards[12], //cost 1
            DistrictsPile.allDistrictCards[15], //cost 2
            DistrictsPile.allDistrictCards[18], //cost 3
            DistrictsPile.allDistrictCards[22], //cost 5
            DistrictsPile.allDistrictCards[24]); //cost 1

    List<District> cardsPlayer2 = Arrays.asList(
            DistrictsPile.allDistrictCards[29], //cost 2
            DistrictsPile.allDistrictCards[33], //cost 2
            DistrictsPile.allDistrictCards[37], //cost 3
            DistrictsPile.allDistrictCards[40], //cost 4
            DistrictsPile.allDistrictCards[43], //cost 5
            DistrictsPile.allDistrictCards[45], //cost 1
            DistrictsPile.allDistrictCards[48]); //cost 2

    List<District> cardsPlayer3 = Arrays.asList(
            DistrictsPile.allDistrictCards[51], //cost 3
            DistrictsPile.allDistrictCards[54], //cost 5
            DistrictsPile.allDistrictCards[57], //cost 2
            DistrictsPile.allDistrictCards[58], //cost 3
            DistrictsPile.allDistrictCards[59], //cost 5
            DistrictsPile.allDistrictCards[51]); //cost 3

    List<District> cardsPlayer4 = Arrays.asList(
            DistrictsPile.allDistrictCards[60], //cost 5
            DistrictsPile.allDistrictCards[61], //cost 5
            DistrictsPile.allDistrictCards[62], //cost 5
            DistrictsPile.allDistrictCards[63], //cost 6
            DistrictsPile.allDistrictCards[64], //cost 6
            DistrictsPile.allDistrictCards[65], //cost 6
            DistrictsPile.allDistrictCards[66]); //cost 6



    /* Initialize players */

    Game game = new Game(new Player[4], null);

    Player player1 = new Monarchist("Tom", cardsPlayer1, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };

    Player player2 = new Monarchist("Bob", cardsPlayer2, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };

    Player player3 = new Monarchist("Noa", cardsPlayer3, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };

    Player player4 = new Monarchist("Luk", cardsPlayer4, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };
    Scoreboard scoreboard = new Scoreboard(new Player[]{player1, player2, player3, player4});



    /* Create the scoreboard */

    /* Simulate an entire game (Only for this test class, the method play has been changed) */
    @BeforeEach
    void setUp() {
        player1.playBuildingPhase();
        player1.chooseCharacter(characters);
        player2.playBuildingPhase();
        player2.chooseCharacter(characters);
        player3.playBuildingPhase();
        player3.chooseCharacter(characters);
        player4.playBuildingPhase();
        player4.chooseCharacter(characters);
    }


    @Test
    void getWinner() {
        Score.setFirstPlayerWithCompleteCity(player2);
        scoreboard.determineRanking();
        assertEquals(player4, scoreboard.getWinner());
    }

    @Test
    void initialize() {
        scoreboard.initialize(new Player[]{player1, player2, player3, player4});
        assertEquals(player1, scoreboard.getScores()[0].getPlayer());
        assertEquals(player2, scoreboard.getScores()[1].getPlayer());
        assertEquals(player3, scoreboard.getScores()[2].getPlayer());
        assertEquals(player4, scoreboard.getScores()[3].getPlayer());
    }

    @Test
    void toStringTest() {
        Score.setFirstPlayerWithCompleteCity(player2);
        scoreboard.determineRanking();
        assertEquals("■ 1e place\n" +
                        scoreboard.getScores()[0].toString() + "\n" +
                        "■ 2e place\n" +
                        scoreboard.getScores()[1].toString() + "\n" +
                        "■ 3e place\n" +
                        scoreboard.getScores()[2].toString() + "\n" +
                        "■ 4e place\n" +
                        scoreboard.getScores()[3].toString() + "\n"
                , scoreboard.toString());
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