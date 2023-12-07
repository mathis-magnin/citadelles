package fr.citadels.engine;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {

    /* Initialize cards */

    List<DistrictCard> cardsPlayer1 = Arrays.asList(
            new DistrictCard("Manoir"),
            new DistrictCard("Château"),
            new DistrictCard("Palais"),
            new DistrictCard("Temple"),
            new DistrictCard("Église"),
            new DistrictCard("Monastère"),
            new DistrictCard("Cathédrale"),
            new DistrictCard("Taverne")
    );

    List<DistrictCard> cardsPlayer2 = Arrays.asList(
            new DistrictCard("Échoppe"),
            new DistrictCard("Marché"),
            new DistrictCard("Comptoir"),
            new DistrictCard("Port"),
            new DistrictCard("Hôtel de ville"),
            new DistrictCard("Tour de garde"),
            new DistrictCard("Prison")
    );

    List<DistrictCard> cardsPlayer3 = Arrays.asList(
            new DistrictCard("Caserne"),
            new DistrictCard("Fortetmpse"),
            new DistrictCard("Cour des miracles"),
            new DistrictCard("Donjon"),
            new DistrictCard("Observatoire")
    );

    List<DistrictCard> cardsPlayer4 = Arrays.asList(
            new DistrictCard("Laboratoire"),
            new DistrictCard("Manufacture"),
            new DistrictCard("Cimetière"),
            new DistrictCard("École de magie"),
            new DistrictCard("Bibliothèque"),
            new DistrictCard("Université"),
            new DistrictCard("Dracoport")
    );


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
    Score score1 = new Score(player1);
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
    Score score2 = new Score(player2);


    /* Simulate an entire game (Only for this test class, the method play has been changed) */
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


    /* Create the score */
    Score score3 = new Score(player3);
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
        assertEquals("Score du joueur Tom : 0", score1.toString());

        Score.setFirstPlayerWithCompleteCity(player2);
        score2.determinePoints(); // 7 + 4 = 11
        assertEquals("Score du joueur Bob : 11", score2.toString());

        assertEquals("Score du joueur Noa : 0", score3.toString());
        score4.determinePoints(); // 7 + 2 = 9
        assertEquals("Score du joueur Luk : 9", score4.toString());
    }


    @Test
    void compareTo() {
        Score.setFirstPlayerWithCompleteCity(player1);

        score1.determinePoints(); // 8 + 4 = 12
        score2.determinePoints(); // 7 + 2 = 9
        score3.determinePoints(); // 5
        score4.determinePoints(); // 7 + 2 = 9

        assertEquals(1, score1.compareTo(score2));
        assertEquals(-1, score2.compareTo(score1));

        assertEquals(0, score2.compareTo(score4));
        assertEquals(0, score4.compareTo(score2));

        assertEquals(-1, score3.compareTo(score2));
        assertEquals(1, score2.compareTo(score3));
    }


    @Test
    void determinePoints() {
        Score.setFirstPlayerWithCompleteCity(player4);

        score1.determinePoints();
        score2.determinePoints();
        score3.determinePoints();
        score4.determinePoints();

        assertEquals(8 + 2, score1.getPoints());
        assertEquals(7 + 2, score2.getPoints());
        assertEquals(5, score3.getPoints());
        assertEquals(7 + 4, score4.getPoints());
    }
}