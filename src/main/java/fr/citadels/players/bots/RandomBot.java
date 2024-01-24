package fr.citadels.players.bots;


import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

public class RandomBot extends Player {

    /* Constant */

    private final Random RAND;


    /* Constructor */

    public RandomBot(String name, List<DistrictCard> cards, Game game, Random random) {
        super(name, cards, game);
        RAND = random;
    }


    /* Methods */

    /***
     * choose a card to play among the cards drawn
     * drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        getActions().putBack(drawnCards, randomIndex);
        return cardToPlay;
    }


    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public void chooseDistrictToBuild() {
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i))) {
                this.getInformation().setDistrictToBuild(this.getActions().removeCardFromHand(i));
                return;
            }
        }
        this.getInformation().setDistrictToBuild(null);
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
        getInformation().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    @Override
    public void playResourcesPhase() {
        boolean draw;
        draw = !RAND.nextBoolean();
        getActions().takeCardsOrGold(draw);
    }


    @Override
    public void playBuildingPhase() {
        boolean takeGoldFromFamily;

        takeGoldFromFamily = RAND.nextBoolean();
        if (takeGoldFromFamily) getActions().takeGoldFromCity();

        boolean play;
        play = RAND.nextBoolean();

        if (play) {
            this.chooseDistrictToBuild();
            this.getActions().build();
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }

        getInformation().getDisplay().addBlankLine();
        if (!takeGoldFromFamily)
            getActions().takeGoldFromCity();
    }


    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();

        int randIndex = RAND.nextInt(CharacterCardsList.allCharacterCards.length - 1) + 1;
        getInformation().setTarget(CharacterCardsList.allCharacterCards[randIndex]);
        getCharacter().usePower();
    }


    @Override
    public void playAsThief() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsMagician() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsKing() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsBishop() {
        playResourcesPhase();
        playBuildingPhase();
    }


    @Override
    public void playAsMerchant() {
        playResourcesPhase();
        getCharacter().usePower();
        playBuildingPhase();
    }


    @Override
    public void playAsWarlord() {
        playResourcesPhase();
        playBuildingPhase();
    }

}
