package fr.citadels.engine;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.players.RandomBot;
import fr.citadels.players.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrownTest {

    @Mock Random RAND=mock(Random.class);
    @Test
    void crownTest() {
        Crown crown = new Crown();
        Player player = new RandomBot("player", new ArrayList<DistrictCard>(),new Random());
        Player player2 = new RandomBot("player2", new ArrayList<DistrictCard>(),new Random());

        crown.initializeCrown(RAND);
        assertNotEquals(-1, crown.getCrownedPlayerIndex());

        crown.setCrownedPlayerIndex(1);
        assertEquals(1, crown.getCrownedPlayerIndex());
    }

    @Test
    void getKingPlayerTest() {
        Crown crown = new Crown();
        Player player = new RandomBot("player", new ArrayList<DistrictCard>(),new Random());
        Player player2 = new RandomBot("player2", new ArrayList<DistrictCard>(),new Random());
        Player player3 = new RandomBot("player3", new ArrayList<DistrictCard>(),new Random());

        Player[] players = {player, player2,player3};

        assertEquals(-1, crown.getKingPlayer(players));
        Player player2Spy=spy(player2);
        players[1]=player2Spy;


        when(player2Spy.getCharacter()).thenReturn(new CharacterCard("Roi", 4));
        assertEquals(1, crown.getKingPlayer(players));

    }

    @Test
    void defineNextCrownedPlayer(){
        Crown crown = new Crown();
        Player player = new RandomBot("player", new ArrayList<DistrictCard>(),new Random());
        Player player2 = new RandomBot("player2", new ArrayList<DistrictCard>(),new Random());
        Player player3 = new RandomBot("player3", new ArrayList<DistrictCard>(),new Random());
        Player playerSpy=spy(player);
        Player playerSpy2=spy(player2);
        Player[] players = {playerSpy, playerSpy2,player3};

        //set the crown to the player 3
        when(RAND.nextInt(Game.NB_PLAYERS)).thenReturn(2);
        crown.defineNextCrownedPlayer(players,RAND);
        assertEquals(2, crown.getCrownedPlayerIndex());

        //set the crown to the king
        when(playerSpy.getCharacter()).thenReturn(new CharacterCard("Roi", 4));
        crown.defineNextCrownedPlayer(players,RAND);
        assertEquals(0, crown.getCrownedPlayerIndex());

        //Still the same player because no king
        when(playerSpy.getCharacter()).thenReturn(new CharacterCard("Assassin", 1));
        crown.defineNextCrownedPlayer(players,RAND);
        assertEquals(0, crown.getCrownedPlayerIndex());

        //switch to player two because he is the king
        when(playerSpy2.getCharacter()).thenReturn(new CharacterCard("Roi", 4));
        crown.defineNextCrownedPlayer(players,RAND);
        assertEquals(1, crown.getCrownedPlayerIndex());

    }
}