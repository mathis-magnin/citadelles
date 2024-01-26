package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.Hand;

import java.util.ArrayList;
import java.util.List;

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
                this.getPlayer().getInformation().getDisplay().addMagicianSwap(this.getPlayer(), this.getPlayer().getInformation().getTarget().getPlayer());
                break;
            case 2:
                DistrictCard card;
                List<DistrictCard> discardsCards = new ArrayList<>();
                int iterations = Math.min(this.getPlayer().getInformation().getCardsToDiscard(), this.getPlayer().getHand().size());
                for (int i = 0; i < iterations; i++) {
                    card = this.getPlayer().getHand().remove(this.getPlayer().getHand().size()-1);
                    this.getPlayer().getInformation().getPile().placeBelowPile(card);
                    discardsCards.add(card);
                }
                this.getPlayer().getInformation().getDisplay().addMagicianDiscard(this.getPlayer(), discardsCards);
                this.getPlayer().getActions().draw(iterations);
                break;
        }
    }

}
