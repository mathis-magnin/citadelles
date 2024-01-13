package fr.citadels.engine;

import fr.citadels.cards.Card;
import fr.citadels.cards.CardFamily;
import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.players.City;
import fr.citadels.players.Hand;
import fr.citadels.players.Player;

public class Display {

    /* Attribute */

    private final StringBuilder events;


    /* Constructor */

    public Display() {
        this.events = new StringBuilder();
    }


    /* Getter */

    public String getEvents() {
        return this.toString();
    }


    /* Methods */

    public void reset() {
        this.events.delete(0, this.events.length());
    }


    private void print() {
        System.out.print(this.events);
    }


    public void printAndReset() {
        this.print();
        this.reset();
    }

    private void removeLastComma() {
        if (this.events.charAt(this.events.length() - 2) == ',') {
            this.events.delete(this.events.length() - 2, this.events.length());
        }
    }


    public void addBlankLine() {
        this.events.append("\n");
    }

    public void addBlankLine(int number) {
        for (int i = 0; i < number; i++) {
            this.events.append("\n");
        }
    }


    public void addGameTitle() {
        this.events.append("\n┌────────────┐\n│ Citadelles │\n└────────────┘\n\n");
    }


    public void addTurnTitle(int turn) {
        this.events.append("┌─────────┐\n│ Tour ").append(String.format("%2d", turn)).append(" │\n└─────────┘\n");
    }


    public void addSelectionPhaseTitle() {
        this.events.append("\n\t┌────────────────────┐\n\t│ Phase de sélection │\n\t└────────────────────┘\n\n");
    }


    public void addTurnPhaseTitle() {
        this.events.append("\n\t┌──────────────┐\n\t│ Phase de jeu │\n\t└──────────────┘\n\n");
    }


    public void addScoreTitle() {
        this.events.append("┌────────┐\n│ Scores │\n└────────┘\n\n");
    }


    public void addPlayers(Player[] players) {
        this.events.append("Les joueurs présents dans cette partie sont : ");
        for (Player player : players) {
            this.events.append(player.getName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addFirstDistrictsDrawn(Player player) {
        this.events.append(player.getName()).append(" pioche : ");
        for (DistrictCard districtCard : player.getHand() ) {
            this.events.append(districtCard.toString()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addFirstCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" a été aléatoirement désigné comme joueur couronné.");
    }


    public void addRemovedCharacter(CharacterCard[] cardsUp, CharacterCard[] cardsDown) {
        /* Cards up */
        if (cardsUp.length >= 2) {
            this.events.append("Les personnages retirés face visible sont : ");
        }
        else if (cardsUp.length == 1) {
            this.events.append("Le personnage retiré face visible est : ");
        }
        else {
            this.events.append("Aucun personnage n'est retiré face visible.");
        }
        for (CharacterCard card : cardsUp) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();

        /* Cards down */
        if (cardsDown.length >= 2) {
            this.events.append("Les personnages retirés face cachée sont : ");
        }
        else if (cardsDown.length == 1) {
            this.events.append("Le personnage retiré face cachée est : ");
        }
        else {
            this.events.append("Aucun personnage n'est retiré face cachée.");
        }
        for (CharacterCard card : cardsDown) {
            this.events.append(card.getCardName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" est le joueur couronné.\nIl sera donc le premier à choisir son personnage.\n");
    }


    public void addCharacterChosen(Player player, CharacterCard card) {
        this.events.append(player.getName()).append(" choisit : ").append(card.getCardName()).append("\n");
    }


    public void addPlayerTurn(Player player) {
        this.events.append("■ Le joueur couronné appelle : ").append(player.getCharacter().getCardName()).append("\nC'est donc au tour de ").append(player.getName()).append(".\n");
    }


    public void addPlayer(Player player) {
        this.events.append(player.toString()).append("\n");
    }


    public void addGoldTakenFromCity(Player player, int gold) {
        this.events.append("Le joueur prend ").append(gold).append(" pièces d'or grâce à ses quartiers : ").append(player.getCharacter().getCardFamily()).append("\n");
        this.addGoldUpdate(player.getGold());
    }


    public void addGoldTaken(Player player, int gold) {
        this.events.append("Le joueur prend ").append(gold).append(" pièces d'or.\n");
        this.addGoldUpdate(player.getGold());
    }


    private void addGoldUpdate(int gold) {
        this.events.append("\tSa fortune s'élève donc à ").append(gold).append(" pièces d'or.\n");
    }


    public void addDistrictDrawn(DistrictCard[] districtCards) {
        this.events.append("Le joueur pioche : ");
        for (Card card : districtCards) {
            this.events.append(card.toString()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addDistrictChosen(Player player, DistrictCard districtCard) {
        this.events.append("Le joueur choisit : ").append(districtCard.getCardName()).append("\n");
        this.addHandUpdate(player.getHand());
    }


    private void addHandUpdate(Hand hand) {
        this.events.append("\tSa main comporte donc : ").append(hand.toString()).append("\n");
    }


    public void addNoDistrictBuilt() {
        this.events.append("Le joueur ne construit rien.\n");
    }


    public void addDistrictBuilt(Player player, DistrictCard districtCard) {
        this.events.append("Le joueur construit : ").append(districtCard.toString()).append("\n");
        this.addGoldUpdate(player.getGold());
        this.addHandUpdate(player.getHand());
        this.addCityUpdate(player.getCity());
    }


    private void addCityUpdate(City city) {
        this.events.append("\tSa cité comporte donc : ").append(city.toString()).append("\n");
    }


    public void addGameFinished(Player player) {
        this.events.append(player.getName()).append(" est le premier joueur à posséder une cité complète.\nLa partie se terminera donc à la fin de ce tour.\n");
    }


    public void addScoreboard(Scoreboard scoreboard) {
        this.events.append(scoreboard.toString());
    }


    public void addWinner(Player player) {
        this.events.append("Le gagnant est : ").append(player.getName()).append(" !\n");
    }

}