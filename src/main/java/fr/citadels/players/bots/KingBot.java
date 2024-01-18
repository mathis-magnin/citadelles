package fr.citadels.players.bots;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

/*
 * This bot will try to take the king character every time it can
 */
public class KingBot extends Player {

    /*
     * Constructor
     */
    public KingBot(String name, List<DistrictCard> cards, DistrictCardsPile pile, Bank bank, Display display, Random random) {
        super(name, cards, pile, bank, display);
        sortHand(CardFamily.NOBLE);
    }


    /*
     * Methods
     */


    /***
     * choose a NOBLE card if possible or the first card
     * drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        DistrictCard cardToPlay = drawnCards[0];
        //take the card that is noble if possible
        for (int i = 0; i < drawnCards.length; i++) {
            if (drawnCards[i].getCardFamily().equals(CardFamily.NOBLE)) {
                cardToPlay = drawnCards[i];
                putBack(drawnCards, i);
                return cardToPlay;
            }
        }

        putBack(drawnCards, 0);
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
     */
    @Override
    public void play() {
        boolean draw = getHand().isEmpty() || getHand().get(0).getGoldCost() < getGold();

        takeCardsOrGold(draw);

        if (!getHand().isEmpty()) {
            if (draw)
                sortHand(CardFamily.NOBLE);

            DistrictCard cardToPlace = chooseCardInHand();

            placeCard(cardToPlace);
        } else {
            this.display.addNoDistrictBuilt();
        }
        this.display.addBlankLine();
        takeGoldFromCity();
    }


    /**
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCardName().equals("Roi")) {
                this.setCharacter(characters.remove(i));
                this.display.addCharacterChosen(this, this.getCharacter());
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.setCharacter(characters.remove(0));
        this.display.addCharacterChosen(this, this.getCharacter());
    }


}
