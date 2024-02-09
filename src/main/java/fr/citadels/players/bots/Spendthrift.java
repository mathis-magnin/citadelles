package fr.citadels.players.bots;

import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.Role;
import fr.citadels.engine.Game;
import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.roles.*;
import fr.citadels.cards.districts.District;
import fr.citadels.players.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * This bot has a more spending strategy
 * It will choose the cheapest cards and build districts as soon as it can to be the first to fill its city
 */
public class Spendthrift extends Player {

    private final Random rand;
    /* Constructor */

    public Spendthrift(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        this.rand = random;
    }

    public Spendthrift(String name, Random random) {
        super(name);
        this.rand = random;
    }
    /* Methods */

    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(List<Character> characters) {
        Family mostRepresentedFamily = getCity().getMostRepresentedFamily();
        if ((getGold() > 4) && (getHand().size() > 1) && (characters.contains(this.getMemory().getCharactersDeck().get(Role.ARCHITECT)))) {
            this.setCharacter(this.getMemory().getCharactersDeck().get(Role.ARCHITECT));
            characters.remove(this.getMemory().getCharactersDeck().get(Role.ARCHITECT));
        } else if ((!getCity().isEmpty() && (mostRepresentedFamily != Family.UNIQUE))) {
            for (Character character : characters) {
                if (character.getFamily() == mostRepresentedFamily) {
                    this.setCharacter(characters.remove(characters.indexOf(character)));
                    return;
                }
            }
        } else {
            int randomIndex = rand.nextInt(characters.size());
            this.setCharacter(characters.remove(randomIndex));
        }
    }


    /**
     * Draw if the player has less than 5 golds, if he has no cards in hand or if the cheapest card in hand costs more than 3.
     */
    @Override
    public void chooseDraw() {
        this.memory.setDraw(((5 < getGold()) || this.getHand().isEmpty() || (3 < getCheapestCardInHand()[1])));
    }


    /**
     * Get the index and the cost of the cheapest card in the hand that can be built
     *
     * @precondition cardsInHand must contain at least 1 card
     */
    public int[] getCheapestCardInHand() {
        int[] result = new int[2];

        int minIndex = -1;
        for (int i = 0; i < getHand().size(); i++) {
            if (!hasCardInCity(getHand().get(i)) && ((minIndex == -1) || (getHand().get(i).getGoldCost() < getHand().get(minIndex).getGoldCost()))) {
                minIndex = i;
            }
        }
        result[0] = minIndex;
        if (minIndex == -1) {
            result[1] = -1;
        } else {
            result[1] = getHand().get(minIndex).getGoldCost();
        }
        return result;
    }


    /**
     * Choose the cheapest card among the cards drawn
     *
     * @param drawnCards cards drawn
     * @return the card to play
     * @precondition drawnCards must contain at least 1 card
     */
    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        int minIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getFamily() == Family.UNIQUE) {
                return drawnCards[i];
            }
            if (drawnCards[i].getGoldCost() < drawnCards[minIndex].getGoldCost())
                minIndex = i;
        }
        District cardToPlay = drawnCards[minIndex];
        getActions().putBack(drawnCards, minIndex);
        return cardToPlay;
    }


    /**
     * Choose the cheapest card in hand that can be bought
     * set its districtToBuild attribute with the card chosen or null if no card can be chosen
     *
     * @precondition cardsInHand must contain at least 1 card
     */
    @Override
    public void chooseDistrictToBuild() {
        this.getActions().sortHand(this.getCharacter().getFamily());
        int minIndex = getCheapestCardInHand()[0];
        if ((minIndex >= 0) && (getHand().get(minIndex).getGoldCost() <= getGold()))
            this.getMemory().setDistrictToBuild(this.getHand().get(minIndex));
        else
            this.getMemory().setDistrictToBuild(null);
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        List<Character> possibleTargets = this.getCharacter().getPossibleTargets();
        if (rand.nextBoolean()) {
            getMemory().setTarget(possibleTargets.get(4));
        } else {
            getMemory().setTarget(possibleTargets.get(5));
        }
    }


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    @Override
    public void chooseTargetToRob() {
        List<Character> targets = this.getCharacter().getPossibleTargets();
        if (rand.nextBoolean()) {
            if (targets.contains(this.getMemory().getCharactersDeck().get(Role.WARLORD))) {
                getMemory().setTarget(this.getMemory().getCharactersDeck().get(Role.WARLORD));
            } else {
                getMemory().setTarget(this.getMemory().getCharactersDeck().get(Role.ARCHITECT));
            }
        } else {
            if (targets.contains(this.getMemory().getCharactersDeck().get(Role.ARCHITECT))) {
                getMemory().setTarget(this.getMemory().getCharactersDeck().get(Role.ARCHITECT));
            } else {
                getMemory().setTarget(this.getMemory().getCharactersDeck().get(Role.WARLORD));
            }
        }
    }


    /**
     * Choose the power to use as a magician
     */
    @Override
    public void chooseMagicianPower() {
        Character characterWithMostCards = this.getCharacterWithMostCards();

        // The magician will exchange his hand with the player with the most cards if this latter has more cards than the magician
        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getMemory().setPowerToUse(Power.SWAP);
            getMemory().setTarget(characterWithMostCards);
        } else { // else, the magician will discard cards he already has in his city
            getMemory().setPowerToUse(Power.RECYCLE);
            getHand().sortCards(Family.NEUTRAL);
            Collections.reverse(getHand());

            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getMemory().setNumberCardsToDiscard(nbCardsToDiscard + 1);
        }
        this.memory.setMomentWhenUse(Moment.BEFORE_RESOURCES);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return getGold() >= 3;
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return this.getActions().putRedundantCardsAtTheEnd() > 0 || (getGold() < 2 && getHand().size() > 2) || getHand().size() > 4;
    }


    /**
     * This bot wants to activate the graveyard's effect if the removed district's is cheaper than each district of his hand
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict) &&
                (this.getHand().isEmpty() || (!this.getHand().isEmpty() && (removedDistrict.getGoldCost() + 1 <= this.getCheapestCardInHand()[1]))); // + 1 is to take into account the effect's cost.
    }

}