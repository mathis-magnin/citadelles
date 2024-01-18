package fr.citadels.players.bots;

import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.Bank;
import fr.citadels.engine.Display;
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
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i)))
                return removeCardFromHand(i);
        }
        return null;
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     */
    @Override
    public void play(DistrictCardsPile pile, Bank bank, Display display) {
        boolean takeGoldFromFamily;
        try {
            takeGoldFromFamily = RAND.nextBoolean();
            if (takeGoldFromFamily) takeGoldFromCity(bank, display);
        } catch (Exception e) {
            takeGoldFromFamily = false; //don't play when exception raised
        }

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

        if (play && !getHand().isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();

            placeCard(cardToPlace, bank, display);
        } else {
            display.addNoDistrictBuilt();
        }

        display.addBlankLine();
        if (!takeGoldFromFamily)
            takeGoldFromCity(bank, display);
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
        this.setCharacter(characters.remove(randomIndex));
        display.addCharacterChosen(this, this.getCharacter());
    }

}
