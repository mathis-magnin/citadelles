package fr.citadels.players.bots;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.MagicianCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
import fr.citadels.players.Player;

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
        sortHand(CardFamily.NOBLE);
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
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i).getGoldCost() <= getGold()) {
                cardToPlace = getHand().get(i);
                removeCardFromHand(i);
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
    public void play(DistrictCardsPile pile, Bank bank, Display display) {
        boolean draw = getHand().isEmpty() || getHand().get(0).getGoldCost() < getGold();

        takeCardsOrGold(pile, bank, draw, display);

        // ((MagicianCard) (this.getCharacter())).exchangeHands();

        if (!getHand().isEmpty()) {
            if (draw)
                sortHand(CardFamily.NOBLE);

            DistrictCard cardToPlace = chooseCardInHand();

            if (cardToPlace != null) {
                addCardToCity(cardToPlace);
                pay(cardToPlace.getGoldCost(), bank);
                display.addDistrictBuilt(this, cardToPlace);
            } else {
                display.addNoDistrictBuilt();
            }
        } else {
            display.addNoDistrictBuilt();
        }
        display.addBlankLine();
        takeGoldFromCity(bank, display);
    }


    /**
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters, Display display) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCardName().equals("Roi")) {
                this.setCharacter(characters.remove(i));
                display.addCharacterChosen(this, this.getCharacter());
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.setCharacter(characters.remove(0));
        display.addCharacterChosen(this, this.getCharacter());
    }


}
