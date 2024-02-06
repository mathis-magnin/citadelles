package fr.citadels.players;

import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;

public interface Choices {

    /**
     * choose a card to take among the cards drawn
     *
     * @param drawnCards cards drawn
     * @return the card to play
     */
    District chooseCardAmongDrawn(District[] drawnCards);


    /**
     * choose a district in hand to build
     * set the player's districtToBuild attribute with the card chosen or null if no card can be chosen
     */
    void chooseDistrictToBuild();


    /**
     * Choose and take a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    void chooseCharacter(CharactersList characters);


    /**
     * When the player embodies the assassin, choose the
     * character to kill from the list of possibles targets
     */
    void chooseTargetToKill();


    /**
     * When the player embodies the thief, choose the
     * character to rob from the list of possibles targets
     */
    void chooseTargetToRob();


    /**
     * When the player embodies the magician, choose which power he should use, when he should use it,
     * and consequently either the character to exchange his hand with from the list of possibles targets
     * or the cards from his hands he should and replace by cards from the pile
     */
    int chooseMagicianPower();


    /**
     * When the player embodies the warlord, choose the character and the
     * district in city to destroy from the list of possibles targets
     */
    void chooseTargetToDestroy();


    /**
     * play the phase when the player takes resources for his turn
     */
    void playResourcesPhase();


    /**
     * play the phase when the player builds districts in his city
     */
    void playBuildingPhase();


    /**
     * activate the effect of the factory if the player can
     *
     * @return a boolean true if we want to activate the effect, else otherwise
     */
    boolean activateFactoryEffect();

    /**
     * activate the effect of the laboratory if the player can
     *
     * @return a boolean true if we want to activate the effect, else otherwise
     */
    boolean activateLaboratoryEffect();


    /**
     * Choose if the player wants to use graveyard's effect
     *
     * @param removedDistrict the district removed by the Warlord
     * @return a boolean value
     */
    boolean activateGraveyardEffect(District removedDistrict);

}