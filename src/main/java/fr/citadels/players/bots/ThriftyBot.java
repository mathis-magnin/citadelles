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
    public DistrictCard chooseCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < getHand().size(); i++) {
            if ((getHand().get(i).getGoldCost() > getHand().get(maxIndex).getGoldCost()) && (!hasCardInCity(getHand().get(maxIndex))) && (getHand().get(i).getGoldCost() <= getGold()))
                maxIndex = i;
        }
        if ((getHand().get(maxIndex).getGoldCost() < getGold()) && (getHand().get(maxIndex).getGoldCost() > 1))
            return removeCardFromHand(maxIndex);
        return null;
    }


    public void play() {

        // Draw 2 cards or take 2 golds
        boolean draw = ((getGold() > 15) || (getHand().isEmpty()) || ((getGold() > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        takeGoldFromCity();
        takeCardsOrGold(draw);

        // Buy the most expensive card with a cost > 1 if possible
        if (!this.getHand().isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            placeCard(cardToPlace);
        } else {
            this.display.addNoDistrictBuilt();
        }
        this.display.addBlankLine();
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

}
