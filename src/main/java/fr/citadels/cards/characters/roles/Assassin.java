package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;

public class Assassin extends Character {

    /* Static content */

    /**
     * The Assassin must not target himself.
     *
     * @return The list of characters the Assassin can target.
     */
    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (!characterCard.equals(new Assassin())) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    /* Constructor */

    public Assassin() {
        super("Assassin", Family.NEUTRAL, 1);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsAssassin();
    }


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * KILL : The player kills the target he wants to.
     *
     * @precondition The player must have chosen which character he wants to target.
     */
    public void usePower() {
        getPlayer().getMemory().getTarget().setDead(true);
        getPlayer().getMemory().getDisplay().addAssassinPower(getPlayer().getMemory().getTarget());
        getPlayer().getMemory().getDisplay().addBlankLine();
    }

}