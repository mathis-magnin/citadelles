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
        assertEquals("Score du joueur Tom : 0\nScore du joueur Bob : 0\nScore du joueur Noa : 0\nScore du joueur Luk : 0\n", scoreboard.toString());
    }

    @Test
    void determineRanking() {
        Score.setFirstPlayerWithCompleteCity(player1);
        scoreboard.determineRanking();
        assertEquals("Score du joueur Tom : 12\nScore du joueur Bob : 9\nScore du joueur Luk : 9\nScore du joueur Noa : 5\n", scoreboard.toString());
    }

    @Test
    void getWinner() {
        Score.setFirstPlayerWithCompleteCity(player2);
        scoreboard.determineRanking();
        assertEquals(player2, scoreboard.getWinner());
    }
}