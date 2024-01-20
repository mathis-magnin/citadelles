package fr.citadels.engine.score;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreboardTest {

    CharacterCardsList characters = new CharacterCardsList();

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
            DistrictCardsPile.allDistrictCards[59], //cost 5
            DistrictCardsPile.allDistrictCards[51]); //cost 3

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
    Bank bank = new Bank();
    Display events = new Display();

    Player player1 = new Player("Tom", cardsPlayer1, pile, bank, events) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public void chooseCharacter(CharacterCardsList characters) {
            this.setCharacter(characters.get(1));
        }

        @Override
        public void playResourcesPhase() { }

        @Override
        public void playBuildingPhase() {
            this.addCardsToCity(this.getHand());
        }

        @Override
        public void playAsAssassin() { }

        @Override
        public void playAsThief() { }

        @Override
        public void playAsMagician() { }

        @Override
        public void playAsKing() { }

        @Override
        public void playAsBishop() { }

        @Override
        public void playAsMerchant() { }

        @Override
        public void playAsArchitect() { }

        @Override
        public void playAsWarlord() { }

    };

    Player player2 = new Player("Bob", cardsPlayer2, pile, bank, events) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public void chooseCharacter(CharacterCardsList characters) {
            this.setCharacter(characters.get(2));
        }

        @Override
        public void playResourcesPhase() { }

        @Override
        public void playBuildingPhase() {
            this.addCardsToCity(this.getHand());
        }

        @Override
        public void playAsAssassin() { }

        @Override
        public void playAsThief() { }

        @Override
        public void playAsMagician() { }

        @Override
        public void playAsKing() { }

        @Override
        public void playAsBishop() { }

        @Override
        public void playAsMerchant() { }

        @Override
        public void playAsArchitect() { }

        @Override
        public void playAsWarlord() { }

    };

    Player player3 = new Player("Noa", cardsPlayer3, pile, bank, events) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public void chooseCharacter(CharacterCardsList characters) {
            this.setCharacter(characters.get(3));
        }

        @Override
        public void playResourcesPhase() { }

        @Override
        public void playBuildingPhase() {
            this.addCardsToCity(this.getHand());
        }

        @Override
        public void playAsAssassin() { }

        @Override
        public void playAsThief() { }

        @Override
        public void playAsMagician() { }

        @Override
        public void playAsKing() { }

        @Override
        public void playAsBishop() { }

        @Override
        public void playAsMerchant() { }

        @Override
        public void playAsArchitect() { }

        @Override
        public void playAsWarlord() { }

    };

    Player player4 = new Player("Luk", cardsPlayer4, pile, bank, events) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public void chooseCharacter(CharacterCardsList characters) {
            this.setCharacter(characters.get(4));
        }

        @Override
        public void playResourcesPhase() { }

        @Override
        public void playBuildingPhase() {
            this.addCardsToCity(this.getHand());
        }

        @Override
        public void playAsAssassin() { }

        @Override
        public void playAsThief() { }

        @Override
        public void playAsMagician() { }

        @Override
        public void playAsKing() { }

        @Override
        public void playAsBishop() { }

        @Override
        public void playAsMerchant() { }

        @Override
        public void playAsArchitect() { }

        @Override
        public void playAsWarlord() { }

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
}