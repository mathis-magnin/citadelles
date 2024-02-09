package fr.citadels.players.bots;

import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.ArrayList;
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
        List<Player> playersAboutToWin = this.getPlayersWithMoreDistrictsThan(List.of(getMemory().getPlayers()), 5);

        for (Player player : playersAboutToWin) {
            if (getMemory().getPlayersWhoChose().contains(player)) {
                if (!getMemory().getPossibleCharacters().contains(CharactersList.allCharacterCards[4]) && !getMemory().getFaceUpcharacters().contains(CharactersList.allCharacterCards[4])) {
                    this.memory.setTarget(CharactersList.allCharacterCards[4]);
                    return;
                } else if (!getMemory().getPossibleCharacters().contains(CharactersList.allCharacterCards[7]) && !getMemory().getFaceUpcharacters().contains(CharactersList.allCharacterCards[7])) {
                    this.memory.setTarget(CharactersList.allCharacterCards[7]);
                    return;
                }
            } else {
                if (getMemory().getPossibleCharacters().contains(CharactersList.allCharacterCards[4])) {
                    this.memory.setTarget(CharactersList.allCharacterCards[4]);
                    return;
                } else if (getMemory().getPossibleCharacters().contains(CharactersList.allCharacterCards[7])) {
                    this.memory.setTarget(CharactersList.allCharacterCards[7]);
                    return;
                }
            }
        }

        CharactersList targets = Thief.getPossibleTargets();
        for (int i : List.of(6, 7, 2, 3, 4)) { // Architect, Warlord, Magician, King, Bishop
            if (!quiPeutAvoir(CharactersList.allCharacterCards[i]).isEmpty() && targets.contains(CharactersList.allCharacterCards[i])) {
                this.memory.setTarget(CharactersList.allCharacterCards[i]);
                return;
            }
        }

        this.memory.setTarget(null);
    }


    @Override
    public void chooseMagicianPower() {
        this.memory.setPowerToUse(Power.SWAP);
        this.memory.setTarget(Magician.getPossibleTargets().get(0));
        this.memory.setMomentWhenUse(Moment.BEFORE_RESSOURCES);
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
            if (warlordTargets.contains(king) && this.getCharactersWhoPlayed().contains(king) && (5 <= king.getPlayer().getCity().size())) {
                this.getMemory().setTarget(CharactersList.allCharacterCards[3]);
                District cheapestDistrict = CharactersList.allCharacterCards[3].getPlayer().getCity().getCheapestDistrict();
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


    /**
     * Find players who have a minimum amount of districts in their city.
     *
     * @param players the list to check.
     * @param minDistricts the minimum amount of districts the player should have
     * @return a list of players who are about to win.
     */
    public List<Player> getPlayersWithMoreDistrictsThan(List<Player> players, int minDistricts) {
        List<Player> playersWithMinDistricts = new ArrayList<>();
        for (Player player : players) {
            if (minDistricts <= player.getCity().size()) {
                playersWithMinDistricts.add(player);
            }
        }
        return playersWithMinDistricts;
    }


    /**
     * Get characters who played before Richard.
     *
     * @return the list of characters who played before.
     */
    public CharactersList getCharactersWhoPlayed() {
        CharactersList charactersWhoPlayed = new CharactersList();
        for (int i = 0; i < this.getCharacter().getRank() - 1; i++) {
            if (CharactersList.allCharacterCards[i].isPlayed()) {
                charactersWhoPlayed.add(CharactersList.allCharacterCards[i]);
            }
        }
        return charactersWhoPlayed;
    }

}