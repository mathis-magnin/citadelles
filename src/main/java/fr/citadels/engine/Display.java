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

    public void addDistrictBuilt(Player player, Card card) {
        this.events.append(player.getName()).append(" a construit dans sa ville : ").append(card.getCardName()).append("\n");
        this.addCity(player);
    }


    public void addNoDistrictBuilt(Player player) {
        this.events.append(player.getName()).append(" n'a rien construit.\n");
        this.addCity(player);
    }


    public void addCardDrawn(Player player, Card[] cards) {
        this.events.append(player.getName()).append(" a pioché : ");
        for (Card card : cards) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.events.append("\n");
    }


    public void addCardChosen(Player player, Card card) {
        this.events.append(player.getName()).append(" a choisi : ").append(card.getCardName()).append("\n");
        this.addHand(player);
    }


    public void addGoldTaken(Player player, int gold) {
        this.events.append(player.getName()).append(" a pris ").append(gold).append(" pièces d'or.\n");
        this.addGold(player);
    }


    public void addCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" obtient la couronne.\n");
    }


    public void addCharacterChosen(Player player, CharacterCard card) {
        this.events.append(player.getName()).append(" a choisi le personnage : ").append(card.getCardName()).append("\n");
    }


    public void addCity(Player player) {
        this.events.append(player.getName()).append(" a dans sa ville : ");
        for (Card card : player.getCityCards()) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.events.append("\n");
    }


    public void addHand(Player player) {
        this.events.append(player.getName()).append(" a en main : ");
        for (Card card : player.getCardsInHand()) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.events.append("\n");
    }


    public void addGold(Player player) {
        this.events.append(player.getName()).append(" a ").append(player.getGold()).append(" pièces d'or.\n");
    }


    public void addScoreboard(Scoreboard scoreboard) {
        this.events.append("\nScores : \n");
        this.events.append(scoreboard.toString());
    }


    public void addWinner(Player player) {
        this.events.append("\nLe gagnant est : ").append(player.getName()).append(" !\n");
    }


    public void addTurn(int turn) {
        this.events.append("\n--------\nTour ").append(turn).append("\n--------\n");
    }


    public void addPlayerTurn(Player player) {
        this.events.append("C'est au tour de ").append(player.getName()).append("\n");
    }


    public void addRemovedCharacter(CharacterCard[] cardsUp, CharacterCard[] cardsDown) {
        this.events.append("les personnages retirés face visible sont : ");
        for (CharacterCard card : cardsUp) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.events.append("\nles personnages retirés face cachée sont : ");
        for (CharacterCard card : cardsDown) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.events.append("\n");
    }


    private void reset() {
        this.events.delete(0, this.events.length());
    }


    private void print() {
        System.out.println(this.events);
    }


    public void printAndReset() {
        this.print();
        this.reset();
    }

}