package fr.citadels;

import com.beust.jcommander.JCommander;
import fr.citadels.engine.Game;
import com.beust.jcommander.Parameter;

public class Main {

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
        if (main.demo || !(main.twoThousands || main.csv)) {
            main.runDemonstration();
        }
        if (main.twoThousands) {
            main.runTwoThousands();
        }
        if (main.csv) {
            main.runCsv();
        }
    }


    /**
     * Run a single game with a full log printed.
     */
    public void runDemonstration() {
        Game game = new Game();
        game.play();
    }


    /**
     * This program launch 2000 games as follows :
     * 1. Simulation of 1000 games of the best bot against the second best (with others bots to complete).
     * 2. Simulation of 1000 games of the best bot against itself (or as many clone of itself than players).
     */
    public void runTwoThousands() {
        Game game = new Game();
        game.play();
    }


    /**
     * Run several simulations while reading “stats/gamestats.csv” if it exists and adding new statistics.
     */
    public void runCsv() {
        System.out.println("csv");
    }

}