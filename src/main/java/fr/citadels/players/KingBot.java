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
 * This bot will try to take the king character every time it can
 */
public class KingBot extends Player {
    private final Random RAND;

    /*
     * Constructor
     */
    public KingBot(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
        RAND = random;
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * /!\SAME AS RANDOMBOT FOR NOW /!\ /!\ SHOULD BE CHANGED WHEN DISTRICTS TYPES WILL BE IMPLEMENTED /!\
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
     * /!\SAME AS RANDOMBOT FOR NOW /!\ /!\ SHOULD BE CHANGED WHEN DISTRICTS TYPES WILL BE IMPLEMENTED /!\
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
     * /!\SAME AS RANDOMBOT FOR NOW /!\
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
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters, Display display) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCardName().equals("Roi")) {
                this.character = characters.remove(i);
                display.addCharacterChosen(this, this.character);
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.character = characters.remove(0);
        display.addCharacterChosen(this, this.character);
    }

}
