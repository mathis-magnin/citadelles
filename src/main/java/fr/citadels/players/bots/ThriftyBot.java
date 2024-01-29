package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import fr.citadels.gameelements.cards.charactercards.characters.MagicianCard;
import fr.citadels.gameelements.cards.charactercards.characters.ThiefCard;
import fr.citadels.gameelements.cards.charactercards.characters.WarlordCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to buy expensive cards to obtain the most points
 */
public class ThriftyBot extends Player {

    private final Random RAND;


    /* Constructor */

    public ThriftyBot(String name, List<DistrictCard> cards, Game game, Random random) {
        super(name, cards, game);
        this.RAND = random;
    }


    /* Methods */

    /**
     * Get the index and the cost of the most expensive card in the hand that can be bought
     */
    public int[] getMostExpensiveCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()) && (!hasCardInCity(getHand().get(maxIndex))))
                maxIndex = i;
        }
        int[] result = new int[2];
        result[0] = maxIndex;
        result[1] = getHand().get(maxIndex).getGoldCost();
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
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        int maxIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getGoldCost() > drawnCards[maxIndex].getGoldCost())
                maxIndex = i;
        }
        DistrictCard cardToPlay = drawnCards[maxIndex];
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
        int maxIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()) && (!hasCardInCity(getHand().get(maxIndex))) && (getHand().get(i).getGoldCost() <= getGold()))
                maxIndex = i;
        }
        if ((getHand().get(maxIndex).getGoldCost() < getGold()) && (getHand().get(maxIndex).getGoldCost() > 1))
            this.getInformation().setDistrictToBuild(this.getActions().removeCardFromHand(maxIndex));
        else
            this.getInformation().setDistrictToBuild(null);
    }


    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharacterCardsList characters) {

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
        CharacterCardsList possibleTargets = AssassinCard.getPossibleTargets();
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
        List<CharacterCard> potentialTargets = ThiefCard.getPossibleTargets();
        if (RAND.nextBoolean()) {
            if (potentialTargets.contains(CharacterCardsList.allCharacterCards[3])) {
                getInformation().setTarget(CharacterCardsList.allCharacterCards[3]);
            } else {
                getInformation().setTarget(CharacterCardsList.allCharacterCards[6]);
            }
        } else {
            if (potentialTargets.contains(CharacterCardsList.allCharacterCards[6])) {
                getInformation().setTarget(CharacterCardsList.allCharacterCards[6]);
            } else {
                getInformation().setTarget(CharacterCardsList.allCharacterCards[3]);
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
        CharacterCard characterWithMostCards = MagicianCard.getCharacterWithMostCards();

        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getInformation().setPowerToUse(1);
            getInformation().setTarget(characterWithMostCards);
        } else {
            getInformation().setPowerToUse(2);
            getHand().sortCards(CardFamily.NEUTRAL);
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
        CharacterCard target = WarlordCard.getOtherCharacterWithBiggestCity();
        getInformation().setTarget(target);
        DistrictCard districtToDestroy = null;
        if (target != null) {
            for (DistrictCard districtCard : target.getPlayer().getCity()) {
                if ((districtToDestroy == null) || (districtCard.getGoldCost() < districtToDestroy.getGoldCost())) {
                    districtToDestroy = districtCard;
                }
            }
            if ((districtToDestroy != null) && (districtToDestroy.getGoldCost() - 1 > this.getGold())) {
                districtToDestroy = null;
            }
        }
        getInformation().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public void playResourcesPhase() {
        // Draw 2 cards or take 2 golds
        boolean draw = ((getGold() > 6) || (getHand().isEmpty()) || ((getGold() > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        getActions().takeGoldFromCity();
        getActions().takeCardsOrGold(draw);
    }


    @Override
    public void playBuildingPhase() {
        // Buy the most expensive card with a cost > 1 if possible
        if (!this.getHand().isEmpty()) {
            this.chooseDistrictToBuild();
            this.getActions().build();
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }
        getInformation().getDisplay().addBlankLine();
    }

}
