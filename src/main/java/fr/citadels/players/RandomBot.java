package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCard;
import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;
import fr.citadels.engine.Bank;
import fr.citadels.engine.Display;

import java.util.List;
import java.util.Random;

public class RandomBot extends Player {

    /*
     * constants
     */

    private final Random RAND;

    /*
     * Constructor
     */
    public RandomBot(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
        RAND = random;
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * drawnCards must contain at least 1 card
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        putBack(drawnCards, pile, randomIndex);
        return cardToPlay;
    }

    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public DistrictCard chooseCardInHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).getGoldCost() <= gold && !hasCardInCity(cardsInHand.get(i)))
                return cardsInHand.remove(i);
        }
        return null;
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     * @return the actions of the player
     */
    @Override
    public void play(DistrictCardsPile pile, Bank bank, Display display) {
        boolean draw;
        try {
            draw = !RAND.nextBoolean();
        } catch (Exception e) {
            draw = false; //take money (if possible) when exception raised
        }
        takeCardsOrGold(pile, bank, draw, display);

        boolean play;
        try {
            play = RAND.nextBoolean();
        } catch (Exception e) {
            play = false; //don't play when exception raised
        }

        if (play && !cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost(), bank);
                display.addDistrictBuilt(this, cardToPlace);
            } else {
                display.addNoDistrictBuilt(this);
            }
        } else display.addNoDistrictBuilt(this);
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
