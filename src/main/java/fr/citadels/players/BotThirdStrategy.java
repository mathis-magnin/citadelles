package fr.citadels.players;

import fr.citadels.cards.DistrictCard;
import fr.citadels.cards.DistrictCardsPile;

import java.util.List;

/*
 * This bot will try to by expensive cards to obtain the most points
 */
public class BotThirdStrategy extends Player {

    /* Constructor */

public BotThirdStrategy(String name, List<DistrictCard> cards) {
        super(name, cards);
    }

    /* Methods */

    /**
     * Get the index and the cost of the most expensive card in the hand that can be bought
     */
    public int[] getMostExpensiveCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < cardsInHand.size(); i++) {
            if ((cardsInHand.get(i).getGoldCost() > cardsInHand.get(maxIndex).getGoldCost()) && (!hasCardInCity(cardsInHand.get(maxIndex))))
                maxIndex = i;
        }
        int[] result = new int[2];
        result[0] = maxIndex;
        result[1] = cardsInHand.get(maxIndex).getGoldCost();
        return result;
    }

    /**
     * Choose the most expensive card among the cards drawn
     * @precondition drawnCards must contain at least 1 card
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int maxIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getGoldCost() > drawnCards[maxIndex].getGoldCost())
                maxIndex = i;
        }
        DistrictCard cardToPlay = drawnCards[maxIndex];
        putBack(drawnCards, pile, maxIndex);
        return cardToPlay;
    }

    /**
     * Choose the most expensive card in hand with a cost > 1 that can be bought
     * @return the card chosen or null if no card can be chosen
     * @precondition cardsInHand must contain at least 1 card
     */
    public DistrictCard chooseCardInHand() {
        int maxIndex = 0;
        for (int i = 1; i < cardsInHand.size(); i++) {
            if ((cardsInHand.get(i).getGoldCost() > cardsInHand.get(maxIndex).getGoldCost()) && (!hasCardInCity(cardsInHand.get(maxIndex))) && (cardsInHand.get(i).getGoldCost() <= gold))
                maxIndex = i;
        }
        if ((cardsInHand.get(maxIndex).getGoldCost() < gold) && (cardsInHand.get(maxIndex).getGoldCost() > 1))
            return cardsInHand.remove(maxIndex);
        return null;
    }

    public String play(DistrictCardsPile pile){
        StringBuilder actions = new StringBuilder();
        actions.append(this.getName());

        // Draw 2 cards or take 2 golds
        boolean draw = ((gold > 15) || (cardsInHand.isEmpty()) || ((gold > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        if (!draw) {
            try {
                addGold(2);
            } catch (IllegalArgumentException e) {
                draw = true;
            }
        }
        if (draw) {
            DistrictCard[] drawnCards = pile.draw(2);
            if (drawnCards.length != 0) {//if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(pile, drawnCards);
                cardsInHand.add(cardToPlay);
            }
        }

        // Buy the most expensive card with a cost > 1 if possible
        if (!this.cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost());
                actions.append(" a ajouté a sa ville : ").append(cardToPlace.getCardName());
            } else {
                actions.append(" n'a pas construit ce tour-ci");
            }
        } else {
            actions.append(" n'a pas construit ce tour-ci");
        }

        return actions.toString();
    }
}
