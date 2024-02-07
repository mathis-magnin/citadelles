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


    /* Command line parameters */

    @Parameter(names = "--demo", description = "Demonstration of a single game")
    public static boolean demo = false;

    @Parameter(names = "--2thousands", description = "Simulation of two thousands games")
    public static boolean twoThousands = false;

    @Parameter(names = "--csv", description = "Run several games and add the statistics to the csv file")
    public static boolean csv = false;


    /* Programs */

    public static void main(String... argv) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(argv);
        if (demo || !(twoThousands || csv)) {
            main.runDemonstration();
        } else
        if (twoThousands) {
            main.runTwoThousands();
        } else
        if (csv) {
            // main.runCsv();
        }
    }


    /**
     * Run a single game with a full log printed.
     */
    public void runDemonstration() {
        Player[] players = new Player[Game.NB_PLAYERS];
        players[0] = new Uncertain("HASARDEUX", RAND);
        players[1] = new Spendthrift("DÉPENSIER", RAND);
        players[2] = new Thrifty("ÉCONOME", RAND);
        players[3] = new Monarchist("MONARCHISTE");
        players[4] = new Richard("RICHARD");


        Game game = new Game(players, RAND);
        game.play();
    }


    /**
     * This program launch 2000 games as follows :
     * 1. Simulation of 1000 games of the best bot against the second best (with others bots to complete).
     * 2. Simulation of 1000 games of the best bot against itself (or as many clone of itself than players).
     */
    public void runTwoThousands() {
        Logger logger = org.apache.logging.log4j.LogManager.getLogger(Main.class);

        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());

        // Initialize the first series of thousand games

        Player[] players1 = new Player[Game.NB_PLAYERS];
        players1[0] = new Thrifty("ÉCONOME_1", RAND);
        players1[1] = new Thrifty("ÉCONOME_2", RAND);
        players1[2] = new Thrifty("ÉCONOME_3", RAND);
        players1[3] = new Thrifty("ÉCONOME_4", RAND);
        players1[4] = new Thrifty("ÉCONOME_5", RAND);

        Statisticboard statisticboard1 = readOrCreateStatisticboard(file, players1, false);

        // Initialize the second series of thousand games

        Player[] players2 = new Player[Game.NB_PLAYERS];
        players2[0] = new Spendthrift("DÉPENSIER_1", RAND);
        players2[1] = new Spendthrift("DÉPENSIER_2", RAND);
        players2[2] = new Spendthrift("DÉPENSIER_3", RAND);
        players2[3] = new Thrifty("ÉCONOME_1", RAND);
        players2[4] = new Thrifty("ÉCONOME_2", RAND);

        Statisticboard statisticboard2 = readOrCreateStatisticboard(file, players2, true);

        // Play the first series of thousand games
        logger.info("\n- Partie avec plusieurs fois le meilleur bot\n\n");

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players1, RAND);
            game.play();
            statisticboard1.update(game.getScoreboard());
        }
        logger.info(statisticboard1);

        if (csv) {
            try {
                statisticboard1.writeCsv(file, false);
            } catch (IOException e) {
                System.out.println("Erreur lors de l'écriture du fichier");
            }
        }

        // Play the second series of thousand games
        logger.info("\n- Partie entre le meilleur bot et le second meilleur bot\n\n");

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players2, RAND);
            game.play();
            statisticboard2.update(game.getScoreboard());
        }
        logger.info(statisticboard2);

        if (csv) {
            try {
                statisticboard2.writeCsv(file, true);
            } catch (IOException e) {
                System.out.println("Erreur lors de l'écriture du fichier");
            }
        }
    }


    /**
     * Run several simulations while reading “stats/gamestats.csv” if it exists and adding new statistics.
     */
    public void runCsv() throws IOException, CsvValidationException {
        System.out.println("csv");

        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());
        if (file.exists()) {
            //Statisticboard stats = Statisticboard.readCsv(file, players);
            //System.out.println(stats);
        } else if (file.createNewFile()) {
            System.out.println("File created");
        }

/*
        Statisticboard stats = new Statisticboard(4);
        Game game = new Game();
        Player[] players = new Player[]{new Monarchist("player1", new ArrayList<>(), game), new Monarchist("player2", new ArrayList<>(), game), new Monarchist("player3", new ArrayList<>(), game), new Monarchist("player4", new ArrayList<>(), game)};
        stats.initialize(players);
        stats.writeCsv(file);

 */

        //Statisticboard stats = Statisticboard.readCsv(file);
        //System.out.println(stats);

    }
}