package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.CardFamily;
import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;

public class AssassinCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for(CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if(!characterCard.equals(new AssassinCard())) {
                targets.add(characterCard);
            }
        }
        return targets;
    }

    /* Constructor */

    public AssassinCard() {
        super("Assassin", CardFamily.NEUTRAL, 1);
    }

    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsAssassin();
    }

    public void usePower() {
        getPlayer().getInformation().getTarget().setDead(true);
        getPlayer().getInformation().getDisplay().addAssassinPower(getPlayer().getInformation().getTarget());
    }

}
