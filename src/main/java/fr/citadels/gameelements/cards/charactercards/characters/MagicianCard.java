package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.Hand;

public class MagicianCard extends CharacterCard {

    /* Static content */

    public static CharacterCardsList getPossibleTargets() {
        CharacterCardsList targets = new CharacterCardsList();
        for(CharacterCard characterCard : CharacterCardsList.allCharacterCards) {
            if(characterCard.isPlayed()) {
                targets.add(characterCard);
            }
        }
        return targets;
    }

    /* Constructor */

    public MagicianCard() {
        super("Magicien", CardFamily.NEUTRAL, 3);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsMagician();
    }

    public void usePower(){
        Hand hand = this.getPlayer().getInformation().getTarget().getPlayer().getHand();
        this.getPlayer().getInformation().getTarget().getPlayer().setHand(this.getPlayer().getHand());
        this.getPlayer().setHand(hand);

        this.getPlayer().getInformation().getDisplay().addMagicianPower(this.getPlayer(), this.getPlayer().getInformation().getTarget().getPlayer());
    }

}
