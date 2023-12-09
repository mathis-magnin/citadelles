package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreboardTest {

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
        public DistrictCard chooseCard(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };

    Player player2 = new Player("Bob", cardsPlayer2) {
        @Override
        public DistrictCard chooseCard(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };

    Player player3 = new Player("Noa", cardsPlayer3) {
        @Override
        public DistrictCard chooseCard(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };

    Player player4 = new Player("Luk", cardsPlayer4) {
        @Override
        public DistrictCard chooseCard(DistrictCardsPile pile, DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public String play(DistrictCardsPile pile) {
            this.cityCards.addAll(this.cardsInHand);
            return null;
        }
    };
    Scoreboard scoreboard = new Scoreboard(new Player[]{player1, player2, player3, player4});



    /* Create the scoreboard */

    /* Simulate an entire game (Only for this test class, the method play has been changed) */
    @BeforeEach
    void setUp() {
        player1.play(pile);
        player2.play(pile);
        player3.play(pile);
        player4.play(pile);
    }

    @Test
    void testToString() {
        assertEquals("Score de Tom : 0\nScore de Bob : 0\nScore de Noa : 0\nScore de Luk : 0\n", scoreboard.toString());
    }

    @Test
    void determineRanking() {
        Score.setFirstPlayerWithCompleteCity(player1);
        scoreboard.determineRanking();
        assertEquals("Score de Luk : 41\nScore de Tom : 28\nScore de Bob : 21\nScore de Noa : 18\n", scoreboard.toString());
    }

    @Test
    void getWinner() {
        Score.setFirstPlayerWithCompleteCity(player2);
        scoreboard.determineRanking();
        assertEquals(player4, scoreboard.getWinner());
    }
}