package fr.citadels.players.bots;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.Assassin;
import fr.citadels.cards.charactercards.characters.Magician;
import fr.citadels.cards.charactercards.characters.Thief;
import fr.citadels.cards.charactercards.characters.Warlord;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;

import java.util.List;

/*
 * This bot will try to take the king character every time it can
 */
public class Monarchist extends Player {

    /* Constructor */

    public Monarchist(String name, List<District> cards, Game game) {
        super(name, cards, game);
        actions.sortHand(Family.NOBLE);
    }

    public Monarchist(String name) {
        super(name);
    }

    /* Methods */

    /**
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharactersList characters) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getName().equals("Roi")) {
                this.setCharacter(characters.remove(i));
                return;
            }
        }
        // Cannot find the king character, it could happen if a player already took it or if it is placed face down
        this.setCharacter(characters.remove(0));
    }


    /**
     * Draw if his hand is empty, or if he only has district which are already built in his city, or if he can build the district he wants.
     */
    @Override
    public void chooseDraw() {
        int firstNotDuplicateIndex = getCity().getFirstNotDuplicateIndex(getHand());
        this.memory.setDraw(getHand().isEmpty() || firstNotDuplicateIndex == -1 || getHand().get(firstNotDuplicateIndex).getGoldCost() < getGold());
    }


    /***
     * choose a NOBLE card if possible or the first card
     *
     * @precondition drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    @Override
    public District chooseCardAmongDrawn(District[] drawnCards) {
        District cardToPlay = drawnCards[0];
        //take the card that is noble if possible
        for (int i = 0; i < drawnCards.length; i++) {
            if (drawnCards[i].getFamily().equals(Family.NOBLE)) {
                cardToPlay = drawnCards[i];
                getActions().putBack(drawnCards, i);
                return cardToPlay;
            }
        }

        getActions().putBack(drawnCards, 0);
        return cardToPlay;
    }


    /**
     * Choose to take gold from city after he built another district from his family or before otherwise.
     */
    @Override
    public void chooseMomentToTakeIncome() {
        this.chooseDistrictToBuild();
        if (this.memory.getDistrictToBuild() != null) {
            this.memory.setMomentWhenUse((this.memory.getDistrictToBuild().getFamily().equals(this.getCharacter().getFamily())) ? Moment.AFTER_BUILDING : Moment.BETWEEN_PHASES);
        } else {
            this.memory.setMomentWhenUse(Moment.BETWEEN_PHASES);
        }
    }


    /**
     * Take the most expensive card that he can place (preferred noble)
     * set its districtToBuild attribute with the card chosen or null if no card can be chosen
     */
    @Override
    public void chooseDistrictToBuild() {
        this.getActions().sortHand(Family.NOBLE);
        for (int i = 0; i < getHand().size(); i++) {
            if (this.getHand().get(i).getGoldCost() <= getGold() && !this.hasCardInCity(getHand().get(i))) {
                this.getMemory().setDistrictToBuild(this.getHand().get(i));
                return;
            }
        }
        this.getMemory().setDistrictToBuild(null);
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharactersList possibleTargets = Assassin.getPossibleTargets();
        getMemory().setTarget(possibleTargets.get(2));
    }


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    @Override
    public void chooseTargetToRob() {
        List<Character> potentialTargets = Thief.getPossibleTargets();
        if (potentialTargets.contains(CharactersList.allCharacterCards[3])) {
            getMemory().setTarget(CharactersList.allCharacterCards[3]);
        } else {
            getMemory().setTarget(CharactersList.allCharacterCards[6]);
        }
    }


    /**
     * Choose the power to use as a magician.
     */
    @Override
    public void chooseMagicianPower() {
        Character characterWithMostCards = Magician.getCharacterWithMostCards();

        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getMemory().setPowerToUse(Power.SWAP);
            getMemory().setTarget(characterWithMostCards);
        } else {
            getMemory().setPowerToUse(Power.RECYCLE);
            getHand().sortCards(Family.NOBLE);
            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getMemory().setCardsToDiscard(nbCardsToDiscard + 1);
        }
        this.memory.setMomentWhenUse(Moment.BEFORE_RESSOURCES);
    }


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    @Override
    public void chooseTargetToDestroy() {
        Character target = Warlord.getOtherCharacterWithBiggestCity();
        getMemory().setTarget(target);
        District districtToDestroy = null;
        if (target != null) {
            districtToDestroy = target.getPlayer().getCity().getCheapestDistrictToDestroy();
            if ((districtToDestroy != null) && (districtToDestroy.getGoldCost() - 1 > this.getGold())) {
                districtToDestroy = null;
            }
        }
        getMemory().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public boolean chooseFactoryEffect() {
        return getHand().size() < 2 && getGold() >= 3;
    }


    @Override
    public boolean chooseLaboratoryEffect() {
        return this.getActions().putRedundantCardsAtTheEnd() > 0 || getHand().size() > 4 || (getHand().size() > 2 && getGold() < 2);
    }


    /**
     * This bot wants to activate the graveyard's effect if the removed district is NOBLE
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    @Override
    public boolean chooseGraveyardEffect(District removedDistrict) {
        return (1 <= this.getGold()) && !this.hasCardInCity(removedDistrict) && removedDistrict.getFamily().equals(Family.NOBLE);
    }

}
