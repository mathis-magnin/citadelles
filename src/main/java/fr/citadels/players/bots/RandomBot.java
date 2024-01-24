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
    public void chooseCardInHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i).getGoldCost() <= getGold() && !hasCardInCity(getHand().get(i))) {
                this.setDistrictToBuild(removeCardFromHand(i));
                return;
            }
        }
        this.setDistrictToBuild(null);
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


    @Override
    public void playResourcesPhase() {
        boolean draw;
        draw = !RAND.nextBoolean();
        takeCardsOrGold(draw);
    }


    @Override
    public void playBuildingPhase() {
        boolean takeGoldFromFamily;

        takeGoldFromFamily = RAND.nextBoolean();
        if (takeGoldFromFamily) takeGoldFromCity();

        boolean play;
        play = RAND.nextBoolean();

        if (play) {
            this.chooseCardInHand();
            this.build();
        } else {
            this.display.addNoDistrictBuilt();
        }

        this.display.addBlankLine();
        if (!takeGoldFromFamily)
            takeGoldFromCity();
    }

    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        playBuildingPhase();

        int randIndex = RAND.nextInt(CharacterCardsList.allCharacterCards.length - 1) + 1;
        setTarget(CharacterCardsList.allCharacterCards[randIndex]);
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
    public void playAsArchitect() {
        this.setPowerToUse(1);  // draw two cards
        this.getCharacter().usePower();

        this.playResourcesPhase();
        this.playBuildingPhase();

        this.setPowerToUse(2);  // build another district
        this.chooseCardInHand();
        if (this.getDistrictToBuild() != null) this.getCharacter().usePower();
        else {
            this.display.addNoArchitectPower();
            this.display.addBlankLine();
        }

        this.chooseCardInHand();
        if (this.getDistrictToBuild() != null) this.getCharacter().usePower();
        else {
            this.display.addNoArchitectPower();
            this.display.addBlankLine();
        }
    }

    @Override
    public void playAsWarlord() {
        playResourcesPhase();

        playBuildingPhase();
    }

}
