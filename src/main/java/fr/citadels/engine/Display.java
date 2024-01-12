package fr.citadels.engine;

import fr.citadels.cards.Card;
import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.players.Player;

public class Display {

    /* Attributes */

    private String events;

    /* Constructor */

    public Display() {
        this.events = "";
    }

    /* Getters */

    public String getEvents() {
        return this.events;
    }

    /* Methods */
    public void displayDistrictBuilt(Player player, Card card) {
        this.events += player.getName() + " a construit dans sa ville : " + card.getCardName() + "\n";
        this.displayCity(player);
    }

    public void displayNoDistrictBuilt(Player player) {
        this.events += player.getName() + " n'a rien construit.\n";
        this.displayCity(player);
    }

    public void displayCardDrawn(Player player, Card[] cards) {
        this.events += player.getName() + " a pioché : ";
        for (Card card : cards) {
            this.events += card.getCardName() + ", ";
        }
        this.events += "\n";
    }

    public void displayCardChosen(Player player, Card card) {
        this.events += player.getName() + " a choisi : " + card.getCardName() + "\n";
        this.displayHand(player);
    }

    public void displayGoldTaken(Player player, int gold) {
        this.events += player.getName() + " a pris " + gold + " pièces d'or.\n";
        this.displayGold(player);
    }

    public void displayCrownedPlayer(Player player) {
        this.events += player.getName() + " obtient la couronne.\n";
    }

    public void displayCharacterChosen(Player player, CharacterCard card) {
        this.events += player.getName() + " a choisi le personnage : " + card.getCardName() + "\n";
    }

    public void displayCity(Player player) {
        this.events += player.getName() + " a dans sa ville : ";
        for (Card card : player.getCity()) {
            this.events += card.getCardName() + ", ";
        }
        this.events += "\n";
    }

    public void displayHand(Player player) {
        this.events += player.getName() + " a en main : ";
        for (Card card : player.getHand()) {
            this.events += card.getCardName() + ", ";
        }
        this.events += "\n";
    }

    public void displayGold(Player player) {
        this.events += player.getName() + " a " + player.getGold() + " pièces d'or.\n";
    }

    public void displayScoreboard(Scoreboard scoreboard) {
        this.events += "\nScores : \n";
        this.events += scoreboard.toString();
    }

    public void displayWinner(Player player) {
        this.events += "\nLe gagnant est : " + player.getName() + " !\n";
    }

    public void displayTurn(int turn) {
        this.events += "\n--------\n";
        this.events += "Tour " + turn + "\n";
        this.events += "--------\n";
    }

    public void displayPlayerTurn(Player player) {
        this.events += "C'est au tour de " + player.getName() + "\n";
    }

    public void displayRemovedCharacter(CharacterCard[] cardsUp, CharacterCard[] cardsDown) {
        this.events += "les personnages retirés face visible sont : ";
        for (CharacterCard card : cardsUp) {
            this.events += card.getCardName() + ", ";
        }
        this.events += "\n";
        this.events += "les personnages retirés face cachée sont : ";
        for (CharacterCard card : cardsDown) {
            this.events += card.getCardName() + ", ";
        }
        this.events += "\n";
    }

    public void resetDisplay() {
        this.events = "";
    }

    public void printDisplay() {
        System.out.println(this.events);
    }

    public void printAndReset() {
        this.printDisplay();
        this.resetDisplay();
    }
}
