package fr.citadels.players.bots;

import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.engine.Game;
import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.charactercards.characters.AssassinCard;
import fr.citadels.cards.charactercards.characters.MagicianCard;
import fr.citadels.cards.charactercards.characters.ThiefCard;
import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.players.Player;

import java.util.List;

/*
 * This bot will try to take the king character every time it can
 */
public class KingBot extends Player {

    /* Constructor */

    public KingBot(String name, List<DistrictCard> cards, Game game) {
        super(name, cards, game);
        actions.sortHand(CardFamily.NOBLE);
    }


    /* Methods */

    /***
     * choose a NOBLE card if possible or the first card
     *
     * @precondition drawnCards must contain at least 1 card
     * @param drawnCards cards drawn
     * @return the card to play
     */
    @Override
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
     * set its districtToBuild attribute with the card chosen or null if no card can be chosen
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
     * Choose the king characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    @Override
    public void chooseCharacter(CharacterCardsList characters) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getCardName().equals("Roi")) {
                this.setCharacter(characters.remove(i));
                getInformation().getDisplay().addCharacterChosen(this, this.getCharacter());
                return;
            }
        }
        /*
         * cannot find the king character
         * Could happen if a player already took it
         */
        this.setCharacter(characters.remove(0));
        getInformation().getDisplay().addCharacterChosen(this, this.getCharacter());
    }


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    @Override
    public void chooseTargetToKill() {
        CharacterCardsList possibleTargets = AssassinCard.getPossibleTargets();
        getInformation().setTarget(possibleTargets.get(2));
    }


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    @Override
    public void chooseTargetToRob() {
        List<CharacterCard> potentialTargets = ThiefCard.getPossibleTargets();
        if (potentialTargets.contains(CharacterCardsList.allCharacterCards[3])) {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[3]);
        } else {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[6]);
        }
    }

    /**
     * Choose the power to use as a magician
     *
     * @return an int depending on the moment to use the power
     */
    @Override
    public int chooseMagicianPower() {
        CharacterCard characterWithMostCards = MagicianCard.getCharacterWithMostCards();

        if ((characterWithMostCards != null) && (characterWithMostCards.getPlayer().getHand().size() > this.getHand().size())) {
            getInformation().setPowerToUse(1);
            getInformation().setTarget(characterWithMostCards);
        } else {
            getInformation().setPowerToUse(2);
            getHand().sortCards(CardFamily.NOBLE);
            int nbCardsToDiscard = this.getActions().putRedundantCardsAtTheEnd();
            getInformation().setCardsToDiscard(nbCardsToDiscard + 1);
        }
        return 0;
    }


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    @Override
    public void chooseTargetToDestroy() {
        CharacterCard target = WarlordCard.getOtherCharacterWithBiggestCity();
        getInformation().setTarget(target);
        DistrictCard districtToDestroy = null;
        if (target != null) {
            for (DistrictCard districtCard : target.getPlayer().getCity()) {
                if ((districtToDestroy == null) || (districtCard.getGoldCost() < districtToDestroy.getGoldCost())) {
                    districtToDestroy = districtCard;
                }
            }
            if ((districtToDestroy != null) && (districtToDestroy.getGoldCost() - 1 > this.getGold())) {
                districtToDestroy = null;
            }
        }
        getInformation().setDistrictToDestroy(districtToDestroy);
    }


    @Override
    public void playResourcesPhase() {
        int firstNotDuplicateIndex = getCity().getFirstNotDuplicateIndex(getHand());
        boolean draw = getHand().isEmpty() || firstNotDuplicateIndex == -1 || getHand().get(firstNotDuplicateIndex).getGoldCost() < getGold();

        getActions().takeCardsOrGold(draw);

        if (draw) {
            getActions().sortHand(CardFamily.NOBLE);
        }

        if (this.equals(DistrictCardsPile.allDistrictCards[60].getOwner())) // Utilise le pouvoir du laboratoire
            DistrictCardsPile.allDistrictCards[60].useEffect();
    }


    @Override
    public void playBuildingPhase() {
        if (!getHand().isEmpty()) {
            this.chooseDistrictToBuild();
            this.getActions().build();
        } else {
            getInformation().getDisplay().addNoDistrictBuilt();
        }
        getInformation().getDisplay().addBlankLine();
        getActions().takeGoldFromCity();
        if (this.equals(DistrictCardsPile.allDistrictCards[61].getOwner())) // Utilise le pouvoir de la manufacture
            DistrictCardsPile.allDistrictCards[61].useEffect();
    }

    @Override
    public boolean activateFactoryEffect() {
        return getHand().size() < 2 && getGold() >= 3;
    }

    @Override
    public boolean activateLaboratoryEffect() {
        return this.getActions().putRedundantCardsAtTheEnd() > 0 || getHand().size() > 4 || (getHand().size() > 2 && getGold() < 2);
    }

}
