package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;

public class ThiefCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for(CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if(!characterCard.equals(new AssassinCard()) && !characterCard.equals(new ThiefCard()) && !characterCard.isDead()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }

    /* Constructor */

    public ThiefCard() {
        super("Voleur", CardFamily.NEUTRAL, 2);
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
    public void usePower(){
        this.getPlayer().getInformation().getTarget().setRobbed(true);
        this.getPlayer().getInformation().getDisplay().addThiefPower(this.getPlayer().getInformation().getTarget());
        this.getPlayer().getInformation().getDisplay().addBlankLine();
    }

}
