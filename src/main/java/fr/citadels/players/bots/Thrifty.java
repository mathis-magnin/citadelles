package fr.citadels.players.bots;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.characters.Power;
import fr.citadels.cards.characters.Role;
import fr.citadels.cards.characters.roles.Assassin;
import fr.citadels.cards.characters.roles.Magician;
import fr.citadels.cards.characters.roles.Thief;
import fr.citadels.cards.districts.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to buy expensive cards to obtain the most points
 */
public class Thrifty extends Player {

    /* Attribute */

    private final Random rand;


    /* Constructor */

    public Thrifty(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        this.rand = random;
    }


    public Thrifty(String name, Random random) {
        super(name);
        this.rand = random;
    }

    /* Methods */

    /**
     * Choose a characterCard from the list of character :
     * - The Merchant if the player has less than 3 golds.
     * - The character of the most represented family in the city if it exists.
     * - A random character otherwise.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharactersList characters) {
        Family mostRepresentedFamily = getCity().getMostRepresentedFamily();

        if ((!getCity().isEmpty() && (mostRepresentedFamily != Family.UNIQUE))) {
            for (Character character : characters) {
                if (character.getFamily() == mostRepresentedFamily) {
                    this.setCharacter(characters.remove(characters.indexOf(character)));
                    return;
                }
            }
        } else if ((this.getGold() < 3) && (characters.contains(CharactersList.allCharacterCards[Role.MERCHANT.ordinal()]))) {
            this.setCharacter(CharactersList.allCharacterCards[Role.MERCHANT.ordinal()]);
            characters.remove(CharactersList.allCharacterCards[Role.MERCHANT.ordinal()]);
        } else {
            int randomIndex = rand.nextInt(characters.size());
            this.setCharacter(characters.remove(randomIndex));
        }
    }


    /**
     * Draw if he has more than 6 golds, or if his hand is empty, or if his most expensive card is cheaper than 4 golds.
     */
    @Override
    public void chooseDraw() {
        this.memory.setDraw(((6 < getGold()) || (getHand().isEmpty()) || ((5 < getGold()) && (getMostExpensiveCardInHand()[1] < 4))));
    }


    /**
     * Get the index and the cost of the most expensive card in the hand that can be bought.
     */
    public int[] getMostExpensiveCardInHand() {
        int[] result = new int[2];

        int maxIndex = -1;
        for (int i = 0; i < getHand().size(); i++) {
            if (!hasCardInCity(getHand().get(i)) && ((maxIndex == -1) || (getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()))) {
                maxIndex = i;
            }
        }
        result[0] = maxIndex;
        if (maxIndex == -1) {
            result[1] = -1;
        } else {
            result[1] = getHand().get(maxIndex).getGoldCost();
        }
        return result;
    }


    /**
     * Choose the most expensive card among the cards drawn.
     *
     * @param drawnCards cards drawn.
     * @return the card to play.
     * @precondition drawnCards must contain at least 1 card.
     */
    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        int maxIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getFamily() == Family.UNIQUE) {
                return drawnCards[i];
            }
            if (drawnCards[i].getGoldCost() > drawnCards[maxIndex].getGoldCost())
                maxIndex = i;
        }
        District cardToPlay = drawnCards[maxIndex];
        getActions().putBack(drawnCards, maxIndex);
        return cardToPlay;
    }


    /**
     * Choose the most expensive card in hand with a cost > 1 that can be bought.
     * set its districtToBuild attribute with the card chosen or null if no card can be chosen.
     *
     * @precondition cardsInHand must contain at least 1 card.
     */
    @Override
    public void chooseDistrictToBuild() {
        this.getActions().sortHand(this.getCharacter().getFamily());
        int maxIndex = getMostExpensiveCardInHand()[0];
        if ((maxIndex >= 0) && (getHand().get(maxIndex).getGoldCost() <= getGold()))
            this.getMemory().setDistrictToBuild(this.getHand().get(maxIndex));
        else
            this.getMemory().setDistrictToBuild(null);
    }


    /**
     * When the player embodies the assassin, choose the character to kill from the list of possibles targets.
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        if (rand.nextBoolean()) {
            getMemory().setTarget(possibleTargets.get(4));
        } else {
            getMemory().setTarget(possibleTargets.get(5));
        }
    }


    /**
     * When the player embodies the thief, choose the character to rob from the list of possibles targets.
     */
    @Override
    public void chooseTargetToRob() {
        List<Character> targets = Thief.getPossibleTargets();
        if (rand.nextBoolean()) {
            if (targets.contains(CharactersList.allCharacterCards[Role.KING.ordinal()])) {
                getMemory().setTarget(CharactersList.allCharacterCards[Role.KING.ordinal()]);
            } else {
                getMemory().setTarget(CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()]);
            }
        } else {
            if (targets.contains(CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()])) {
                getMemory().setTarget(CharactersList.allCharacterCards[Role.ARCHITECT.ordinal()]);
            } else {
                getMemory().setTarget(CharactersList.allCharacterCards[Role.KING.ordinal()]);
            }
        }
    }


    /**
     * Choose the power to use as a magician.
     */
    @Override
    public void chooseMagicianPower() {
        Character characterWithMostCards = Magician.getCharacterWithMostCards();

        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getMemory().setPowerToUse(Power.SWAP);
            getMemory().setTarget(characterWithMostCards);
        } else {
            getMemory().setPowerToUse(Power.RECYCLE);
            getHand().sortCards(Family.NEUTRAL);
            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getMemory().setNumberCardsToDiscard(nbCardsToDiscard + 1);
        }
        this.memory.setMomentWhenUse(Moment.BEFORE_RESOURCES);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return getGold() >= 6;
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return getActions().putRedundantCardsAtTheEnd() > 0 || getHand().size() > 4 || (getGold() < 5 && getHand().size() > 2);
    }


    /**
     * This bot wants to activate the graveyard's effect if the removed district's is more expensive than each district of his hand.
     *
     * @param removedDistrict the district removed by the Warlord.
     * @return a boolean value.
     */
    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict) &&
                (this.getHand().isEmpty() || (!this.getHand().isEmpty() && (this.getMostExpensiveCardInHand()[1] <= removedDistrict.getGoldCost() - 1))); // - 1 is to take into account the effect's cost.
    }

}