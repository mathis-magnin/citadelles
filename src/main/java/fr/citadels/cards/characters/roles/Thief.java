package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;

public class Thief extends Character {

    /* Static content */

    /**
     * The Thief must not target himself nor the assassin nor dead.
     *
     * @return The list of characters the Thief can target.
     */
    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (!characterCard.equals(new Assassin()) && !characterCard.equals(new Thief()) && !characterCard.isDead()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    /* Constructor */

    public Thief() {
        super("Voleur", Family.NEUTRAL, 2);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsThief();
    }


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * ROB : The thief robs all the gold of the character he wants to.
     *
     * @precondition The player must have chosen which character he wants to target.
     */
    @Override
    public void usePower() {
        this.getPlayer().getMemory().getTarget().setRobbed(true);
        this.getPlayer().getMemory().getDisplay().addThiefPower(this.getPlayer().getMemory().getTarget());
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }

}
