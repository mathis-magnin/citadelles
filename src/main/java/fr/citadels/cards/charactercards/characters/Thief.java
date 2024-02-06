package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;

public class Thief extends Character {

    /* Static content */

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
     * The thief steals all the gold of the character he chooses (the target).
     * The target can be neither the thief nor the assassin nor dead.
     */
    @Override
    public void usePower() {
        this.getPlayer().getMemory().getTarget().setRobbed(true);
        this.getPlayer().getMemory().getDisplay().addThiefPower(this.getPlayer().getMemory().getTarget());
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }

}
