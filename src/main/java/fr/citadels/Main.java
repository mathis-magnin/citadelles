package fr.citadels;

import com.beust.jcommander.JCommander;
import fr.citadels.engine.Game;
import com.beust.jcommander.Parameter;
import fr.citadels.engine.score.Statisticboard;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import fr.citadels.players.bots.Uncertain;
import fr.citadels.players.bots.Spendthrift;
import fr.citadels.players.bots.Thrifty;
import org.apache.logging.log4j.Logger;


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
        }
        if (twoThousands) {
            main.runTwoThousands();
        }
        if (csv) {
            main.runCsv();
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
        logger.info("\n- Partie avec plusieurs fois le meilleur bot\n\n");
        Statisticboard statisticboard = new Statisticboard(Game.NB_PLAYERS);

        Player[] players = new Player[Game.NB_PLAYERS];
        players[0] = new Thrifty("ÉCONOME_1", RAND);
        players[1] = new Thrifty("ÉCONOME_2", RAND);
        players[2] = new Thrifty("ÉCONOME_3", RAND);
        players[3] = new Thrifty("ÉCONOME_4", RAND);

        statisticboard.initialize(players);

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players, RAND);
            game.play();
            statisticboard.update(game.getScoreboard());
        }
        logger.info(statisticboard);


        players = new Player[Game.NB_PLAYERS];


        logger.info("\n- Partie entre le meilleur bot et le second meilleur bot\n\n");

        players[0] = new Spendthrift("DÉPENSIER_1", RAND);
        players[1] = new Spendthrift("DÉPENSIER_2", RAND);
        players[2] = new Thrifty("ÉCONOME_1", RAND);
        players[3] = new Thrifty("ÉCONOME_2", RAND);

        statisticboard.initialize(players);

        for (int i = 0; i < 1000; i++) {
            Game game = new Game(players, RAND);
            game.play();
            statisticboard.update(game.getScoreboard());
        }
        logger.info(statisticboard);
    }


    /**
     * Run several simulations while reading “stats/gamestats.csv” if it exists and adding new statistics.
     */
    public void runCsv() {
        System.out.println("csv");
    }

}