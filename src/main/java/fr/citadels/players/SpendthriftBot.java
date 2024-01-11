package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Display;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to buy expensive cards to obtain the most points
 */
public class SpendthriftBot extends Player {

    private final Random RAND;
    /* Constructor */

    public SpendthriftBot(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
        this.RAND = random;
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
     *
     * @param pile       pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     * @precondition drawnCards must contain at least 1 card
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
     *
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

    public void play(DistrictCardsPile pile, Display display) {

        // Draw 2 cards or take 2 golds
        boolean draw = ((gold > 15) || (cardsInHand.isEmpty()) || ((gold > 5) && (getMostExpensiveCardInHand()[1] < 4)));
        takeCardsOrGold(pile, draw, display);

        // Buy the most expensive card with a cost > 1 if possible
        if (!this.cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost());
                display.addDistrictBuilt(this, cardToPlace);
            } else {
                display.addNoDistrictBuilt(this);
            }
        } else {
            display.addNoDistrictBuilt(this);
        }
    }


    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters, Display display) {

        int randomIndex = -1;

        while (randomIndex >= characters.size() || randomIndex < 0) {
            try {
                randomIndex = RAND.nextInt(characters.size());
            } catch (Exception e) {
                randomIndex = -1;
            }
        }
        this.character = characters.remove(randomIndex);
        display.addCharacterChosen(this, this.character);
    }

}
