package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(new DistrictCard("Temple"), new DistrictCard("Manoir"), new DistrictCard("Cathédrale")));
        player = new BotFirstStrategy("Hello",districts);
    }

    @Test
    void getName() {
        assertEquals("Hello", player.getName());
    }


    @Test
    void getCardsInHand() {
        assertEquals(3, player.getCardsInHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getCardsInHand().get(0).getCardName());
        assertEquals("Manoir", player.getCardsInHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getCardsInHand().get(2).getCardName());
    }


    @Test
    void putBack() {
        DistrictCard[] drawnCards = new DistrictCard[3];
        drawnCards[0] = new DistrictCard("Temple");
        drawnCards[1] = new DistrictCard("Manoir");
        drawnCards[2] = new DistrictCard("Cathédrale");
        DistrictCardsPile pile = new DistrictCardsPile();
        player.putBack(drawnCards, pile, 1);
        for(int i = 0; i<drawnCards.length; i++) {
            if(i==1) {
                assertEquals("Manoir", drawnCards[i].getCardName());
            }
            else {
                assertNull(drawnCards[i]);
            }
        }


    }

}