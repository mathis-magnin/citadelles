package fr.citadels.players;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import fr.citadels.gameelements.cards.charactercards.characters.KingCard;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PlayerTest {

    Player player;
    Game game;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[18], DistrictCardsPile.allDistrictCards[63], DistrictCardsPile.allDistrictCards[62]));
        player = new Player("Hello", districts, new Game()) {
            @Override
            public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
                return drawnCards[0];
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
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public void playAsAssassin() {
            }

            @Override
            public void playAsWarlord() {
            }

            @Override
            public void playAsMerchant() {
            }

            @Override
            public void playAsArchitect() {
            }

            @Override
            public void playAsBishop() {
            }

            @Override
            public void playAsKing() {
            }

            @Override
            public void playAsMagician() {
            }

            @Override
            public void playAsThief() {
            }
        };
    }

    @Test
    void getTarget() {
        assertNull(player.getTarget());
        KingCard king = new KingCard();
        player.setTarget(king);
        assertEquals(king, player.getTarget());
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

        player.play();
        assertEquals(1, player.getCity().size());
        assertEquals("Temple", player.getCity().get(0).getCardName());

        player.play();
        assertEquals(2, player.getCity().size());
        assertEquals("Manoir", player.getCity().get(1).getCardName());

        player.play();
        assertEquals(3, player.getCity().size());
        assertEquals("Cathédrale", player.getCity().get(2).getCardName());

    }

    @Test
    void hasCompleteCity() {
        while (player.getCity().size() < 7) {
            assertFalse(player.hasCompleteCity());
            player.play();
        }
        assertTrue(player.hasCompleteCity());
    }

    @Test
    void hasCardInHand() {
        player.play();
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
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                this.setCharacter(characters.get(2));
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public void playAsAssassin() {
            }

            @Override
            public void playAsWarlord() {
            }

            @Override
            public void playAsMerchant() {
            }

            @Override
            public void playAsArchitect() {
            }

            @Override
            public void playAsBishop() {
            }

            @Override
            public void playAsKing() {
            }

            @Override
            public void playAsMagician() {
            }

            @Override
            public void playAsThief() {
            }
        };
        player.play();
        player2.play();

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
            public DistrictCard chooseCardInHand() {
                return null;
            }

            @Override
            public void chooseCharacter(CharacterCardsList characters) {
                this.setCharacter(characters.get(2));
            }

            @Override
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public void playAsAssassin() {
            }

            @Override
            public void playAsWarlord() {
            }

            @Override
            public void playAsMerchant() {
            }

            @Override
            public void playAsArchitect() {
            }

            @Override
            public void playAsBishop() {
            }

            @Override
            public void playAsKing() {
            }

            @Override
            public void playAsMagician() {
            }

            @Override
            public void playAsThief() {
            }
        };
        player.play();
        player2.play();
        assertEquals(player, player2);
        assertEquals(player2, player);

        //test with different name
        player2 = new Player("Hello2", districts2, game) {
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
            public void play() {
                this.chooseCharacter(new CharacterCardsList());
                getActions().addCardToCity(getHand().get(0));
                getActions().removeCardFromHand(0);
            }

            @Override
            public void playAsAssassin() {
            }

            @Override
            public void playAsWarlord() {
            }

            @Override
            public void playAsMerchant() {
            }

            @Override
            public void playAsArchitect() {
            }

            @Override
            public void playAsBishop() {
            }

            @Override
            public void playAsKing() {
            }

            @Override
            public void playAsMagician() {
            }

            @Override
            public void playAsThief() {
            }

        };


        assertNotEquals(player, player2);
        assertNotEquals(player2, player);

    }


}