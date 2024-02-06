package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;

public class Assassin extends Character {

    /* Static content */

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

    public void usePower() {
        getPlayer().getInformation().getTarget().setDead(true);
        getPlayer().getInformation().getDisplay().addAssassinPower(getPlayer().getInformation().getTarget());
        getPlayer().getInformation().getDisplay().addBlankLine();
    }

}