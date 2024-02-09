package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Richard extends Player {


    /* Constructor */

    public Richard(String name, List<District> cards, Game game) {
        super(name, cards, game);
    }

    public Richard(String name) {
        super(name);
    }


    /* Methods */

    @Override
    public void chooseCharacter(CharactersList characters) {
        this.setCharacter(characters.remove(0));
        this.getMemory().setPossibleCharacters(characters);
    }


    @Override
    public void chooseDraw() {
        this.memory.setDraw(3 <= this.getGold());
    }


    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        return drawnCards[0];
    }


    /**
     * Choose to take gold from city after he built another district from his family or before otherwise.
     */
    @Override
    public void chooseMomentToTakeIncome() {
        this.chooseDistrictToBuild();
        if (this.memory.getDistrictToBuild() != null) {
            this.memory.setMomentWhenUse((this.memory.getDistrictToBuild().getFamily().equals(this.getCharacter().getFamily())) ? Moment.BETWEEN_PHASES : Moment.AFTER_BUILDING);
        } else {
            this.memory.setMomentWhenUse(Moment.BETWEEN_PHASES);
        }
    }


    @Override
    public void chooseDistrictToBuild() {
        if (!this.getHand().isEmpty() && (this.getHand().get(0).getGoldCost() <= this.getGold()) && !this.hasCardInCity(this.getHand().get(0))) {
            this.memory.setDistrictToBuild(this.getHand().get(0));
        } else {
            this.memory.setDistrictToBuild(null);
        }
    }


    @Override
    public void chooseTargetToKill() {
        this.memory.setTarget(Assassin.getPossibleTargets().get(0));
    }


    @Override
    public void chooseTargetToRob() {
        this.memory.setTarget(Thief.getPossibleTargets().get(0));
    }


    @Override
    public void chooseMagicianPower() {
        this.getMemory().setPowerToUse(Power.SWAP);
        this.getMemory().setMomentWhenUse(Moment.BEFORE_RESSOURCES);
        CharactersList magicianTarget = Magician.getPossibleTargets();

        Character assassin = CharactersList.allCharacterCards[0];
        Character bishop = CharactersList.allCharacterCards[4];
        Character warlord = CharactersList.allCharacterCards[7];

        /* Richard's strategy */
        /* Assassin */
        if (magicianTarget.contains(assassin) && (5 <= assassin.getPlayer().getCity().size()) && (this.getKilledCharacter().equals(warlord))) {
            this.getMemory().setTarget(assassin);
            return;
        }

        /* Bishop and Warlord */
        List<Player> possibleBishopPlayers = this.getPossiblePlayersWhoPlay(bishop);
        List<Player> possibleWarlordPlayers = this.getPossiblePlayersWhoPlay(warlord);
        for (Character character : magicianTarget) {
            if (possibleBishopPlayers.contains(character.getPlayer()) && (5 <= character.getPlayer().getCity().size())) {
                this.getMemory().setTarget(character);
                return;
            }
            if (possibleWarlordPlayers.contains(character.getPlayer()) && (5 <= character.getPlayer().getCity().size())) {
                this.getMemory().setTarget(character);
                return;
            }
        }

        /* Player who is about to win */
        List<Player> playersAboutToWin = this.getPlayersAboutToWin(Arrays.asList(this.getMemory().getPlayers()));
        List<Player> playersWhoChoseBefore = this.getPlayersWhoChoseBefore();
        if (!(playersAboutToWin.isEmpty()) && (playersWhoChoseBefore.size() == 1) && (playersWhoChoseBefore.get(0).getCity().size() < 6) && (this.getHand().size() <= 2)) {
            for (Character character : magicianTarget) {
                if (playersAboutToWin.contains(character.getPlayer())) {
                    this.getMemory().setTarget(character);
                    return;
                }
            }
        }

        /* Previous architect */
        Player previousArchitect = this.getMemory().getPreviousArchitect();
        if ((previousArchitect != null) && (4 <= previousArchitect.getHand().size())) {
            this.getMemory().setTarget(previousArchitect.getCharacter());
        }

        /* Basic strategy : Discard redundant cards between phases */
        this.memory.setPowerToUse(Power.RECYCLE);
        this.getMemory().setCardsToDiscard(this.getActions().putRedundantCardsAtTheEnd());
        this.memory.setMomentWhenUse(Moment.BETWEEN_PHASES);
    }


    /**
     * When the player embodies the warlord, choose the character and the district in city to destroy from the list of possibles targets.
     * If the player who embodies the king has 5 or more districts in his city, Richard will destroy his cheapest district.
     */
    @Override
    public void chooseTargetToDestroy() {
        CharactersList warlordTargets = Warlord.getPossibleTargets();
        if (!warlordTargets.isEmpty()) {
            // Richard's strategy
            Character king = CharactersList.allCharacterCards[3];
            if (warlordTargets.contains(king) && (5 <= king.getPlayer().getCity().size())) {
                this.getMemory().setTarget(king);
                District cheapestDistrict = king.getPlayer().getCity().getCheapestDistrict();
                if ((cheapestDistrict != null) && (cheapestDistrict.getGoldCost() - 1 <= this.getGold())) {
                    this.getMemory().setDistrictToDestroy(cheapestDistrict);
                    return;
                }
            }

            // Basic strategy : Choose the first player on which he can destroy a district.
            for (Character character : warlordTargets) {
                District cheapestDistrict = character.getPlayer().getCity().getCheapestDistrict();
                if ((cheapestDistrict != null) && (cheapestDistrict.getGoldCost() - 1 <= this.getGold())) {
                    this.getMemory().setTarget(character);
                    this.getMemory().setDistrictToDestroy(cheapestDistrict);
                    return;
                }
            }
        }
        this.getMemory().setTarget(null);
        this.getMemory().setDistrictToDestroy(null);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return (3 <= this.getGold());
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return !this.getHand().isEmpty();
    }


    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && (!this.hasCardInCity(removedDistrict));
    }


    public List<Player> getPossiblePlayersWhoPlay(Character character) {
        if (!character.isDead() && (character.getRank() < this.getCharacter().getRank())) { // Richard already know who is playing the character
            return List.of(character.getPlayer());
        }
        if (!this.getMemory().getFaceUpCharacters().contains(character)) { // A player is maybe playing the character
            if (this.getMemory().getPossibleCharacters().contains(character)) { // A player who chose his character after richard is maybe playing the character
                return this.getPlayersWhoChoseBefore();
            }
            else {  // A player who chose his character before richard is maybe playing Warlord
                return this.getPlayersWhoChoseAfter();
            }
        }
        return new ArrayList<>();
    }


    /**
     * Find the killed player if it exists.
     *
     * @return the killed player or null.
     */
    public Character getKilledCharacter() {
        for (Character character : CharactersList.allCharacterCards) {
            if (character.isDead()) {
                return character;
            }
        }
        return null;
    }


    /**
     * Find players who are about to win, that is to say with more than 5 district in theirs city.
     *
     * @param players the list to check.
     * @return a list of players who are about to win.
     */
    public List<Player> getPlayersAboutToWin(List<Player> players) {
        List<Player> playersAboutToWin = new ArrayList<>();
        for (Player player : players) {
            if (5 <= player.getCity().size()) {
                playersAboutToWin.add(player);
            }
        }
        return playersAboutToWin;
    }

}