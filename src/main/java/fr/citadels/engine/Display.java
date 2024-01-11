package fr.citadels.engine;

import fr.citadels.cards.Card;
import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.players.Player;

public class Display {

    /* Attributes */

    private final StringBuilder events;


    /* Constructor */

    public Display() {
        this.events = new StringBuilder();
    }


    /* Getters */

    public String getEvents() {
        return this.toString();
    }


    /* Methods */

    private void removeLastComma() {
        if (this.events.charAt(this.events.length() - 2) == ',') {
            this.events.delete(this.events.length() - 2, this.events.length());
        }
    }


    public void addDistrictBuilt(Player player, Card card) {
        this.events.append("Il construit : ").append(card.getCardName()).append("\n");
        this.addCity(player);
    }


    public void addNoDistrictBuilt(Player player) {
        this.events.append("Il ne construit rien.\n");
        this.addCity(player);
    }


    public void addCardDrawn(Player player, Card[] cards) {
        this.events.append("Il pioche : ");
        for (Card card : cards) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addCardChosen(Player player, Card card) {
        this.events.append("Il choisit : ").append(card.getCardName()).append("\n");
        this.addHand(player);
    }


    public void addGoldTaken(Player player, int gold) {
        this.events.append("Il prend ").append(gold).append(" pièces d'or.\n");
        this.addGold(player);
    }


    public void addCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" est le joueur couronné.\nIl sera donc le premier à choisir son personnage.\n");
    }


    public void addCharacterChosen(Player player, CharacterCard card) {
        this.events.append(player.getName()).append(" choisit : ").append(card.getCardName()).append("\n");
    }


    public void addCity(Player player) {
        this.events.append("Il a dans sa ville : ");
        for (Card card : player.getCityCards()) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addHand(Player player) {
        this.events.append("Il a en main : ");
        for (Card card : player.getCardsInHand()) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addGold(Player player) {
        this.events.append("Il possède ").append(player.getGold()).append(" pièces d'or.\n");
    }


    public void addScoreboard(Scoreboard scoreboard) {
        this.events.append(scoreboard.toString());
    }


    public void addGameFinished(Player player) {
        this.events.append(player.getName()).append(" est le premier joueur à posséder une cité complète.\nLa partie se terminera donc à la fin de ce tour.\n");
    }


    public void addWinner(Player player) {
        this.events.append("Le gagnant est : ").append(player.getName()).append(" !\n");
    }


    public void addTurnTitle(int turn) {
        this.events.append("\n╭─────────╮\n│ Tour ").append(String.format("%2d", turn)).append(" │\n╰─────────╯\n");
    }


    public void addPlayerTurn(Player player) {
        this.events.append("C'est au tour de ").append(player.getName()).append("\n");
    }


    public void addRemovedCharacter(CharacterCard[] cardsUp, CharacterCard[] cardsDown) {
        /* Cards up */
        if (cardsUp.length >= 2) {
            this.events.append("Les personnages retirés en face visible sont : ");
        }
        else if (cardsUp.length == 1) {
            this.events.append("Le personnage retiré en face visible est : ");
        }
        else {
            this.events.append("Aucun personnage n'est retiré en face visible.");
        }
        for (CharacterCard card : cardsUp) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();

        /* Cards down */
        if (cardsDown.length >= 2) {
            this.events.append("Les personnages retirés en face cachée sont : ");
        }
        else if (cardsDown.length == 1) {
            this.events.append("Le personnage retiré en face cachée est : ");
        }
        else {
            this.events.append("Aucun personnage n'est retiré en face cachée.");
        }
        for (CharacterCard card : cardsDown) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    private void reset() {
        this.events.delete(0, this.events.length());
    }


    private void print() {
        System.out.print(this.events);
    }


    public void printAndReset() {
        this.print();
        this.reset();
    }


    public void addSelectionPhaseTitle() {
        this.events.append("\n\t╭────────────────────────────────────╮\n\t│ Phase de sélection des personnages │\n\t╰────────────────────────────────────╯\n\n");
    }


    public void addTurnPhaseTitle() {
        this.events.append("\n\t╭──────────────╮\n\t│ Phase de jeu │\n\t╰──────────────╯\n\n");
    }


    public void addBlankLine() {
        this.events.append("\n");
    }


    public void addScoreTitle() {
        this.events.append("\n╭────────╮\n│ Scores │\n╰────────╯\n\n");
    }

    public void addGameTitle() {
        this.events.append("\n╭────────────╮\n│ Citadelles │\n╰────────────╯\n\n");
    }

    public void addPlayers(Player[] players) {
        this.events.append("Les joueurs présents dans cette partie sont : ");
        for (Player player : players) {
            this.events.append(player.getName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }
}