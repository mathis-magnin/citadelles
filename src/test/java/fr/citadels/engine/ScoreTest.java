package fr.citadels.engine;

import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {

    /* Initialize cards */

    List<DistrictCard> cardsPlayer1 = Arrays.asList(
            DistrictCardsPile.allDistrictCards[0], //cost 3
            DistrictCardsPile.allDistrictCards[5], //cost 4
            DistrictCardsPile.allDistrictCards[10], //cost 5
            DistrictCardsPile.allDistrictCards[12], //cost 1
            DistrictCardsPile.allDistrictCards[15], //cost 2
            DistrictCardsPile.allDistrictCards[18], //cost 3
            DistrictCardsPile.allDistrictCards[22], //cost 5
            DistrictCardsPile.allDistrictCards[24]); //cost 1

    List<DistrictCard> cardsPlayer2 = Arrays.asList(
            DistrictCardsPile.allDistrictCards[29], //cost 2
            DistrictCardsPile.allDistrictCards[33], //cost 2
            DistrictCardsPile.allDistrictCards[37], //cost 3
            DistrictCardsPile.allDistrictCards[40], //cost 4
            DistrictCardsPile.allDistrictCards[43], //cost 5
            DistrictCardsPile.allDistrictCards[45], //cost 1
            DistrictCardsPile.allDistrictCards[48]); //cost 2

    List<DistrictCard> cardsPlayer3 = Arrays.asList(
            DistrictCardsPile.allDistrictCards[51], //cost 3
            DistrictCardsPile.allDistrictCards[54], //cost 5
            DistrictCardsPile.allDistrictCards[57], //cost 2
            DistrictCardsPile.allDistrictCards[58], //cost 3
            DistrictCardsPile.allDistrictCards[59]); //cost 5

    List<DistrictCard> cardsPlayer4 = Arrays.asList(
            DistrictCardsPile.allDistrictCards[60], //cost 5
            DistrictCardsPile.allDistrictCards[61], //cost 5
            DistrictCardsPile.allDistrictCards[62], //cost 5
            DistrictCardsPile.allDistrictCards[63], //cost 6
            DistrictCardsPile.allDistrictCards[64], //cost 6
            DistrictCardsPile.allDistrictCards[65], //cost 6
            DistrictCardsPile.allDistrictCards[66]); //cost 6


    /* Initialize players */

    DistrictCardsPile pile = new DistrictCardsPile();

    Player player1 = new Player("Tom", cardsPlayer1) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };
    Score score1 = new Score(player1);
    Player player2 = new Player("Bob", cardsPlayer2) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };
    Score score2 = new Score(player2);


    /* Simulate an entire game (Only for this test class, the method play has been changed) */
    Player player3 = new Player("Noa", cardsPlayer3) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };


    /* Create the score */
    Score score3 = new Score(player3);
    Player player4 = new Player("Luk", cardsPlayer4) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };
    Score score4 = new Score(player4);

    @BeforeEach
    void setUp() {
        player1.play(pile);
        player2.play(pile);
        player3.play(pile);
        player4.play(pile);
    }

    @Test
    void getPoints() {
        assertEquals(0, score1.getPoints());
        assertEquals(0, score2.getPoints());
        assertEquals(0, score3.getPoints());
        assertEquals(0, score4.getPoints());
    }


    @Test
    void getPlayer() {
        assertEquals(player1, score1.getPlayer());
        assertEquals(player2, score2.getPlayer());
        assertEquals(player3, score3.getPlayer());
        assertEquals(player4, score4.getPlayer());
    }


    @Test
    void testToString() {
        assertEquals("Score de Tom : 0", score1.toString());

        Score.setFirstPlayerWithCompleteCity(player2);
        score2.determinePoints(); // 19 + 4 = 23
        assertEquals("Score de Bob : 23", score2.toString());

        assertEquals("Score de Noa : 0", score3.toString());

        score4.determinePoints(); // 39 + 2 = 41
        assertEquals("Score de Luk : 41", score4.toString());
    }


    @Test
    void compareTo() {
        Score.setFirstPlayerWithCompleteCity(player1);

        score1.determinePoints(); // 24 + 4 = 28
        score2.determinePoints(); // 19 + 2 = 21
        score3.determinePoints(); // 18
        score4.determinePoints(); // 39 + 2 = 41

        assertEquals(1, score1.compareTo(score2));
        assertEquals(-1, score2.compareTo(score1));

        assertEquals(-1, score2.compareTo(score4));
        assertEquals(1, score4.compareTo(score2));

        assertEquals(-1, score3.compareTo(score2));
        assertEquals(1, score2.compareTo(score3));
    }


    @Test
    void determinePoints() {
        Score.setFirstPlayerWithCompleteCity(player4);

        score1.determinePoints(); // 24 + 2 = 26
        score2.determinePoints(); // 19 + 2 = 21
        score3.determinePoints(); // 18
        score4.determinePoints(); // 39 + 4 = 43

        assertEquals(26, score1.getPoints());
        assertEquals(21, score2.getPoints());
        assertEquals(18, score3.getPoints());
        assertEquals(43, score4.getPoints());
    }
}