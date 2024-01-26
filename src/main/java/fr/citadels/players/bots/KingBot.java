package fr.citadels.players.bots;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.charactercards.characters.AssassinCard;
import fr.citadels.gameelements.cards.charactercards.characters.ThiefCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
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
    public void chooseTargetToKill() {
        CharacterCardsList possibleTargets = AssassinCard.getPossibleTargets();
        getInformation().setTarget(possibleTargets.get(2));
    }


    @Override
    public void playResourcesPhase() {
        int firstNotDuplicateIndex = getCity().getFirstNotDuplicateIndex(getHand());
        boolean draw = getHand().isEmpty() || firstNotDuplicateIndex == -1 || getHand().get(firstNotDuplicateIndex).getGoldCost() < getGold();

        getActions().takeCardsOrGold(draw);

        if (draw) {
            getActions().sortHand(CardFamily.NOBLE);
        }
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
    }


    @Override
    public void playAsAssassin() {
        playResourcesPhase();
        ();
        chooseTargetToKill();
        getCharacter().usePower();
    }


    @Override
    public void playAsThief() {
        playResourcesPhase();
        playBuildingPhase();

        List<CharacterCard> potentialTargets = ThiefCard.getPossibleTargets();
        if (potentialTargets.contains(CharacterCardsList.allCharacterCards[3])) {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[3]);
        } else {
            getInformation().setTarget(CharacterCardsList.allCharacterCards[6]);
        }
        getCharacter().usePower();
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
        this.playResourcesPhase();

        this.getInformation().setPowerToUse(1);  // draw two cards
        this.getCharacter().usePower();

        this.playBuildingPhase();

        this.getInformation().setPowerToUse(2);  // build another district
        for (int i = 0; i < 2; i++) {
            this.chooseDistrictToBuild();
            if (this.getInformation().getDistrictToBuild() != null) {
                this.getCharacter().usePower();
            }
            else {
                this.getInformation().getDisplay().addNoArchitectPower();
                this.getInformation().getDisplay().addBlankLine();
            }
        }
    }


    @Override
    public void playAsWarlord() {
        playResourcesPhase();
        playBuildingPhase();
    }

}
