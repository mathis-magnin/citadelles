package fr.citadels.players.bots;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to buy expensive cards to obtain the most points
 */
public class ThriftyBot extends Player {

    private final Random RAND;

    /* Constructor */

    public ThriftyBot(String name, List<DistrictCard> cards, DistrictCardsPile pile, Bank bank, Display display, Random random) {
        super(name, cards, pile, bank, display);
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
        putBack(drawnCards, maxIndex);
        return cardToPlay;
    }

    /**
     * Choose the most expensive card in hand with a cost > 1 that can be bought
     *
     * @return the card chosen or null if no card can be chosen
     * @precondition cardsInHand must contain at least 1 card
     */
    public void chooseCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()) && (!hasCardInCity(getHand().get(maxIndex))) && (getHand().get(i).getGoldCost() <= getGold()))
                maxIndex = i;
        }
        if ((getHand().get(maxIndex).getGoldCost() < getGold()) && (getHand().get(maxIndex).getGoldCost() > 1))
            this.setDistrictToBuild(removeCardFromHand(maxIndex));
        else
            this.setDistrictToBuild(null);
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

        this.display.addCharacterChosen(this, this.getCharacter());
    }


    @Override
    public void playResourcesPhase() {
        // Draw 2 cards or take 2 golds
        boolean draw = ((getGold() > 6) || (getHand().isEmpty()) || ((getGold() > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        takeGoldFromCity();
        takeCardsOrGold(draw);
    }


    @Override
    public void playBuildingPhase() {
        // Buy the most expensive card with a cost > 1 if possible
        if (!this.getHand().isEmpty()) {
            this.chooseCardInHand();
            this.build();
        } else {
            this.display.addNoDistrictBuilt();
        }
        this.display.addBlankLine();
    }

    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();

        if (RAND.nextBoolean()) {
            setTarget(CharacterCardsList.allCharacterCards[5]);
        }
        else {
            setTarget(CharacterCardsList.allCharacterCards[6]);
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
        this.setPowerToUse(1);  // draw two cards
        this.getCharacter().usePower();

        this.playResourcesPhase();
        this.playBuildingPhase();

        this.setPowerToUse(2);  // build another district
        this.chooseCardInHand();
        if (this.getDistrictToBuild() != null) this.getCharacter().usePower();
        else {
            this.display.addNoArchitectPower();
            this.display.addBlankLine();
        }

        this.chooseCardInHand();
        if (this.getDistrictToBuild() != null) this.getCharacter().usePower();
        else {
            this.display.addNoArchitectPower();
            this.display.addBlankLine();
        }
    }

    @Override
    public void playAsWarlord() {
        playResourcesPhase();

        playBuildingPhase();
    }

}
