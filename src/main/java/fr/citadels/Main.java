package fr.citadels;

import com.beust.jcommander.JCommander;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import fr.citadels.cards.charactercards.characters.King;
import fr.citadels.engine.Game;
import com.beust.jcommander.Parameter;
import fr.citadels.engine.score.Statisticboard;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /* Command line parameters */

    @Parameter(names = "--demo", description = "Demonstration of a single game")
    private boolean demo = false;

    @Parameter(names = "--2thousands", description = "Simulation of two thousands games")
    private boolean twoThousands = false;

    @Parameter(names = "--csv", description = "Run several games and add the statistics to the csv file")
    private boolean csv = false;


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
            try {
                main.runCsv();
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }
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
        System.out.println("2 thousands");
    }


    /**
     * Run several simulations while reading “stats/gamestats.csv” if it exists and adding new statistics.
     */
    public void runCsv() throws IOException, CsvValidationException {
        System.out.println("csv");

        Path path = Paths.get("stats", "gamestats.csv");
        File file = new File(path.toString());
        if (file.exists()) {
            Statisticboard stats = Statisticboard.readCsv(file);
            System.out.println(stats);
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

        Statisticboard stats = Statisticboard.readCsv(file);
        System.out.println(stats);

    }

}