package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;

import java.util.ArrayList;
import java.util.List;

public class Assassin extends Character {

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
    @Override
    public void usePower() {
        getPlayer().getMemory().getTarget().setDead(true);
        getPlayer().getMemory().getDisplay().addAssassinPower(getPlayer().getMemory().getTarget());
        getPlayer().getMemory().getDisplay().addBlankLine();
    }


    /**
     * The Assassin must not target himself.
     *
     * @return The list of characters the Assassin can target.
     */
    @Override
    public List<Character> getPossibleTargets() {
        List<Character> targets = new ArrayList<>();
        for (Character character : this.getPlayer().getMemory().getCharactersDeck().get()) {
            if (!character.equals(new Assassin())) {
                targets.add(character);
            }
        }
        return targets;
    }

}