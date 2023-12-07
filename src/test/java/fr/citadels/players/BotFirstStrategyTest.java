package fr.citadels.players;

import fr.citadels.cards.Card;
import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BotFirstStrategyTest {

    Player player;

    @BeforeEach
    void setUp() {
        List<DistrictCard> districts = new ArrayList<>(List.of(new DistrictCard("Temple"), new DistrictCard("Manoir"), new DistrictCard("Cathédrale")));
        player = new BotFirstStrategy("Hello", districts);
    }

    @Test
    void initializeBot() {
        assertEquals("Hello", player.getName());
        assertEquals(3, player.getCardsInHand().size());
        /*check if the elements are in the list*/
        assertEquals("Temple", player.getCardsInHand().get(0).getCardName());
        assertEquals("Manoir", player.getCardsInHand().get(1).getCardName());
        assertEquals("Cathédrale", player.getCardsInHand().get(2).getCardName());
        assertEquals(0, player.getCityCards().size());

    }

    @Test
    void chooseCard() {
        DistrictCardsPile pile = new DistrictCardsPile();
        DistrictCard[] drawnCards = pile.draw(2);
        DistrictCard cardToPlay = player.chooseCard(pile, drawnCards);
        for (Card card : drawnCards)
            if (card != null) assertEquals(cardToPlay, card);

    }

    @Test
    void play() {
        DistrictCardsPile pile = new DistrictCardsPile();
        String turn=player.play(pile);
        assertTrue(player.getCityCards().isEmpty() || player.getCityCards().size() == 1);
        if (player.getCityCards().size() == 1) {
            assertEquals(3, player.getCardsInHand().size());
            assertTrue(turn.equals(player.getName() + " a ajouté a sa ville : Temple")
                    || turn.equals(player.getName() + " a ajouté a sa ville : Manoir")
                    || turn.equals(player.getName() + " a ajouté a sa ville : Cathédrale"));
        }
        else{
            assertEquals(4, player.getCardsInHand().size());
            assertEquals(turn, player.getName() + " n'a pas construit ce tour-ci");
        }
    }


}