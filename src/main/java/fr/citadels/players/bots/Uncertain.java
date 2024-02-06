package fr.citadels.players.bots;


import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.players.Player;

import java.util.List;
import java.util.Random;

public class Uncertain extends Player {

    /* Constant */

    private final Random RAND;


    /* Constructor */

    public Uncertain(String name, List<District> cards, Game game, Random random) {
        super(name, cards, game);
        RAND = random;
    }

    public Uncertain(String name, Random random) {
        super(name);
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
    public District chooseCardAmongDrawn(District[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        District cardToPlay = drawnCards[randomIndex];
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
                this.getMemory().setDistrictToBuild(this.getActions().removeCardFromHand(i));
                return;
            }
        }
        this.getMemory().setDistrictToBuild(null);
    }


    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharactersList characters) {

        int randomIndex = -1;

        while (randomIndex >= characters.size() || randomIndex < 0) {
            randomIndex = RAND.nextInt(characters.size());
        }
        this.setCharacter(characters.remove(randomIndex));
        getMemory().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        int randIndex = RAND.nextInt(possibleTargets.size());
        getMemory().setTarget(possibleTargets.get(randIndex));
    }


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    @Override
    public void chooseTargetToRob() {
        List<Character> potentialTargets = Thief.getPossibleTargets();
        int randIndex = RAND.nextInt(potentialTargets.size());
        getMemory().setTarget(potentialTargets.get(randIndex));
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
            this.getMemory().setPowerToUse(1);
            int randTarget = RAND.nextInt(Magician.getPossibleTargets().size());
            this.getMemory().setTarget(Magician.getPossibleTargets().get(randTarget));
        } else { // discard cards : choose how many cards to discard
            this.getMemory().setPowerToUse(2);
            if (!this.getHand().isEmpty())
                this.getMemory().setCardsToDiscard(RAND.nextInt(this.getHand().size()));
            else this.getMemory().setCardsToDiscard(0);
        }
        return RAND.nextInt(3);
    }


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    @Override
    public void chooseTargetToDestroy() {
        Character target = null;
        District districtToDestroy = null;
        if (!Warlord.getPossibleTargets().isEmpty()) {
            target = Warlord.getPossibleTargets().get(RAND.nextInt(Warlord.getPossibleTargets().size()));
            if (!target.getPlayer().getCity().isEmpty()) {
                districtToDestroy = target.getPlayer().getCity().get(RAND.nextInt(target.getPlayer().getCity().size()));
                if (districtToDestroy.getGoldCost() - 1 > this.getGold()) {
                    districtToDestroy = null;
                }
            }
        }
        this.getMemory().setTarget(target);
        this.getMemory().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public void playResourcesPhase() {
        boolean draw;
        draw = !RAND.nextBoolean();
        getActions().takeCardsOrGold(draw);

        if (this.equals(DistrictsPile.allDistrictCards[60].getOwner())) // Utilise le pouvoir du laboratoire
            DistrictsPile.allDistrictCards[60].useEffect();
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
            getMemory().getDisplay().addNoDistrictBuilt();
        }

        getMemory().getDisplay().addBlankLine();
        if (!takeGoldFromFamily)
            getActions().takeGoldFromCity();
        if (this.equals(DistrictsPile.allDistrictCards[61].getOwner())) // Utilise le pouvoir de la manufacture
            DistrictsPile.allDistrictCards[61].useEffect();
    }


    @Override
    public boolean activateFactoryEffect() {
        return RAND.nextBoolean() && getGold() >= 3;
    }


    /**
     * This bot can randomly activate the graveyard's effect if he can afford it
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    @Override
    public boolean activateGraveyardEffect(District removedDistrict) {
        return RAND.nextBoolean() && (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict);
    }

    @Override
    public boolean activateLaboratoryEffect() {
        return RAND.nextBoolean() && !this.getHand().isEmpty();
    }
}
