package fr.citadels.engine.score;

import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticTest {

    Game game;
    Player player;
    Statistic statistic;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();
        player = new Monarchist("Bob", new ArrayList<>(), game);
        statistic = new Statistic(player);
    }

    @Test
    void update() {
        double tau = 0.0001;

        assertEquals(0, statistic.getWinNumber());
        assertEquals(0, statistic.getWinPercentage());
        assertEquals(0, statistic.getDefeatPercentage());
        assertEquals(0, statistic.getAverageScore());


        Score score1 = mock(Score.class);
        when(score1.getPlayer()).thenReturn(player);
        when(score1.getPoints()).thenReturn(50);
        statistic.update(score1, true);

        assertTrue(Math.abs(1.0 - statistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(100.0 - statistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(0.0 - statistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(50.0 - statistic.getAverageScore()) <= tau);


        Score score2 = mock(Score.class);
        when(score2.getPlayer()).thenReturn(player);
        when(score2.getPoints()).thenReturn(25);
        statistic.update(score2, true);

        assertTrue(Math.abs(2.0 - statistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(100.0 - statistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(0.0 - statistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((50.0 + 25.0) / 2.0) - statistic.getAverageScore()) <= tau);


        Score score3 = mock(Score.class);
        when(score3.getPlayer()).thenReturn(player);
        when(score3.getPoints()).thenReturn(2);
        statistic.update(score3, false);

        assertTrue(Math.abs(2.0 - statistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(((100.0 / 3.0) * 2.0) - statistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs((100.0 / 3.0) - statistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((50.0 + 25.0 + 2.0) / 3.0) - statistic.getAverageScore()) <= tau);


        Score score4 = mock(Score.class);
        when(score4.getPlayer()).thenReturn(player);
        when(score4.getPoints()).thenReturn(19);
        statistic.update(score4, false);

        assertTrue(Math.abs(2.0 - statistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(50.0 - statistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(50.0 - statistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((50.0 + 25.0 + 2.0 + 19.0) / 4.0) - statistic.getAverageScore()) <= tau);


        Score score5 = mock(Score.class);
        when(score5.getPlayer()).thenReturn(player);
        when(score5.getPoints()).thenReturn(31);
        statistic.update(score5, true);

        assertTrue(Math.abs(3.0 - statistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(((100.0 / 5.0) * 3.0) - statistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(((100.0 / 5.0) * 2.0) - statistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((50.0 + 25.0 + 2.0 + 19.0 + 31.0) / 5.0) - statistic.getAverageScore()) <= tau);

    }

}