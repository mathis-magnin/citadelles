package fr.citadels.engine.score;

import fr.citadels.cards.characters.roles.*;
import fr.citadels.engine.Game;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.DistrictsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreTest {

    /* Initialize cards */

    CharactersList characters = new CharactersList(CharactersList.allCharacterCards);

    List<District> cardsPlayer1 = Arrays.asList(
            DistrictsPile.allDistrictCards[0], //cost 3
            DistrictsPile.allDistrictCards[5], //cost 4
            DistrictsPile.allDistrictCards[10], //cost 5
            DistrictsPile.allDistrictCards[12], //cost 1
            DistrictsPile.allDistrictCards[15], //cost 2
            DistrictsPile.allDistrictCards[18], //cost 3
            DistrictsPile.allDistrictCards[22], //cost 5
            DistrictsPile.allDistrictCards[24]  //cost 1
    );

    List<District> cardsPlayer2 = Arrays.asList(
            DistrictsPile.allDistrictCards[29], //cost 2
            DistrictsPile.allDistrictCards[33], //cost 2
            DistrictsPile.allDistrictCards[37], //cost 3
            DistrictsPile.allDistrictCards[40], //cost 4
            DistrictsPile.allDistrictCards[43], //cost 5
            // DistrictCardsPile.allDistrictCards[45], //cost 1
            DistrictsPile.allDistrictCards[48]  //cost 2
    );

    List<District> cardsPlayer3 = Arrays.asList(
            DistrictsPile.allDistrictCards[51], //cost 3
            DistrictsPile.allDistrictCards[54], //cost 5
            DistrictsPile.allDistrictCards[57], //cost 2
            DistrictsPile.allDistrictCards[58], //cost 3
            DistrictsPile.allDistrictCards[59]  //cost 5
    );

    List<District> cardsPlayer4 = Arrays.asList(
            DistrictsPile.allDistrictCards[60], //cost 5
            DistrictsPile.allDistrictCards[61], //cost 5
            DistrictsPile.allDistrictCards[62], //cost 5
            DistrictsPile.allDistrictCards[63], //cost 6
            DistrictsPile.allDistrictCards[64], //cost 6
            DistrictsPile.allDistrictCards[65], //cost 6, bring 8 points
            DistrictsPile.allDistrictCards[66] //cost 6, bring 8 points
    ); //cost 6, bring 8 points

    List<District> cardsPlayer5 = Arrays.asList(
            DistrictsPile.allDistrictCards[57], //cost 2 MiracleCourtyard
            DistrictsPile.allDistrictCards[0],  //cost 3 Noble
            DistrictsPile.allDistrictCards[15], //cost 2 Religious
            DistrictsPile.allDistrictCards[25], //cost 1 Trade
            DistrictsPile.allDistrictCards[58]  //cost 3 Unique
    );

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
    Score score1 = new Score(player1);

    Player player2 = new Monarchist("Bob", cardsPlayer2, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };
    Score score2 = new Score(player2);

    Player player3 = new Monarchist("Noa", cardsPlayer3, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };
    Score score3 = new Score(player3);


    /* Create the score */
    Player player4 = new Monarchist("Luk", cardsPlayer4, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };
    Score score4 = new Score(player4);

    Player player5 = new Monarchist("Dan", cardsPlayer5, game) {
        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
            this.getActions().addCardsToCity(this.getHand());
        }

    };
    Score score5 = new Score(player5);

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
        player5.playBuildingPhase();
        player5.chooseCharacter(characters);
    }

    @Test
    void getPoints() {
        assertEquals(0, score1.getPoints());
        assertEquals(0, score2.getPoints());
        assertEquals(0, score3.getPoints());
        assertEquals(0, score4.getPoints());
        assertEquals(0, score5.getPoints());
    }


    @Test
    void getPlayer() {
        assertEquals(player1, score1.getPlayer());
        assertEquals(player2, score2.getPlayer());
        assertEquals(player3, score3.getPlayer());
        assertEquals(player4, score4.getPlayer());
        assertEquals(player5, score5.getPlayer());
    }


    @Test
    void compareTo() {
        Score.setFirstPlayerWithCompleteCity(player1);

        score1.determinePoints(); // 24 + 4 = 28
        score2.determinePoints(); // 18
        score3.determinePoints(); // 18
        score4.determinePoints(); // 27 + 2 + 16 = 45
        score5.determinePoints(); // 11 + 3 = 14


        assertTrue(score1.compareTo(score2) > 0);
        assertTrue(score2.compareTo(score1) < 0);

        assertTrue(score2.compareTo(score4) < 0);
        assertTrue(score4.compareTo(score2) > 0);

        assertTrue(score3.compareTo(score2) > 0); // comparison based on character's rank.
        assertTrue(score2.compareTo(score3) < 0);
    }


    @Test
    void determinePoints() {
        Score.setFirstPlayerWithCompleteCity(player4);

        score1.determinePoints(); // 24 + 2 = 26
        score2.determinePoints(); // 18
        score3.determinePoints(); // 18
        score4.determinePoints(); // 27 + 4 + 16 = 47
        score5.determinePoints(); // 11 + 3 = 14

        assertEquals(26, score1.getPoints());
        assertEquals(18, score2.getPoints());
        assertEquals(18, score3.getPoints());
        assertEquals(47, score4.getPoints());
        assertEquals(14, score5.getPoints());
    }

    @Test
    void toStringTest() {
        Score.setFirstPlayerWithCompleteCity(player4);

        score1.determinePoints(); // 24 + 2 = 26
        score2.determinePoints(); // 18
        score3.determinePoints(); // 18
        score4.determinePoints(); // 27 + 4 + 16 = 47
        score5.determinePoints(); // 11 + 3 = 14

        assertEquals("Tom\n" +
                        "\tScore total : 26 points\n" +
                        "\tQuartiers construits : 24 points\n" +
                        "\tCité complète : 2 points\n"
                , score1.toString());
        assertEquals("Bob\n" +
                        "\tScore total : 18 points\n" +
                        "\tQuartiers construits : 18 points\n"
                , score2.toString());
        assertEquals("Noa\n" +
                        "\tScore total : 18 points\n" +
                        "\tQuartiers construits : 18 points\n"
                , score3.toString());
        assertEquals("Luk\n" +
                        "\tScore total : 47 points\n" +
                        "\tQuartiers construits : 27 points\n" +
                        "\tEffets des quartiers merveilles : 16 points\n" +
                        "\tPremière cité complète : 4 points\n"
                , score4.toString());
        assertEquals("Dan\n" +
                        "\tScore total : 14 points\n" +
                        "\tQuartiers construits : 11 points\n" +
                        "\tQuartier de chaque famille grâce à l'effet de la Cour des miracles : 3 points\n"
                , score5.toString());
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
        Score.setFirstPlayerWithCompleteCity(null);
    }

}