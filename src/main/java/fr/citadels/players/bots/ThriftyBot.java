package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
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
    public DistrictCard chooseCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()) && (!hasCardInCity(getHand().get(maxIndex))) && (getHand().get(i).getGoldCost() <= getGold()))
                maxIndex = i;
        }
        if ((getHand().get(maxIndex).getGoldCost() < getGold()) && (getHand().get(maxIndex).getGoldCost() > 1))
            return getActions().removeCardFromHand(maxIndex);
        return null;
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
            DistrictCard cardToPlace = chooseCardInHand();
            getActions().placeCard(cardToPlace);
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }
        getInformation().getDisplay().addBlankLine();
    }

    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();

        if (RAND.nextBoolean()) {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[5]);
        } else {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[6]);
        }
        getCharacter().usePower();
    }

    @Override
    public void playAsThief() {
        playResourcesPhase();

        playBuildingPhase();
    }

    @Override
    public void playAsMagician() {
        playResourcesPhase();

        playBuildingPhase();
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
        playResourcesPhase();

        playBuildingPhase();
    }

    @Override
    public void playAsWarlord() {
        playResourcesPhase();

        playBuildingPhase();
    }

}
