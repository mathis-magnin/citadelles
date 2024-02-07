package fr.citadels.players.bots;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

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


    @Override
    public void chooseTargetToDestroy() {
        if (!Warlord.getPossibleTargets().isEmpty()) {
            this.memory.setTarget(Warlord.getPossibleTargets().get(0));
            if (!this.memory.getTarget().getPlayer().getCity().isEmpty() && (this.memory.getTarget().getPlayer().getCity().get(0).getGoldCost() - 1 <= this.getGold())) {
                this.memory.setDistrictToDestroy(this.memory.getTarget().getPlayer().getCity().get(0));
            }
        }
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

}