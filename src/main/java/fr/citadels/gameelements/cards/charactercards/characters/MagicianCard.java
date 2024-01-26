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
        switch (this.getPlayer().getInformation().getPowerToUse()) {
            case 1 :
                Hand hand = this.getPlayer().getInformation().getTarget().getPlayer().getHand();
                this.getPlayer().getInformation().getTarget().getPlayer().setHand(this.getPlayer().getHand());
                this.getPlayer().setHand(hand);
                this.getPlayer().getInformation().getDisplay().addMagicianPower(this.getPlayer(), this.getPlayer().getInformation().getTarget().getPlayer());
                break;
            case 2:
                int iterations = Math.min(this.getPlayer().getInformation().getCardsToDiscard(), this.getPlayer().getHand().size());
                for (int i = 0; i < iterations; i++) {
                    this.getPlayer().getInformation().getPile().placeBelowPile(this.getPlayer().getHand().remove(this.getPlayer().getHand().size()-1));
                }
                this.getPlayer().getActions().draw(iterations);
                break;
        }
    }

}
