package fr.citadels.engine;

import fr.citadels.engine.score.Scoreboard;
import fr.citadels.gameelements.cards.Card;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.characters.MerchantCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.Hand;
import fr.citadels.players.Player;

import java.util.List;

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


    public void print() {
        System.out.print(this.events);
    }


    public void printAndReset() {
        this.print();
        this.reset();
    }

    public void removeLastComma() {
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


    public void addInitialGoldGiven(int gold) {
        this.events.append("Tous les joueurs obtiennent ").append(gold).append(" pièces d'or.\n");
    }


    public void addFirstDistrictsDrawn(Player player) {
        this.events.append(player.getName()).append(" pioche : ");
        for (DistrictCard districtCard : player.getHand()) {
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
        } else if (cardsUp.length == 1) {
            this.events.append("Le personnage retiré face visible est : ");
        } else {
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
        } else if (cardsDown.length == 1) {
            this.events.append("Le personnage retiré face cachée est : ");
        } else {
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


    public void addCharacterNotChosen(CharacterCard character) {
        this.events.append("Le personnage non choisis est : ").append(character.getCardName());
    }


    public void addNoPlayerTurn(Player crowned, CharacterCard character) {
        this.events.append("■ ").append(crowned.getName()).append(" appelle : ").append(character.getCardName()).append("\nAucun joueur ne se manifeste.\n");
    }


    public void addPlayerTurn(Player crowned, Player player) {
        this.events.append("■ ").append(crowned.getName()).append(" appelle : ").append(player.getCharacter().getCardName()).append("\n").append(player.getName()).append(" dévoile son rôle.\n");
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


    public void addGoldUpdate(int gold) {
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


    public void addHandUpdate(Hand hand) {
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


    public void addCityUpdate(City city) {
        this.events.append("\tSa cité comporte donc : ").append(city.toString()).append("\n");
    }


    public void addThiefPower(Player player, CharacterCard target) {
        this.events.append(player.getName()).append(" utilise le pouvoir du voleur sur le personnage ").append(target.getCardName()).append(".\n");
    }


    public void addRobbed(Player thief, Player target, int gold) {
        this.events.append(thief.getName()).append(" a volé ").append(gold).append(" pièces d'or à ").append(target.getName()).append(".\n");
    }


    public void addKilled(CharacterCard character) {
        this.events.append("\n").append(character.getCardName()).append(" a été tué par l'Assassin.\n");
    }


    public void addWasKilled(CharacterCard character) {
        this.events.append(character.getPlayer().getName()).append(" était ").append(character.getCardName()).append(" et a été tué par l'Assassin.\n");
    }


    public void addMagicianSwap(Player player, Player victim) {
        this.events.append(player.getName()).append(" échange sa main avec ").append(victim.getName()).append(".\n");
        this.addHandUpdate(player.getHand());
    }


    public void addMagicianDiscard(Player player, List<DistrictCard> districtCards){
        this.events.append(player.getName()).append(" défausse ");
        for (DistrictCard districtCard : districtCards) {
            this.events.append(districtCard.getCardName()).append(", ");
        }
        this.events.append(".\n");
    }


    public void addMerchantPower(MerchantCard merchantCard) {
        this.events.append(merchantCard.getPlayer().getName()).append(" utilise son pouvoir pour gagner une pièce d'or.\n");
        this.addGoldUpdate(merchantCard.getPlayer().getGold());
    }


    public void addArchitectPower(int number, Player player) {
        this.events.append("Le joueur utilise son pouvoir pour ");
        switch (number) {
            case 1:
                this.events.append("piocher et ajouter deux cartes à sa main.\n");
                break;
            case 2:
                this.events.append("construire un quartier supplémentaire.\n");
        }
    }


    public void addNoArchitectPower() {
        this.events.append("Le joueur n'utilise pas son pouvoir permettant de construire un quartier supplémentaire.\n");
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