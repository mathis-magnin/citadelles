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
        }
        else {
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
        this.memory.setPowerToUse(Power.SWAP);
        this.memory.setTarget(Magician.getPossibleTargets().get(0));
        this.memory.setMomentWhenUse(Moment.BEFORE_RESSOURCES);
    }


    /**
     * When the player embodies the warlord, choose the character and the district in city to destroy from the list of possibles targets.
     * If Richard, who embodies the warlord, thinks the player who embodies the king has 5 or more districts in his city, he will destroy his cheapest district.
     */
    @Override
    public void chooseTargetToDestroy() {
        CharactersList warlordTargets = Warlord.getPossibleTargets();
        if (!warlordTargets.isEmpty()) {
            // Richard's strategy
            if (!((this.getMemory().getPossibleCharacters().containsAll(List.of(new Assassin(), new King()))) || (this.getMemory().getFaceUpcharacters().contains(new Assassin())))) {
                List<Player> richardTargets = getPlayersAboutToWin(this.getMemory().getPlayersWhoChose());
                for (Character character : warlordTargets) {
                    if (richardTargets.contains(character.getPlayer())) {
                        this.getMemory().setTarget(character);
                        District cheapestDistrict = character.getPlayer().getCity().getCheapestDistrict();
                        if (cheapestDistrict.getGoldCost() - 1 <= this.getGold()) {
                            this.getMemory().setDistrictToDestroy(cheapestDistrict);
                            return;
                        }
                    }
                }
            }

            // Basic strategy : Choose the first player on which he can destroy a district.
            for(Character character : warlordTargets) {
                for (District district : character.getPlayer().getCity()) {
                    if (district.getGoldCost() - 1 <= this.getGold()) {
                        this.getMemory().setTarget(character);
                        this.getMemory().setDistrictToDestroy(district);
                        return;
                    }
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
     * Find players who are about to win, that is to say with more than 5 district in theirs city.
     *
     * @param players the list to check.
     * @return a list of players who are about to win.
     */
    private List<Player> getPlayersAboutToWin(List<Player> players) {
        List<Player> playersAboutToWin = new ArrayList<>();
        for (Player player : players) {
            if (5 <= player.getCity().size()) {
                playersAboutToWin.add(player);
            }
        }
        return playersAboutToWin;
    }

}