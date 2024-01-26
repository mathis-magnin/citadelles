package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import fr.citadels.gameelements.cards.charactercards.characters.MagicianCard;
import fr.citadels.gameelements.cards.charactercards.characters.ThiefCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.Collections;
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
     *
     * @return the card chosen or null if no card can be chosen
     * @precondition cardsInHand must contain at least 1 card
     */
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
    public void chooseTargetToKill() {
        CharacterCardsList possibleTargets = AssassinCard.getPossibleTargets();
        if (RAND.nextBoolean()) {
            getInformation().setTarget(possibleTargets.get(4));
        } else {
            getInformation().setTarget(possibleTargets.get(5));
        }
    }


    /**
     * Choose the power to use as a magician
     * @return an int depending on the moment to use the power
     */
    public int chooseMagicianPower() {
        Player playerWithMostCards = MagicianCard.getPlayerWithMostCards();

        if ((playerWithMostCards != null) && (playerWithMostCards.getHand().size() > this.getHand().size())) {
            getInformation().setPowerToUse(1);
            getInformation().setTarget(playerWithMostCards.getCharacter());
        } else {
            getInformation().setPowerToUse(2);
            getHand().sortCards(CardFamily.NEUTRAL);
            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getInformation().setCardsToDiscard(nbCardsToDiscard + 1);
        }
        return 0;
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


    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();
        chooseTargetToKill();
        getCharacter().usePower();
    }


    @Override
    public void playAsThief() {
        playResourcesPhase();
        playBuildingPhase();

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
        getCharacter().usePower();
    }


    @Override
    public void playAsMagician() {
        int momentToUsePower = chooseMagicianPower();

        if (momentToUsePower == 0) {
            this.getCharacter().usePower();
        }
        playResourcesPhase();
        if (momentToUsePower == 1) {
            this.getCharacter().usePower();
        }
        playBuildingPhase();
        if (momentToUsePower == 2) {
            this.getCharacter().usePower();
        }
    }


    @Override
    public void playAsKing() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsBishop() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsMerchant() {
        playResourcesPhase();
        getCharacter().usePower();
        playBuildingPhase();
    }


    @Override
    public void playAsArchitect() {
        this.playResourcesPhase();

        this.getInformation().setPowerToUse(1);  // draw two cards
        this.getCharacter().usePower();

        this.playBuildingPhase();

        this.getInformation().setPowerToUse(2);  // build another district
        for (int i = 0; i < 2; i++) {
            this.chooseDistrictToBuild();
            if (this.getInformation().getDistrictToBuild() != null) {
                this.getCharacter().usePower();
            } else {
                this.getInformation().getDisplay().addNoArchitectPower();
                this.getInformation().getDisplay().addBlankLine();
            }
        }
    }


    @Override
    public void playAsWarlord() {
        playResourcesPhase();
        playBuildingPhase();
    }

}
