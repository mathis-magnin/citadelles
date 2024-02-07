package fr.citadels.engine.score;

import com.opencsv.exceptions.CsvValidationException;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import fr.citadels.players.bots.Thrifty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticboardTest {

    Game game;
    Player bob;
    Player jan;
    Player lou;

    Statisticboard statisticboard;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();

        bob = new Monarchist("bob", new ArrayList<>(), game);
        jan = new Monarchist("jan", new ArrayList<>(), game);
        lou = new Monarchist("lou", new ArrayList<>(), game);

        statisticboard = new Statisticboard(3);
        statisticboard.initialize(new Player[]{bob, jan, lou});
    }

    @Test
    void update() {
        double tau = 0.0001;

        Statistic bobStatistic = statisticboard.getStatistics()[0];
        Statistic janStatistic = statisticboard.getStatistics()[1];
        Statistic louStatistic = statisticboard.getStatistics()[2];

        /* Bob */
        assertEquals("bob", bobStatistic.getPlayer().getName());
        assertEquals(0, bobStatistic.getWinNumber());
        assertEquals(0, bobStatistic.getWinPercentage());
        assertEquals(0, bobStatistic.getDefeatPercentage());
        assertEquals(0, bobStatistic.getAverageScore());

        /* Jan */
        assertEquals("jan", janStatistic.getPlayer().getName());
        assertEquals(0, janStatistic.getWinNumber());
        assertEquals(0, janStatistic.getWinPercentage());
        assertEquals(0, janStatistic.getDefeatPercentage());
        assertEquals(0, janStatistic.getAverageScore());

        /* Lou */
        assertEquals("lou", louStatistic.getPlayer().getName());
        assertEquals(0, louStatistic.getWinNumber());
        assertEquals(0, louStatistic.getWinPercentage());
        assertEquals(0, louStatistic.getDefeatPercentage());
        assertEquals(0, louStatistic.getAverageScore());


        /* Game 1 */
        Score bobScore1 = mock(Score.class);
        when(bobScore1.getPoints()).thenReturn(10);
        when(bobScore1.getPlayer()).thenReturn(bob);

        Score janScore1 = mock(Score.class);
        when(janScore1.getPoints()).thenReturn(20);
        when(janScore1.getPlayer()).thenReturn(jan);

        Score louScore1 = mock(Score.class);
        when(louScore1.getPoints()).thenReturn(30);
        when(louScore1.getPlayer()).thenReturn(lou);

        Scoreboard scoreboard1 = mock(Scoreboard.class);
        when(scoreboard1.getScores()).thenReturn(new Score[]{bobScore1, janScore1, louScore1});
        when(scoreboard1.getWinner()).thenReturn(lou);

        statisticboard.update(scoreboard1);

        /* Bob */
        assertTrue(Math.abs(0.0 - bobStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(0.0 - bobStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(1.0 - bobStatistic.getDefeatNumber()) <= tau);
        assertTrue(Math.abs(100.0 - bobStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(10.0 - bobStatistic.getAverageScore()) <= tau);

        /* Jan */
        assertTrue(Math.abs(0.0 - janStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(0.0 - janStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(1.0 - janStatistic.getDefeatNumber()) <= tau);
        assertTrue(Math.abs(100.0 - janStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(20.0 - janStatistic.getAverageScore()) <= tau);

        /* Lou */
        assertTrue(Math.abs(1.0 - louStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(100.0 - louStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(0.0 - louStatistic.getDefeatNumber()) <= tau);
        assertTrue(Math.abs(0.0 - louStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(30.0 - louStatistic.getAverageScore()) <= tau);


        /* Game 2 */
        Score bobScore2 = mock(Score.class);
        when(bobScore2.getPoints()).thenReturn(30);
        when(bobScore2.getPlayer()).thenReturn(bob);

        Score janScore2 = mock(Score.class);
        when(janScore2.getPoints()).thenReturn(10);
        when(janScore2.getPlayer()).thenReturn(jan);

        Score louScore2 = mock(Score.class);
        when(louScore2.getPoints()).thenReturn(20);
        when(louScore2.getPlayer()).thenReturn(lou);

        Scoreboard scoreboard2 = mock(Scoreboard.class);
        when(scoreboard2.getScores()).thenReturn(new Score[]{bobScore2, janScore2, louScore2});
        when(scoreboard2.getWinner()).thenReturn(bob);

        statisticboard.update(scoreboard2);

        /* Bob */
        assertTrue(Math.abs(1.0 - bobStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(50.0 - bobStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(50.0 - bobStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((10.0 + 30.0) / 2.0) - bobStatistic.getAverageScore()) <= tau);

        /* Jan */
        assertTrue(Math.abs(0.0 - janStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(0.0 - janStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(100.0 - janStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((20.0 + 10.0) / 2.0) - janStatistic.getAverageScore()) <= tau);

        /* Lou */
        assertTrue(Math.abs(1.0 - louStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs(50.0 - louStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(50.0 - louStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((30.0 + 20.0) / 2.0) - louStatistic.getAverageScore()) <= tau);


        /* Game 3 */
        Score bobScore3 = mock(Score.class);
        when(bobScore3.getPoints()).thenReturn(20);
        when(bobScore3.getPlayer()).thenReturn(bob);

        Score janScore3 = mock(Score.class);
        when(janScore3.getPoints()).thenReturn(30);
        when(janScore3.getPlayer()).thenReturn(jan);

        Score louScore3 = mock(Score.class);
        when(louScore3.getPoints()).thenReturn(10);
        when(louScore3.getPlayer()).thenReturn(lou);

        Scoreboard scoreboard3 = mock(Scoreboard.class);
        when(scoreboard3.getScores()).thenReturn(new Score[]{bobScore3, janScore3, louScore3});
        when(scoreboard3.getWinner()).thenReturn(jan);

        statisticboard.update(scoreboard3);

        /* Bob */
        assertTrue(Math.abs(1.0 - bobStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs((100.0 / 3.0) - bobStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(((100.0 / 3.0) * 2.0) - bobStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((10.0 + 30.0 + 20.0) / 3.0) - bobStatistic.getAverageScore()) <= tau);

        /* Jan */
        assertTrue(Math.abs(1.0 - janStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs((100.0 / 3.0) - janStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(((100.0 / 3.0) * 2.0) - janStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((20.0 + 10.0 + 30.0) / 3.0) - janStatistic.getAverageScore()) <= tau);

        /* Lou */
        assertTrue(Math.abs(1.0 - louStatistic.getWinNumber()) <= tau);
        assertTrue(Math.abs((100.0 / 3.0) - louStatistic.getWinPercentage()) <= tau);
        assertTrue(Math.abs(((100.0 / 3.0) * 2.0) - louStatistic.getDefeatPercentage()) <= tau);
        assertTrue(Math.abs(((30.0 + 20.0 + 10.0) / 3.0) - louStatistic.getAverageScore()) <= tau);

    }

    @Test
    void writeCsv() throws IOException, CsvValidationException {
        Path pathTestWrite = Paths.get("src/test/resources/testWrite.csv");
        File fileTestWrite = new File(pathTestWrite.toString());

        Path pathTestRead = Paths.get("src/test/resources/testRead.csv");
        File fileTestRead = new File(pathTestRead.toString());
        Statisticboard stats = Statisticboard.readCsv(fileTestRead);

        stats.writeCsv(fileTestRead);

        // GAME 1
        Score bobScore1 = mock(Score.class);
        when(bobScore1.getPoints()).thenReturn(10);
        when(bobScore1.getPlayer()).thenReturn(bob);

        Score janScore1 = mock(Score.class);
        when(janScore1.getPoints()).thenReturn(20);
        when(janScore1.getPlayer()).thenReturn(jan);

        Score louScore1 = mock(Score.class);
        when(louScore1.getPoints()).thenReturn(30);
        when(louScore1.getPlayer()).thenReturn(lou);

        Scoreboard scoreboard1 = mock(Scoreboard.class);
        when(scoreboard1.getScores()).thenReturn(new Score[]{bobScore1, janScore1, louScore1});
        when(scoreboard1.getWinner()).thenReturn(lou);

        statisticboard.update(scoreboard1);

        // GAME 2
        Score bobScore2 = mock(Score.class);
        when(bobScore2.getPoints()).thenReturn(30);
        when(bobScore2.getPlayer()).thenReturn(bob);

        Score janScore2 = mock(Score.class);
        when(janScore2.getPoints()).thenReturn(10);
        when(janScore2.getPlayer()).thenReturn(jan);

        Score louScore2 = mock(Score.class);
        when(louScore2.getPoints()).thenReturn(20);
        when(louScore2.getPlayer()).thenReturn(lou);

        Scoreboard scoreboard2 = mock(Scoreboard.class);
        when(scoreboard2.getScores()).thenReturn(new Score[]{bobScore2, janScore2, louScore2});
        when(scoreboard2.getWinner()).thenReturn(bob);

        statisticboard.update(scoreboard2);
        statisticboard.writeCsv(fileTestWrite);

        FileReader fileReader = new FileReader(fileTestWrite);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        assertEquals("\"Nombre de parties jouées : \",\"2.0\"", line);
        line = bufferedReader.readLine();
        assertEquals("", line);
        line = bufferedReader.readLine();
        assertEquals("\"Player\",\"Win number\",\"Win percentage\",\"Defeat number\",\"Defeat percentage\",\"Total Score\",\"Average score\"", line);
        line = bufferedReader.readLine();
        assertEquals("\"bob\",\"1.0\",\"50.0\",\"1.0\",\"50.0\",\"40.0\",\"20.0\"", line);
        line = bufferedReader.readLine();
        assertEquals("\"jan\",\"0.0\",\"0.0\",\"2.0\",\"100.0\",\"30.0\",\"15.0\"", line);
        line = bufferedReader.readLine();
        assertEquals("\"lou\",\"1.0\",\"50.0\",\"1.0\",\"50.0\",\"50.0\",\"25.0\"", line);
        line = bufferedReader.readLine();
        assertNull(line);


    }

    @Test
    void readCsv() throws CsvValidationException, IOException {
        Path pathTestRead = Paths.get("src/test/resources/testRead.csv");
        File fileTestRead = new File(pathTestRead.toString());

        Statisticboard stats = Statisticboard.readCsv(fileTestRead);
        assertEquals(5, stats.getStatistics().length);

        assertEquals("player1", stats.getStatistics()[0].getPlayer().getName());
        assertEquals(10, stats.getStatistics()[0].getGameNumber());
        assertEquals(5, stats.getStatistics()[0].getWinNumber());
        assertEquals(200, stats.getStatistics()[0].getTotalScore());

        assertEquals("player2", stats.getStatistics()[1].getPlayer().getName());
        assertEquals(10, stats.getStatistics()[1].getGameNumber());
        assertEquals(3, stats.getStatistics()[1].getWinNumber());
        assertEquals(180, stats.getStatistics()[1].getTotalScore());

        assertEquals("player3", stats.getStatistics()[2].getPlayer().getName());
        assertEquals(10, stats.getStatistics()[2].getGameNumber());
        assertEquals(0, stats.getStatistics()[2].getWinNumber());
        assertEquals(100, stats.getStatistics()[2].getTotalScore());

        assertEquals("player4", stats.getStatistics()[3].getPlayer().getName());
        assertEquals(10, stats.getStatistics()[3].getGameNumber());
        assertEquals(2, stats.getStatistics()[3].getWinNumber());
        assertEquals(150, stats.getStatistics()[3].getTotalScore());

        assertEquals("player5", stats.getStatistics()[4].getPlayer().getName());
        assertEquals(10, stats.getStatistics()[4].getGameNumber());
        assertEquals(0, stats.getStatistics()[4].getWinNumber());
        assertEquals(120, stats.getStatistics()[4].getTotalScore());
    }
}