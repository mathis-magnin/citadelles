package fr.citadels.players.bots;


import fr.citadels.cards.characters.Power;
import fr.citadels.engine.Game;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.roles.Assassin;
import fr.citadels.cards.characters.roles.Magician;
import fr.citadels.cards.characters.roles.Thief;
import fr.citadels.cards.characters.roles.Warlord;
import fr.citadels.cards.districts.District;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

public class Uncertain extends Player {

    /* Constant */

    private final Random rand;


    /* Constructor */

    public Uncertain(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        rand = random;
    }


    public Uncertain(String name, Random random) {
        super(name);
        rand = random;
    }

    /* Methods */

    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharactersList characters) {
        int randomIndex = -1;
        while (randomIndex >= characters.size() || randomIndex < 0) {
            randomIndex = rand.nextInt(characters.size());
        }
        this.setCharacter(characters.remove(randomIndex));
    }


    /**
     * Choose to draw randomly.
     */
    @Override
    public void chooseDraw() {
        this.memory.setDraw(this.rand.nextBoolean());
    }


    /***
     * choose a card to play among the cards drawn.
     *
     * @precondition drawnCards must contain at least 1 card.
     * @param drawnCards cards drawn.
     * @return the card to play.
     */
    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        int randomIndex = rand.nextInt(drawnCards.length);
        District cardToPlay = drawnCards[randomIndex];
        getActions().putBack(drawnCards, randomIndex);
        return cardToPlay;
    }


    /**
     * Choose to take gold from city after he built another district from his family or before otherwise.
     */
    @Override
    public void chooseMomentToTakeIncome() {
        this.chooseDistrictToBuild();
        if (this.memory.getDistrictToBuild() != null) {
            this.memory.setMomentWhenUse((this.memory.getDistrictToBuild().getFamily().equals(this.getCharacter().getFamily())) ? Moment.AFTER_BUILDING : Moment.BETWEEN_PHASES);
        } else {
            this.memory.setMomentWhenUse(Moment.BETWEEN_PHASES);
        }
    }


    /***
     * Choose or not a card in hand
     * set the card chosen or null if no card can be chosen in the districtToBuild attribute.
     */
    @Override
    public void chooseDistrictToBuild() {
        if (this.rand.nextBoolean()) {
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i))) {
                    this.getMemory().setDistrictToBuild(this.getHand().get(i));
                    return;
                }
            }
        }
        this.getMemory().setDistrictToBuild(null);
    }


    /**
     * When the player embodies the assassin, choose the character to kill from the list of possibles targets.
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        int randIndex = rand.nextInt(possibleTargets.size());
        getMemory().setTarget(possibleTargets.get(randIndex));
    }


    /**
     * When the player embodies the thief, choose the character to rob from the list of possibles targets.
     */
    @Override
    public void chooseTargetToRob() {
        List<Character> potentialTargets = Thief.getPossibleTargets();
        int randIndex = rand.nextInt(potentialTargets.size());
        getMemory().setTarget(potentialTargets.get(randIndex));
    }


    /**
     * Choose the power to use as a magician.
     */
    @Override
    public void chooseMagicianPower() {
        int randPower = rand.nextInt(2); // choose which power to use

        if (randPower == 0) { // swap hands : choose a target
            this.getMemory().setPowerToUse(Power.SWAP);
            int randTarget = rand.nextInt(Magician.getPossibleTargets().size());
            this.getMemory().setTarget(Magician.getPossibleTargets().get(randTarget));
        } else { // discard cards : choose how many cards to discard
            this.getMemory().setPowerToUse(Power.RECYCLE);
            if (!this.getHand().isEmpty()) {
                this.getMemory().setNumberCardsToDiscard(rand.nextInt(this.getHand().size()));
            }
            else {
                this.getMemory().setNumberCardsToDiscard(0);
            }
        }
        this.memory.setMomentWhenUse(Moment.values()[this.rand.nextInt(3)]);
    }


    /**
     * When the player embodies the warlord, choose the character and the district in city to destroy from the list of possibles targets.
     */
    @Override
    public void chooseTargetToDestroy() {
        Character target = null;
        District districtToDestroy = null;
        if (!Warlord.getPossibleTargets().isEmpty()) {
            target = Warlord.getPossibleTargets().get(rand.nextInt(Warlord.getPossibleTargets().size()));
            if (!target.getPlayer().getCity().isEmpty()) {
                districtToDestroy = target.getPlayer().getCity().get(rand.nextInt(target.getPlayer().getCity().size()));
                if (districtToDestroy.getGoldCost() - 1 > this.getGold()) {
                    districtToDestroy = null;
                }
            }
        }
        this.getMemory().setTarget(target);
        this.getMemory().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return rand.nextBoolean() && (3 <= getGold());
    }


    /**
     * This bot can randomly activate the graveyard's effect if he can afford it.
     *
     * @param removedDistrict the district removed by the Warlord.
     * @return a boolean value.
     */
    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return rand.nextBoolean() && (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict);
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return rand.nextBoolean() && !this.getHand().isEmpty();
    }

}