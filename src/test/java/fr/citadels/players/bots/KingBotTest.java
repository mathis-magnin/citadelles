package fr.citadels.players.bots;

import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.KingCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class KingBotTest {
    /*same as random bot for now*/

    @Mock
    Random random = mock(Random.class);
    KingBot player1;
    DistrictCardsPile pile;
    Display events;
    Bank bank;

    @BeforeEach
    void setUp() {
        pile = new DistrictCardsPile();
        bank = new Bank();
        events = new Display();
        List<DistrictCard> districts = new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[12], DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[22]));
        player1 = new KingBot("Hello1", districts, pile, bank, events);
    }

    @Test
    void createBot() {
        assertEquals("Hello1", player1.getName());
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getHand().get(2).getCardName());

    }


    @Test
    void chooseCardAmongDrawn() {
        //two NOBLE cards
        DistrictCard[] drawnCards = new DistrictCard[2];
        drawnCards[0] = DistrictCardsPile.allDistrictCards[0];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[5];
        DistrictCard cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Manoir", cardToPlay.getCardName());
        assertEquals(1, pile.size());
        assertEquals("Château", pile.get(0).getCardName());

        //two cards, one NOBLE
        drawnCards[0] = DistrictCardsPile.allDistrictCards[30];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[6];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Château", cardToPlay.getCardName());
        assertEquals(2, pile.size());
        assertEquals("Château", pile.get(0).getCardName());
        assertEquals("Échoppe", pile.get(1).getCardName());

        //two cards, no NOBLE
        drawnCards[0] = DistrictCardsPile.allDistrictCards[31];
        drawnCards[1] = DistrictCardsPile.allDistrictCards[65];
        cardToPlay = player1.chooseCardAmongDrawn(drawnCards);
        assertEquals("Échoppe", cardToPlay.getCardName());
        assertEquals(3, pile.size());
        assertEquals("Château", pile.get(0).getCardName());
        assertEquals("Échoppe", pile.get(1).getCardName());
        assertEquals("Université", pile.get(2).getCardName());


    }

    @Test
    void chooseCardInHand() {
        //no money
        DistrictCard cardToPlay = player1.chooseCardInHand();
        assertNull(cardToPlay);
        assertEquals(3, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getHand().get(2).getCardName());

        //Manoir

        player1.addGold(3);

        cardToPlay = player1.chooseCardInHand();
        assertEquals("Manoir", cardToPlay.getCardName());
        assertEquals(2, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getHand().get(1).getCardName());


    }

    @Test
    void play() {
        this.player1.setCharacter(new KingCard());
        pile.initializePile();

        //take gold because no money
        player1.play();
        assertEquals(1, player1.getGold());
        assertEquals(2, player1.getHand().size());
        assertEquals("Manoir", player1.getHand().get(0).getCardName());
        assertEquals("Cathédrale", player1.getHand().get(1).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());

        //take cards because money
        player1.play();
        assertEquals(1, player1.getGold()); // 1+2-3+1(gold from family)
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());

        //take money because money needed
        player1.play();
        assertEquals(4, player1.getGold());
        assertEquals(1, player1.getHand().size());
        assertEquals("Cathédrale", player1.getHand().get(0).getCardName());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());

        //take cards because money needed
        player1.play();
        assertEquals(2, player1.getGold()); // 4+2-5+1
        assertEquals(0, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());
        assertEquals("Cathédrale", player1.getCity().get(2).getCardName());

        //take cards because no money
        player1.play();
        assertEquals(3, player1.getGold());
        assertEquals(1, player1.getHand().size());
        assertEquals("Temple", player1.getCity().get(0).getCardName());
        assertEquals("Manoir", player1.getCity().get(1).getCardName());
        assertEquals("Cathédrale", player1.getCity().get(2).getCardName());


    }

    @Test
    void chooseCharacter() {
        CharacterCardsList characters = new CharacterCardsList();
        player1.chooseCharacter(characters);
        assertEquals("Roi", player1.getCharacter().getCardName());
        assertEquals(7, characters.size());

        player1.chooseCharacter(characters);
        assertEquals("Assassin", player1.getCharacter().getCardName());
        assertEquals(6, characters.size());
    }

}