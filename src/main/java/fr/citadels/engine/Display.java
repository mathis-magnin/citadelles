package fr.citadels.engine;

import fr.citadels.Main;
import fr.citadels.cards.Card;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.Role;
import fr.citadels.cards.characters.roles.Merchant;
import fr.citadels.cards.districts.*;
import fr.citadels.engine.score.Scoreboard;
import fr.citadels.players.Player;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Display {

    /* Attribute */

    private final StringBuilder events;


    /* Constructor */

    public Display() {
        this.events = new StringBuilder();
    }


    /* Methods */

    public void reset() {
        this.events.delete(0, this.events.length());
    }


    public void print() {
        Logger logger = LogManager.getLogger("Citadels");
        if (!Main.twoThousands && !Main.csv)
            logger.log(Level.INFO, this.events.toString());

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
        for (District districtCard : player.getHand()) {
            this.events.append(districtCard.toString()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addFirstCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" a été aléatoirement désigné comme joueur couronné.");
    }


    public void addRemovedCharacter(Character[] cardsUp, Character[] cardsDown) {
        /* Cards up */
        if (cardsUp.length >= 2) {
            this.events.append("Les personnages retirés face visible sont : ");
        } else if (cardsUp.length == 1) {
            this.events.append("Le personnage retiré face visible est : ");
        } else {
            this.events.append("Aucun personnage n'est retiré face visible.");
        }
        for (Character card : cardsUp) {
            this.events.append(card.getName()).append(", ");
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
        for (Character card : cardsDown) {
            this.events.append(card.getName()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addCrownedPlayer(Player player) {
        this.events.append(player.getName()).append(" est le joueur couronné.\nIl sera donc le premier à choisir son personnage.\n");
    }


    public void addCharacterChosen(Player player, Character card) {
        this.events.append(player.getName()).append(" choisit : ").append(card.getName()).append("\n");
    }


    public void addCharacterNotChosen(Character character) {
        this.events.append("Le personnage non choisis est : ").append(character.getName());
    }


    public void addNoPlayerTurn(Player crowned, Character character) {
        this.events.append("■ ").append(crowned.getName()).append(" appelle : ").append(character.getName()).append("\nAucun joueur ne se manifeste.\n");
    }


    public void addPlayerTurn(Player crowned, Player player) {
        this.events.append("■ ").append(crowned.getName()).append(" appelle : ").append(player.getCharacter().getName()).append("\n").append(player.getName()).append(" dévoile son rôle.\n");
    }


    public void addPlayer(Player player) {
        this.events.append(player.toString()).append("\n");
    }


    public void addGoldTakenFromCity(Player player, int gold, boolean activateSchoolOfMagicEffect) {
        this.events.append("Le joueur utilise son pouvoir pour prendre ").append(gold).append(" pièces d'or grâce à ses quartiers ").append(player.getCharacter().getFamily());
        if (activateSchoolOfMagicEffect) {
            this.events.append(" et à l'effet de l'École de magie");
        }
        this.events.append(".\n");
        this.addGoldUpdate(player.getGold());
    }


    public void addGoldTaken(Player player, int gold) {
        this.events.append("Le joueur prend ").append(gold).append(" pièces d'or.\n");
        this.addGoldUpdate(player.getGold());
    }


    public void addGoldUpdate(int gold) {
        this.events.append("\tSa fortune s'élève donc à ").append(gold).append(" pièces d'or.\n");
    }


    public void addDistrictDrawn(District[] districtCards) {
        this.events.append("Le joueur pioche : ");
        for (Card card : districtCards) {
            this.events.append(card.toString()).append(", ");
        }
        this.removeLastComma();
        this.addBlankLine();
    }


    public void addDistrictChosen(Player player, District districtCard) {
        this.events.append("Le joueur choisit : ").append(districtCard.getName()).append("\n");
        this.addHandUpdate(player.getHand());
    }


    public void addHandUpdate(Hand hand) {
        this.events.append("\tSa main comporte donc : ").append(hand.toString()).append("\n");
    }


    public void addNoDistrictBuilt() {
        this.events.append("Le joueur ne construit rien.\n");
    }


    public void addDistrictBuilt(Player player, District districtCard) {
        this.events.append("Le joueur construit : ").append(districtCard.toString()).append("\n");
        this.addGoldUpdate(player.getGold());
        this.addHandUpdate(player.getHand());
        this.addCityUpdate(player.getCity());
    }


    public void addCityUpdate(City city) {
        this.events.append("\tSa cité comporte donc : ").append(city.toString()).append("\n");
    }


    public void addAssassinPower(Character target) {
        this.events.append("Le joueur utilise son pouvoir pour tuer : ").append(target.getName()).append(".\n");
    }


    public void addWasKilled(Character character) {
        this.events.append(character.getPlayer().getName()).append(" était ").append(character.getName()).append(" et avait été tué par l'Assassin.\n");
    }


    public void addThiefPower(Character target) {
        this.events.append("Le joueur utilise son pouvoir pour voler : ").append(target.getName()).append(".\n");
    }


    public void addRobbed(Player thief, int gold) {
        this.events.append("Le joueur s'est fait volé sa fortune de ").append(gold).append(" pièces d'or par ").append(thief.getName()).append(".\n");
        this.events.append("\tLa fortune de ").append(thief.getName()).append(" s'élève donc à ").append(thief.getGold()).append(" pièces d'or.\n");
        this.addBlankLine();
    }


    public void addMagicianSwap(Player player, Player target) {
        this.events.append("Le joueur utilise son pouvoir pour échanger sa main avec celle de ").append(target.getName()).append(".\n");
        this.addHandUpdate(player.getHand());
    }


    public void addMagicianDiscard(Player player, List<District> discarded) {
        this.events.append("Le joueur utilise son pouvoir pour défausser de sa main ");
        for (District districtCard : discarded) {
            this.events.append(districtCard.getName()).append(", ");
        }
        this.removeLastComma();
        this.events.append(" et piocher autant de nouvelles cartes.\n");
        this.addHandUpdate(player.getHand());
        this.addBlankLine();
    }


    public void addKingPower() {
        this.events.append("Le joueur utilise son pouvoir pour prendre la couronne.\n");
    }


    public void addKingHeir() {
        this.events.append("Le joueur prend la couronne en tant qu'héritier du roi.\n");
    }


    public void addBishopPower() {
        this.events.append(CharactersList.allCharacterCards[Role.BISHOP.ordinal()].getPlayer().getName()).append(" utilise son pouvoir pour ne pas être attaqué par le condottière.\n");
    }


    public void addMerchantPower(Merchant merchantCard) {
        this.events.append("Le joueur utilise son pouvoir pour gagner une pièce d'or.\n");
        this.addGoldUpdate(merchantCard.getPlayer().getGold());
    }


    public void addArchitectPower(Power power) {
        this.events.append("Le joueur utilise son pouvoir pour ");
        switch (power) {
            case DRAW:
                this.events.append("piocher et ajouter deux cartes à sa main.\n");
                break;
            case BUILD:
                this.events.append("construire un quartier supplémentaire.\n");
        }
    }


    public void addNoArchitectPower() {
        this.events.append("Le joueur n'utilise pas son pouvoir permettant de construire un quartier supplémentaire.\n");
    }


    public void addWarlordPower(Player player, Character target, District districtToDestroy) {
        this.events.append("Le joueur utilise son pouvoir pour détruire le quartier ").append(districtToDestroy).append(" dans la cité de ").append(target.getPlayer().getName()).append(".\n");
        this.events.append("\tLa fortune de ").append(player.getName()).append(" s'élève donc à ").append(player.getGold()).append(" pièces d'or.\n");
        this.events.append("\tLa cité de ").append(target.getPlayer().getName()).append((" comporte donc : ")).append((target.getPlayer().getCity().toString())).append("\n");
    }


    public void addNoWarlordPower() {
        this.events.append("Le joueur n'utilise pas son pouvoir permettant de détruire le quartier d'un autre joueur.\n");
    }


    public void addGraveyardEffect(Player player) {
        this.events.append(player.getName()).append(" utilise l'effet du Cimetière pour prendre en main le quartier détruit en payant 1 pièce d'or.\n");
        this.events.append("\tLa fortune de ").append(player.getName()).append(" s'élève donc à ").append(player.getGold()).append(" pièces d'or.\n");
        this.events.append("\tLa main de ").append(player.getName()).append((" comporte donc : ")).append((player.getHand().toString())).append("\n");
    }


    public void addNoGraveyardEffect(Player player) {
        this.events.append(player.getName()).append(" n'utilise pas l'effet du Cimetière.\n");
    }


    public void addDistrictPlacedBelow() {
        this.events.append("Le quartier détruit est donc placé sous la pile.\n");
    }


    public void addFactoryEffect() {
        this.events.append("Le joueur utilise l'effet de la Manufacture pour piocher 3 cartes en payant 3 pièces d'or.\n");
    }


    public void addLibraryEffect() {
        this.events.append("Le joueur utilise l'effet de la Bibliothèque pour prendre en main toutes les cartes piochées.\n");
    }


    public void addObservatoryEffect() {
        this.events.append("Le joueur utilise l'effet de l'Observatoire pour piocher 3 cartes.\n");
    }


    public void addLaboratoryEffect(District card, Player player) {
        this.events.append("Le joueur utilise l'effet du Laboratoire pour défausser ").append(card).append(" et gagner une pièce d'or.\n");
        this.addGoldUpdate(player.getGold());
        this.addHandUpdate(player.getHand());
        this.addBlankLine();
    }


    public void addKeepEffect() {
        this.events.append("Grâce à son effet, le Donjon de ").append(DistrictsPile.allDistrictCards[Unique.KEEP.toIndex()].getOwner().getName()).append(" ne peut pas être détruit par le condottière.\n");
    }


    public void addGameFinished(Player player) {
        this.events.append(player.getName()).append(" est le premier joueur à posséder une cité complète.\nLa partie se terminera donc à la fin de ce tour.\n");
    }


    public void addPlayersCity(Player[] players) {
        for (Player player : players) {
            this.events.append("La cité de ").append(player.getName()).append((" comporte : ")).append((player.getCity().toString())).append("\n");
        }
        this.addBlankLine();
    }


    public void addScoreboard(Scoreboard scoreboard) {
        this.events.append(scoreboard.toString());
    }


    public void addWinner(Player player) {
        this.events.append("Le gagnant est : ").append(player.getName()).append(" !\n");
    }

}