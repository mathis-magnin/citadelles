package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.Character;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Magician extends Character {

    /* Static content */

    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (characterCard.isPlayed() && !characterCard.equals(new Magician())) {
                targets.add(characterCard);
            }
        }
        return targets;
    }

    public static Character getCharacterWithMostCards() {
        Character characterWithMostCards = null;
        for (Character character : getPossibleTargets()) {
            if ((characterWithMostCards == null) || (character.getPlayer().getHand().size() > characterWithMostCards.getPlayer().getHand().size())) {
                characterWithMostCards = character;
            }
        }
        return characterWithMostCards;
    }

    /* Constructor */

    public Magician() {
        super("Magicien", Family.NEUTRAL, 3);
    }


    /* Methods */

    @Override
    public void bringIntoPlay() {
        this.getPlayer().playAsMagician();
    }

    public void usePower() {
        switch (this.getPlayer().getInformation().getPowerToUse()) {
            case 1:
                Hand hand = this.getPlayer().getInformation().getTarget().getPlayer().getHand();
                this.getPlayer().getInformation().getTarget().getPlayer().setHand(this.getPlayer().getHand());
                this.getPlayer().setHand(hand);
                this.getPlayer().getInformation().getDisplay().addMagicianSwap(this.getPlayer(), this.getPlayer().getInformation().getTarget().getPlayer());
                break;
            case 2:
                if (this.getPlayer().getInformation().getCardsToDiscard() != 0) {
                    District card;
                    List<District> discardsCards = new ArrayList<>();
                    int iterations = Math.min(this.getPlayer().getInformation().getCardsToDiscard(), this.getPlayer().getHand().size());
                    for (int i = 0; i < iterations; i++) {
                        card = this.getPlayer().getHand().remove(this.getPlayer().getHand().size() - 1);
                        this.getPlayer().getInformation().getPile().placeBelowPile(card);
                        discardsCards.add(card);
                    }
                    this.getPlayer().getInformation().getDisplay().addMagicianDiscard(this.getPlayer(), discardsCards);
                    this.getPlayer().getActions().draw(iterations);
                    break;
                }
        }
        this.getPlayer().getInformation().getDisplay().addBlankLine();
    }

}
