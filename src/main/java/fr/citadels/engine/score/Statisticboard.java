package fr.citadels.engine.score;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import fr.citadels.Main;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;

import java.io.*;
import java.text.DecimalFormat;
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
        for (Statistic statistic: this.statistics) {
            str.append(statistic.toString()).append("\n");
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
     * If the csv option is activated, read and parse the statisticboard from a file or create a new one if the file does not exist.
     * Else, create a new statisticboard.
     * @param file the file to read
     * @param players an array of players
     * @param inSecondIteration a boolean to know if we are in the second iteration of the thousand games
     * @return a statisticboard
     */
    public static Statisticboard readOrCreateStatisticboard(File file, Player[] players, boolean inSecondIteration) throws CsvValidationException, IOException {
        Statisticboard statisticboard;

        if (Main.csv) {
            if (file.exists()) {
                statisticboard = Statisticboard.readCsv(file, players, inSecondIteration);
            } else {
                statisticboard = new Statisticboard(Game.NB_PLAYERS);
                statisticboard.initialize(players);
                System.out.println("Fichier non trouvé");
            }
        } else {
            statisticboard = new Statisticboard(Game.NB_PLAYERS);
            statisticboard.initialize(players);
            System.out.println("Csv non demandé");
        }
        return statisticboard;
    }


    /**
     * Write the statisticboard into a csv file.
     *
     * @param file the file to write the statisticboard into
     * @throws IOException if the file is not found
     */
    public void writeCsv(File file, boolean inSecondIteration) throws IOException {
        if (!inSecondIteration) {
            file.delete();
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, inSecondIteration);
        CSVWriter writer = new CSVWriter(fileWriter);
        DecimalFormat df = new DecimalFormat("#.###");

        if (inSecondIteration) {
            writer.writeNext(new String[]{});
        }

        writer.writeNext(new String[]{"Nombre de parties jouées : ", (df.format(this.statistics[0].getGameNumber()))});
        fileWriter.write("\n");

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Player", "Win number", "Win percentage", "Defeat number", "Defeat percentage", "Total Score", "Average score"});
        for (Statistic statistic : this.statistics) {
            data.add(new String[]{
                    statistic.getPlayer().getName(),
                    String.valueOf(df.format(statistic.getWinNumber())),
                    String.valueOf(df.format(statistic.getWinPercentage())),
                    String.valueOf(df.format(statistic.getDefeatNumber())),
                    String.valueOf(df.format(statistic.getDefeatPercentage())),
                    String.valueOf(df.format(statistic.getTotalScore())),
                    String.valueOf(df.format(statistic.getAverageScore()))});
        }
        writer.writeAll(data);
        writer.close();
    }

    /**
     * Read a statisticboard in a csv file and return it.
     * If it's not a valid file or if the number of players or the players' name are not the same as the current game, create a new statisticboard.
     *
     * @param file the file to read
     * @param players an array of players
     * @param inSecondIteration a boolean to know if we are in the second iteration of the thousand games
     * @return a statisticboard
     * @throws IOException if the file is not found
     * @throws CsvValidationException if the csv file is not valid
     */
    public static Statisticboard readCsv(File file, Player[] players, boolean inSecondIteration ) throws CsvValidationException, IOException {
        FileReader fileReader = new FileReader(file);
        CSVReader reader = new CSVReader(fileReader);

        if (inSecondIteration) {
            for (int i = 0; i < players.length + 4; i++)
                reader.readNext();
        }

        double nbGame = 0;
        String[] nextLine = reader.readNext();
        if (nextLine == null || nextLine.length < 2) {
            Statisticboard statisticboard = new Statisticboard(Game.NB_PLAYERS);
            statisticboard.initialize(players);
            return statisticboard;
        }

        nbGame = Double.parseDouble(nextLine[1]);
        reader.readNext();
        reader.readNext();
        int nbPlayer = 0;

        List<Statistic> statistics = new ArrayList<>();
        while (((nextLine = reader.readNext()) != null) && (nextLine.length == 7)){
            if ((nbPlayer >= Game.NB_PLAYERS) || (!nextLine[0].equals(players[nbPlayer].getName()))) {
                Statisticboard statisticboard = new Statisticboard(Game.NB_PLAYERS);
                statisticboard.initialize(players);
                return statisticboard;
            }
            Player player = new Monarchist(nextLine[0]);
            Statistic statistic = new Statistic(player, nbGame, Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[5]));
            statistics.add(statistic);
            nbPlayer++;
        }
        if (nbPlayer != Game.NB_PLAYERS) {
            file.delete();
            file.createNewFile();
            Statisticboard statisticboard = new Statisticboard(Game.NB_PLAYERS);
            statisticboard.initialize(players);
            return statisticboard;
        }

        return new Statisticboard(statistics.toArray(new Statistic[nbPlayer]));
    }
}
