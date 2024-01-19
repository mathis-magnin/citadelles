package fr.citadels.players.bots;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;

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
    public RandomBot(String name, List<DistrictCard> cards, DistrictCardsPile pile, Bank bank, Display display, Random random) {
        super(name, cards, pile, bank, display);
        RAND = random;
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        putBack(drawnCards, randomIndex);
        return cardToPlay;
    }

    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public DistrictCard chooseCardInHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i)))
                return removeCardFromHand(i);
        }
        return null;
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


    /***
     * play a round for the linked player
     */
    @Override
    public void play() {
        boolean takeGoldFromFamily;

        takeGoldFromFamily = RAND.nextBoolean();
        if (takeGoldFromFamily) takeGoldFromCity();

        boolean draw;
        draw = !RAND.nextBoolean();
        takeCardsOrGold(draw);


        boolean play;
        play = RAND.nextBoolean();

        if (play && !getHand().isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            placeCard(cardToPlace);
        } else {
            this.display.addNoDistrictBuilt();
        }

        this.display.addBlankLine();
        if (!takeGoldFromFamily)
            takeGoldFromCity();
    }

    @Override
    public void playAsAssassin() {
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
