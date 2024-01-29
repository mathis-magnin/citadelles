package fr.citadels.players.bots;


import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import fr.citadels.gameelements.cards.charactercards.characters.MagicianCard;
import fr.citadels.gameelements.cards.charactercards.characters.ThiefCard;
import fr.citadels.gameelements.cards.charactercards.characters.WarlordCard;
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
     *
     * @precondition drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    @Override
    public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        getActions().putBack(drawnCards, randomIndex);
        return cardToPlay;
    }


    /***
     * choose a card in hand
     * set the card chosen or null if no card can be chosen in the districtToBuild attribute
     */
    @Override
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
    @Override
    public void chooseCharacter(CharacterCardsList characters) {

        int randomIndex = -1;

        while (randomIndex >= characters.size() || randomIndex < 0) {
            randomIndex = RAND.nextInt(characters.size());
        }
        this.setCharacter(characters.remove(randomIndex));
        getInformation().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharacterCardsList possibleTargets = AssassinCard.getPossibleTargets();
        int randIndex = RAND.nextInt(possibleTargets.size());
        getInformation().setTarget(possibleTargets.get(randIndex));
    }


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    @Override
    public void chooseTargetToRob() {
        List<CharacterCard> potentialTargets = ThiefCard.getPossibleTargets();
        int randIndex = RAND.nextInt(potentialTargets.size());
        getInformation().setTarget(potentialTargets.get(randIndex));
    }


    /**
     * Choose the power to use as a magician
     *
     * @return an int depending on the moment to use the power
     */
    @Override
    public int chooseMagicianPower() {
        int randPower = RAND.nextInt(2); // choose which power to use

        if (randPower == 0) { // swap hands : choose a target
            this.getInformation().setPowerToUse(1);
            int randTarget = RAND.nextInt(MagicianCard.getPossibleTargets().size());
            this.getInformation().setTarget(MagicianCard.getPossibleTargets().get(randTarget));
        } else { // discard cards : choose how many cards to discard
            this.getInformation().setPowerToUse(2);
            this.getInformation().setCardsToDiscard(RAND.nextInt(this.getHand().size()));
        }
        return RAND.nextInt(3);
    }


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    @Override
    public void chooseTargetToDestroy() {
        int randTarget = RAND.nextInt(WarlordCard.getPossibleTargets().size());
        CharacterCard target = WarlordCard.getPossibleTargets().get(randTarget);
        this.getInformation().setTarget(target);
        DistrictCard districtToDestroy = null;
        if (!target.getPlayer().getCity().isEmpty()) {
            randTarget = RAND.nextInt(target.getPlayer().getCity().size());
            districtToDestroy = target.getPlayer().getCity().get(randTarget);
            if (districtToDestroy.getGoldCost() - 1 > this.getGold()) {
                districtToDestroy = null;
            }
        }
        this.getInformation().setDistrictToDestroy(districtToDestroy);
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

}
