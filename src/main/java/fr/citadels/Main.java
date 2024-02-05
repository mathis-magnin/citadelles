package fr.citadels;

import com.beust.jcommander.JCommander;
import fr.citadels.engine.Game;
import com.beust.jcommander.Parameter;

public class Main {

    /* Command line parameters */

    @Parameter(names = "--demo", description = "Demonstration of one game")
    private boolean demo = false;

    @Parameter(names = "--2thousands", description = "Simulation of two thousands games")
    private boolean twoThousands = false;

    @Parameter(names = "--csv", description = "Run one game and add the statistics to the csv file")
    private boolean csv = false;


    /* programs */

    public static void main(String... argv) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(argv);
        if (main.demo) {
            main.runDemonstration();
        }
        if (main.twoThousands) {
            main.runTwoThousands();
        }
        if (main.csv) {
            main.runCsv();
        }
        if (!(main.demo || main.twoThousands || main.csv)) {
            System.out.println("Veuillez précisez au moins un paramètre parmis :\n\t--demo\n\t--2thousands\n\t--csv");
        }
    }


    public void runDemonstration() {
        Game game = new Game();
        game.play();
    }


    public void runTwoThousands() {
        System.out.println("2 thousands");
    }


    public void runCsv() {
        System.out.println("csv");
    }

}