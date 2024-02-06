package fr.citadels.players.bots;

import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.players.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * This bot has a more spending strategy
 * It will choose the cheapest cards and build districts as soon as it can to be the first to fill its city
 */
public class Spendthrift extends Player {

    private final Random RAND;
    /* Constructor */

    public Spendthrift(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        this.RAND = random;
    }

    /* Methods */

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
        }
        else {
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
        int minIndex = getCheapestCardInHand()[0];
        if ((minIndex >= 0) && (getHand().get(minIndex).getGoldCost() <= getGold()))
            this.getInformation().setDistrictToBuild(this.getActions().removeCardFromHand(minIndex));
        else
            this.getInformation().setDistrictToBuild(null);
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
        getInformation().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        if (RAND.nextBoolean()) {
            getInformation().setTarget(possibleTargets.get(4));
        } else {
            getInformation().setTarget(possibleTargets.get(5));
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
                getInformation().setTarget(CharactersList.allCharacterCards[3]);
            } else {
                getInformation().setTarget(CharactersList.allCharacterCards[6]);
            }
        } else {
            if (potentialTargets.contains(CharactersList.allCharacterCards[6])) {
                getInformation().setTarget(CharactersList.allCharacterCards[6]);
            } else {
                getInformation().setTarget(CharactersList.allCharacterCards[3]);
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

        // The magician will exchange his hand with the player with the most cards if this latter has more cards than the magician
        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getInformation().setPowerToUse(1);
            getInformation().setTarget(characterWithMostCards);
        } else { // else, the magician will discard cards he already has in his city
            getInformation().setPowerToUse(2);
            getHand().sortCards(Family.NEUTRAL);
            Collections.reverse(getHand());

            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getInformation().setCardsToDiscard(nbCardsToDiscard + 1);
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
        getInformation().setTarget(target);
        District districtToDestroy = null;
        if (target != null) {
            districtToDestroy = target.getPlayer().getCity().getCheapestDistrictToDestroy();
            if ((districtToDestroy != null) && (districtToDestroy.getGoldCost() - 1 > this.getGold())) {
                districtToDestroy = null;
            }
        }
        getInformation().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public void playResourcesPhase() {
        // Draw 2 cards or pick 2 golds
        // Draw if the player has less than 5 golds, if he has no cards in hand or if the cheapest card in hand costs more than 3
        // Else pick 2 golds
        boolean draw = ((getGold() > 5) || this.getHand().isEmpty() || (getCheapestCardInHand()[1] > 3));
        getActions().takeCardsOrGold(draw);

        if (this.equals(DistrictsPile.allDistrictCards[60].getOwner())) // Utilise le pouvoir du laboratoire
            DistrictsPile.allDistrictCards[60].useEffect();
    }


    @Override
    public void playBuildingPhase() {
        // Buy the cheapest card if possible
        if (!this.getHand().isEmpty()) {
            this.chooseDistrictToBuild();
            this.getActions().build();
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }
        getInformation().getDisplay().addBlankLine();
        getActions().takeGoldFromCity();
        if (this.equals(DistrictsPile.allDistrictCards[61].getOwner())) // Utilise le pouvoir de la manufacture
            DistrictsPile.allDistrictCards[61].useEffect();
    }


    @Override
    public boolean activateFactoryEffect() {
        return getGold() >= 3;
    }

    @Override
    public boolean activateLaboratoryEffect() {
        return this.getActions().putRedundantCardsAtTheEnd() > 0 || (getGold() < 2 && getHand().size() > 2) || getHand().size() > 4;
    }


    /**
     * This bot wants to activate the graveyard's effect if the removed district's is cheaper than each district of his hand
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    @Override
    public boolean activateGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict) &&
                (this.getHand().isEmpty() || (!this.getHand().isEmpty() && (removedDistrict.getGoldCost() + 1 <= this.getCheapestCardInHand()[1]))); // + 1 is to take into account the effect's cost.
    }
}