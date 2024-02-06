package fr.citadels.engine.score;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Statisticboard {

    /* Attribute */

    private Statistic[] statistics;


    /* Constructor */

    public Statisticboard(int playerNumber) {
        this.statistics = new Statistic[playerNumber];
    }

    public Statisticboard(Statistic[] statistics) {
        this.statistics = statistics;
    }


    /* Basic methods */

    /**
     * Initialize the statisticboard.
     *
     * @param players an array of player
     * @precondition players should be the same size as statistics.
     */
    public void initialize(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            this.statistics[i] = new Statistic(players[i]);
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Statistic statistic : this.statistics) {
            str.append(statistic.toString());
        }
        return str.toString();
    }


    public Statistic[] getStatistics() {
        return this.statistics;
    }


    /* Methods */

    /**
     * Update all the statistic within the statisticboard based on the new scoreboard.
     *
     * @param scoreboard the scoreboard of the game
     */
    public void update(Scoreboard scoreboard) {
        for (Score score : scoreboard.getScores()) {
            for (Statistic statistic : this.statistics) {
                if (statistic.getPlayer().equals(score.getPlayer())) {
                    statistic.update(score, scoreboard.getWinner().equals(statistic.getPlayer()));
                }
            }
        }
    }

    /**
     * Write the statisticboard into a csv file.
     *
     * @param file the file to write the statisticboard into
     * @throws IOException if the file is not found
     */
    public void writeCsv(File file) throws IOException {
            FileWriter fileWriter = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fileWriter);

            writer.writeNext(new String[]{"Nombre de parties jouées : ", "" + (this.statistics[0].getGameNumber())});
            fileWriter.write("\n");

            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"Player", "Win number", "Win percentage", "Defeat number", "Defeat percentage", "Total Score", "Average score"});
            for (Statistic statistic : this.statistics) {
                data.add(new String[]{statistic.getPlayer().getName(), String.valueOf(statistic.getWinNumber()), String.valueOf(statistic.getWinPercentage()), String.valueOf(statistic.getDefeatNumber()), String.valueOf(statistic.getDefeatPercentage()), String.valueOf(statistic.getTotalScore()), String.valueOf(statistic.getAverageScore())});
            }
            writer.writeAll(data);
            writer.close();
    }

    /**
     * Read a statisticboard in a csv file and return it.
     *
     * @param file the file to read
     * @return a statisticboard
     * @throws IOException if the file is not found
     * @throws CsvValidationException if the csv file is not valid
     */
    public static Statisticboard readCsv(File file) throws IOException, CsvValidationException {
        FileReader fileReader = new FileReader(file);
        CSVReader reader = new CSVReader(fileReader);

        Game game = new Game();
        double nbGame = 0;
        String[] nextLine = reader.readNext();
        nbGame = Double.parseDouble(nextLine[1]);
        reader.readNext();
        reader.readNext();
        int nbPlayer = 0;

        List<Statistic> statistics = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Player player = new Monarchist(nextLine[0], new ArrayList<>(), game);
            Statistic statistic = new Statistic(player, nbGame, Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[5]));
            statistics.add(statistic);
            nbPlayer++;
        }

        return new Statisticboard(statistics.toArray(new Statistic[nbPlayer]));

    }
}
