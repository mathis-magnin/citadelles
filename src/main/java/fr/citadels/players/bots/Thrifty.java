package fr.citadels.players.bots;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to buy expensive cards to obtain the most points
 */
public class Thrifty extends Player {

    private final Random RAND;


    /* Constructor */

    public Thrifty(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        this.RAND = random;
    }


    /* Methods */

    /**
     * Get the index and the cost of the most expensive card in the hand that can be bought
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
        }
        else {
            result[1] = getHand().get(maxIndex).getGoldCost();
        }
        return result;
    }


    /**
     * Choose the most expensive card among the cards drawn
     *
     * @param drawnCards cards drawn
     * @return the card to play
     * @precondition drawnCards must contain at least 1 card
     */
    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        int maxIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getGoldCost() > drawnCards[maxIndex].getGoldCost())
                maxIndex = i;
        }
        District cardToPlay = drawnCards[maxIndex];
        getActions().putBack(drawnCards, maxIndex);
        return cardToPlay;
    }


    /**
     * Choose the most expensive card in hand with a cost > 1 that can be bought
     * set its districtToBuild attribute with the card chosen or null if no card can be chosen
     *
     * @precondition cardsInHand must contain at least 1 card
     */
    @Override
    public void chooseDistrictToBuild() {
        int maxIndex = getMostExpensiveCardInHand()[0];
        if ((maxIndex >= 0) && (getHand().get(maxIndex).getGoldCost() <= getGold()))
            this.getMemory().setDistrictToBuild(this.getActions().removeCardFromHand(maxIndex));
        else
            this.getMemory().setDistrictToBuild(null);
    }


    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharactersList characters) {

        int randomIndex = -1;

        while (randomIndex >= characters.size() || randomIndex < 0) {
            randomIndex = RAND.nextInt(characters.size());
        }
        this.setCharacter(characters.remove(randomIndex));

        getMemory().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        if (RAND.nextBoolean()) {
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
        List<Character> potentialTargets = Thief.getPossibleTargets();
        if (RAND.nextBoolean()) {
            if (potentialTargets.contains(CharactersList.allCharacterCards[3])) {
                getMemory().setTarget(CharactersList.allCharacterCards[3]);
            } else {
                getMemory().setTarget(CharactersList.allCharacterCards[6]);
            }
        } else {
            if (potentialTargets.contains(CharactersList.allCharacterCards[6])) {
                getMemory().setTarget(CharactersList.allCharacterCards[6]);
            } else {
                getMemory().setTarget(CharactersList.allCharacterCards[3]);
            }
        }
    }


    /**
     * Choose the power to use as a magician
     *
     * @return an int depending on the moment to use the power
     */
    @Override
    public int chooseMagicianPower() {
        Character characterWithMostCards = Magician.getCharacterWithMostCards();

        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getMemory().setPowerToUse(1);
            getMemory().setTarget(characterWithMostCards);
        } else {
            getMemory().setPowerToUse(2);
            getHand().sortCards(Family.NEUTRAL);
            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getMemory().setCardsToDiscard(nbCardsToDiscard + 1);
        }
        return 0;
    }


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    @Override
    public void chooseTargetToDestroy() {
        Character target = Warlord.getOtherCharacterWithBiggestCity();
        getMemory().setTarget(target);
        District districtToDestroy = null;
        if (target != null) {
            districtToDestroy = target.getPlayer().getCity().getCheapestDistrictToDestroy();
            if ((districtToDestroy != null) && (districtToDestroy.getGoldCost() - 1 > this.getGold())) {
                districtToDestroy = null;
            }
        }
        getMemory().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public void playResourcesPhase() {
        // Draw 2 cards or take 2 golds
        boolean draw = ((getGold() > 6) || (getHand().isEmpty()) || ((getGold() > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        getActions().takeGoldFromCity();
        getActions().takeCardsOrGold(draw);

        if (this.equals(DistrictsPile.allDistrictCards[60].getOwner())) // Utilise le pouvoir du laboratoire
            DistrictsPile.allDistrictCards[60].useEffect();
    }


    @Override
    public void playBuildingPhase() {
        // Buy the most expensive card with a cost > 1 if possible
        if (!this.getHand().isEmpty()) {
            this.chooseDistrictToBuild();
            this.getActions().build();
        } else {
            getMemory().getDisplay().addNoDistrictBuilt();
        }
        getMemory().getDisplay().addBlankLine();
        if (this.equals(DistrictsPile.allDistrictCards[61].getOwner())) // Utilise le pouvoir de la manufacture
            DistrictsPile.allDistrictCards[61].useEffect();
    }

    @Override
    public boolean activateFactoryEffect() {
        return getGold() >= 6;
    }

    @Override
    public boolean activateLaboratoryEffect() {
        return getActions().putRedundantCardsAtTheEnd() > 0 || getHand().size() > 4 || (getGold() < 5 && getHand().size() > 2);
    }


    /**
     * This bot wants to activate the graveyard's effect if the removed district's is more expensive than each district of his hand
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    @Override
    public boolean activateGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict) &&
                (this.getHand().isEmpty() || (!this.getHand().isEmpty() && (this.getMostExpensiveCardInHand()[1] <= removedDistrict.getGoldCost() - 1))); // - 1 is to take into account the effect's cost.
    }

}
