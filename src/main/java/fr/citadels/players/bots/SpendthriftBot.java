package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot has a more spending strategy
 * It will choose cheaper cards and buy cards as soon as it can to fill its city
 */
public class SpendthriftBot extends Player {

    private final Random RAND;
    /* Constructor */

    public SpendthriftBot(String name, List<DistrictCard> cards, Game game, Random random) {
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
        int minIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() < getHand().get(minIndex).getGoldCost()) && (!hasCardInCity(getHand().get(minIndex))))
                minIndex = i;
        }
        int[] result = new int[2];
        result[0] = minIndex;
        result[1] = getHand().get(minIndex).getGoldCost();
        return result;
    }

    /**
     * Choose the cheapest card among the cards drawn
     *
     * @param drawnCards cards drawn
     * @return the card to play
     * @precondition drawnCards must contain at least 1 card
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        int minIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getGoldCost() < drawnCards[minIndex].getGoldCost())
                minIndex = i;
        }
        DistrictCard cardToPlay = drawnCards[minIndex];
        getActions().putBack(drawnCards, minIndex);
        return cardToPlay;
    }

    /**
     * Choose the cheapest card in hand that can be bought
     *
     * @return the card chosen or null if no card can be chosen
     * @precondition cardsInHand must contain at least 1 card
     */
    public DistrictCard chooseCardInHand() {
        int minIndex = getCheapestCardInHand()[0];
        if (getHand().get(minIndex).getGoldCost() <= getGold())
            return getActions().removeCardFromHand(minIndex);
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
        // Draw 2 cards or pick 2 golds
        // Draw if the player has less than 5 golds, if he has no cards in hand or if the cheapest card in hand costs more than 3
        // Else pick 2 golds
        boolean draw = ((getGold() > 5) || this.getHand().isEmpty() || (getCheapestCardInHand()[1] > 3));
        getActions().takeCardsOrGold(draw);
    }

    @Override
    public void playBuildingPhase() {
        // Buy the cheapest card if possible
        if (!this.getHand().isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            getActions().placeCard(cardToPlace);
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }
        getInformation().getDisplay().addBlankLine();
        getActions().takeGoldFromCity();
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