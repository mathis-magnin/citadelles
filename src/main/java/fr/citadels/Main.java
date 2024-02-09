package fr.citadels;

import com.beust.jcommander.JCommander;
import com.opencsv.exceptions.CsvValidationException;
import fr.citadels.engine.Game;
import com.beust.jcommander.Parameter;
import fr.citadels.engine.score.Statisticboard;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.citadels.players.bots.*;
import org.apache.logging.log4j.Logger;

import static fr.citadels.engine.score.Statisticboard.readOrCreateStatisticboard;

public class Main {
    private static final java.util.Random RAND = new java.util.Random();
    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(Main.class);


    /* Command line parameters */

    @Parameter(names = "--demo", description = "Demonstration of a single game")
    public static boolean demo = false;

    @Parameter(names = "--2thousands", description = "Simulation of two thousands games")
    public static boolean twoThousands = false;

    @Parameter(names = "--csv", description = "Run several games and add the statistics to the csv file")
    public static boolean csv = false;

    // optional parameter
    @Parameter(names = "--length", description = "Number of games to play")
    private int iterationAmount = 100000;

    /**
     * Initialize the players for the game.
     *
     * @return the players for the game
     */
    public static Player[] initPlayer() {
        Player[] players = new Player[Game.PLAYER_NUMBER];
        players[0] = new Uncertain("HASARDEUX", RAND);
        players[1] = new Spendthrift("DÉPENSIER", RAND);
        players[2] = new Thrifty("ÉCONOME", RAND);
        players[3] = new Monarchist("MONARCHISTE");
        players[4] = new Richard("RICHARD");
        return players;
    }

    /* Programs */

    public static void main(String... argv) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(argv);
        if (demo || !(twoThousands || csv)) {
            main.runDemonstration();
        } else if (twoThousands) {
            main.runTwoThousands();
        } else {
            main.runCsv();
        }
    }

    /**
     * Write the statistics in the csv file.
     *
     * @param file              the file to write
     * @param statisticboardCSV the statisticboard to write
     * @param iteration         true if the statisticboard is for the second series of thousand games
     */
    public static void writeCsv(File file, Statisticboard statisticboardCSV, boolean iteration) {
        if (csv) {
            try {
                statisticboardCSV.writeCsv(file, iteration);
            } catch (IOException e) {
                logger.info("Erreur lors de l'écriture du fichier\n\n");
            }
        }
    }

    /**
     * Read the statisticboard from the csv file if it exists, otherwise create a new one.
     *
     * @param file      the file to read
     * @param players   the players of the game
     * @param iteration true if the statisticboard is for the second series of thousand games
     * @return the statisticboard read or created
     */
    public static Statisticboard secureReadOrCreateStatisticboard(File file, Player[] players, boolean iteration) {
        try {
            return readOrCreateStatisticboard(file, players, iteration);
        } catch (IOException | CsvValidationException e) {
            logger.info("Erreur lors de la lecture du fichier\n\n");
            Statisticboard statisticboard = new Statisticboard(Game.PLAYER_NUMBER);
            statisticboard.initialize(players);
            return statisticboard;
        }
    }

    /**
     * Run a single game with a full log printed.
     */
    public void runDemonstration() {
        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());

        Player[] players = initPlayer();

        Statisticboard statisticboard;
        statisticboard = secureReadOrCreateStatisticboard(file, players, false);
        Game game = new Game(players, RAND);
        game.play();
        statisticboard.update(game.getScoreboard());

        writeCsv(file, statisticboard, false);
    }


    /**
     * This program launch 2000 games as follows :
     * 1. Simulation of 1000 games of the best bot against the second best (with others bots to complete).
     * 2. Simulation of 1000 games of the best bot against itself (or as many clone of itself than players).
     */
    public void runTwoThousands() {
        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());

        // Initialize the first series of thousand games

        Player[] players1 = new Player[Game.PLAYER_NUMBER];
        for (int i = 0; i < Game.PLAYER_NUMBER; i++) {
            players1[i] = new Thrifty("ÉCONOME_" + (i + 1), RAND);
        }

        Statisticboard statisticboard1 = new Statisticboard(Game.PLAYER_NUMBER);
        statisticboard1.initialize(players1);

        Statisticboard statisticboardCSV1;
        statisticboardCSV1 = secureReadOrCreateStatisticboard(file, players1, false);

        // Initialize the second series of thousand games

        Player[] players2 = initPlayer();

        Statisticboard statisticboard2 = new Statisticboard(Game.PLAYER_NUMBER);
        statisticboard2.initialize(players2);

        Statisticboard statisticboardCSV2;
        statisticboardCSV2 = secureReadOrCreateStatisticboard(file, players2, true);

        // Play the first series of thousand games
        logger.info("\n- Partie avec plusieurs fois le meilleur bot\n\n");

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players1, RAND);
            game.play();
            statisticboard1.update(game.getScoreboard());
            statisticboardCSV1.update(game.getScoreboard());
        }
        logger.info(statisticboard1);

        writeCsv(file, statisticboardCSV1, false);

        // Play the second series of thousand games
        logger.info("\n- Partie entre le meilleur bot et le second meilleur bot\n\n");

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players2, RAND);
            game.play();
            statisticboard2.update(game.getScoreboard());
            statisticboardCSV2.update(game.getScoreboard());
        }
        logger.info(statisticboard2);

        writeCsv(file, statisticboardCSV2, true);


    }


    /**
     * Run several simulations while reading “stats/gamestats.csv” if it exists and adding new statistics.
     */
    public void runCsv() {
        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());

        // Initialize the hundred thousand games
        Player[] players = initPlayer();
        Statisticboard statisticboard = new Statisticboard(Game.PLAYER_NUMBER);
        statisticboard.initialize(players);
        Statisticboard statisticboardCSV = secureReadOrCreateStatisticboard(file, players, false);

        // Play the hundred thousand games
        logger.info("\nStatistiques sur ");
        logger.info(iterationAmount);
        logger.info(" parties\n\n");

        for (int i = 0; i < iterationAmount; i++) {
            Game game = new Game(players, RAND);
            game.play();
            statisticboardCSV.update(game.getScoreboard());
            statisticboard.update(game.getScoreboard());
        }
        logger.info(statisticboard);

        writeCsv(file, statisticboardCSV, false);
    }
}