package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.List;

/*
 * This bot will try to take the king character every time it can
 */
public class KingBot extends Player {

    /*
     * Constructor
     */
    public KingBot(String name, List<DistrictCard> cards, Game game) {
        super(name, cards, game);
        actions.sortHand(CardFamily.NOBLE);
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
                getActions().putBack(drawnCards, i);
                return cardToPlay;
            }
        }

        getActions().putBack(drawnCards, 0);
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
            if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i))) {
                cardToPlace = getHand().get(i);
                getActions().removeCardFromHand(i);
                return cardToPlace;
            }
        }
        return cardToPlace;
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
                getGame().getDisplay().addCharacterChosen(this, this.getCharacter());
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.setCharacter(characters.remove(0));
        getGame().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /***
     * play a round for the linked player
     */
    @Override
    public void play() {
        int firstNotDuplicateIndex = getCity().getFirstNotDuplicateIndex(getHand());
        boolean draw = getHand().isEmpty() || firstNotDuplicateIndex == -1 || getHand().get(firstNotDuplicateIndex).getGoldCost() < getGold();

        getActions().takeCardsOrGold(draw);

        if (!getHand().isEmpty()) {
            if (draw)
                getActions().sortHand(CardFamily.NOBLE);

            DistrictCard cardToPlace = chooseCardInHand();

            getActions().placeCard(cardToPlace);
        } else {
            getGame().getDisplay().addNoDistrictBuilt();
        }
        getGame().getDisplay().addBlankLine();
        getActions().takeGoldFromCity();
    }


    @Override
    public void playAsAssassin() {
        setTarget(CharacterCardsList.allCharacterCards[3]);
        getCharacter().usePower();
        this.play();
    }

    @Override
    public void playAsThief() {
        this.play();
    }

    @Override
    public void playAsMagician() {
        this.play();
    }

    @Override
    public void playAsKing() {
        this.play();
    }

    @Override
    public void playAsBishop() {
        this.play();
    }

    @Override
    public void playAsMerchant() {
        this.play();
    }

    @Override
    public void playAsArchitect() {
        this.play();
    }

    @Override
    public void playAsWarlord() {
        this.play();
    }


}
