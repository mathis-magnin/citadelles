package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Bank;
import fr.citadels.engine.Display;

import java.util.List;
import java.util.Random;

/*
 * This bot has a more spending strategy
 * It will choose cheaper cards and buy cards as soon as it can to fill its city
 */
public class ThriftyBot extends Player {

    private final Random RAND;
    /* Constructor */

    public ThriftyBot(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
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
        for (int i = 1; i < cardsInHand.size(); i++) {
            if ((cardsInHand.get(i).getGoldCost() < cardsInHand.get(minIndex).getGoldCost()) && (!hasCardInCity(cardsInHand.get(minIndex))))
                minIndex = i;
        }
        int[] result = new int[2];
        result[0] = minIndex;
        result[1] = cardsInHand.get(minIndex).getGoldCost();
        return result;
    }

    /**
     * Choose the cheapest card among the cards drawn
     *
     * @param pile       pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     * @precondition drawnCards must contain at least 1 card
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int minIndex = 0;
        for (int i = 1; i < drawnCards.length; i++) {
            if (drawnCards[i].getGoldCost() < drawnCards[minIndex].getGoldCost())
                minIndex = i;
        }
        DistrictCard cardToPlay = drawnCards[minIndex];
        putBack(drawnCards, pile, minIndex);
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
        if (cardsInHand.get(minIndex).getGoldCost() <= gold)
            return cardsInHand.remove(minIndex);
        return null;
    }

    /**
     * Play a round for the linked player
     *
     * @param pile of cards
     * @return the actions of the player
     */
    public void play(DistrictCardsPile pile, Bank bank, Display display) {

        // Draw 2 cards or pick 2 golds
        // Draw if the player has less than 5 golds, if he has no cards in hand or if the cheapest card in hand costs more than 3
        // Else pick 2 golds
        boolean draw = ((gold > 5) || this.cardsInHand.isEmpty() || (getCheapestCardInHand()[1] > 3));

        takeCardsOrGold(pile, bank, draw, display);

        // Buy the cheapest card if possible
        if (!this.cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost(), bank);
                display.addDistrictBuilt(cardToPlace);
                display.addCity(this.cityCards);
            } else {
                display.addNoDistrictBuilt();
            }
        } else {
            display.addNoDistrictBuilt();
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