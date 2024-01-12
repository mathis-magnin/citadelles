package fr.citadels.players;

import fr.citadels.cards.CardFamily;
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

    private boolean canPlaceCard;

    /*
     * Constructor
     */
    public KingBot(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
        cardsInHand.sortCards(CardFamily.NOBLE);
        RAND = random;
    }


    /*
     * Methods
     */


    /***
     * choose a NOBLE card if possible or the first card
     * drawnCards must contain at least 1 card
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        DistrictCard cardToPlay = drawnCards[0];
        //take the card that is noble if possible
        for (int i = 0; i < drawnCards.length; i++) {
            if (drawnCards[i].getCardFamily().equals(CardFamily.NOBLE)) {
                cardToPlay = drawnCards[i];
                putBack(drawnCards, pile, i);
                return cardToPlay;
            }
        }

        putBack(drawnCards, pile, 0);
        return cardToPlay;
    }

    /***
     * take the most expensive card that he can place (preferred noble)
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public DistrictCard chooseCardInHand() {
        DistrictCard cardToPlace = null;
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).getGoldCost() <= gold) {
                cardToPlace = cardsInHand.get(i);
                cardsInHand.remove(i);
                return cardToPlace;
            }
        }
        return cardToPlace;
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     */
    @Override
    public void play(DistrictCardsPile pile, Bank bank, Display events) {
        boolean draw = getCardsInHand().isEmpty() || getCardsInHand().get(0).getGoldCost() < getGold();

        takeCardsOrGold(pile, bank, draw, events);


        if (!cardsInHand.isEmpty()) {
            if (draw)
                cardsInHand.sortCards(CardFamily.NOBLE);

            DistrictCard cardToPlace = chooseCardInHand();

            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost(), bank);
                events.displayDistrictBuilt(this, cardToPlace);
            } else {
                events.displayNoDistrictBuilt(this);
            }
        } else events.displayNoDistrictBuilt(this);

        takeGoldFromCity(bank);
    }


    /**
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters, Display events) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCardName().equals("Roi")) {
                this.character = characters.remove(i);
                events.displayCharacterChosen(this, this.character);
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.character = characters.remove(0);
        events.displayCharacterChosen(this, this.character);
    }


}
