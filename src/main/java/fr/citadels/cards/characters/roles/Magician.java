package fr.citadels.cards.characters.roles;

import fr.citadels.cards.Family;
import fr.citadels.cards.characters.Character;
import fr.citadels.cards.characters.CharactersList;
import fr.citadels.cards.districts.District;
import fr.citadels.cards.districts.Hand;

import java.util.ArrayList;
import java.util.List;

public class Magician extends Character {

    /* Static content */

    /**
     * The Magician must not target himself or unplayed character.
     *
     * @return The list of characters the Magician can target.
     */
    public static CharactersList getPossibleTargets() {
        CharactersList targets = new CharactersList();
        for (Character characterCard : CharactersList.allCharacterCards) {
            if (characterCard.isPlayed() && !characterCard.equals(new Magician())) {
                targets.add(characterCard);
            }
        }
        return targets;
    }


    /**
     * Gives the character who has the biggest amount of cards in his hand except the magician or an unplayed character.
     *
     * @return the character with the biggest city.
     */
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


    /**
     * Let the player who embodies the character use the power which comes from his role.
     * The abilities of the Warlord are :
     * SWAP : The player swaps his hand with another player.
     * RECYCLE : The player discard a certain amount of cards from his hand and draw the same amount.
     *
     * @precondition The player must have chosen which power he wants to use.
     * @precondition About the SWAP power, the player must have chosen which player he wants to target.
     */
    public void usePower() {
        switch (this.getPlayer().getMemory().getPowerToUse()) {
            case SWAP:
                this.swap();
                break;
            case RECYCLE:
                this.recycle();
                break;
            default:
                break;
        }
        this.getPlayer().getMemory().getDisplay().addBlankLine();
    }


    /**
     * The player swaps his hand with another player.
     */
    private void swap() {
        Hand hand = this.getPlayer().getMemory().getTarget().getPlayer().getHand();
        this.getPlayer().getMemory().getTarget().getPlayer().setHand(this.getPlayer().getHand());
        this.getPlayer().setHand(hand);
        this.getPlayer().getMemory().getDisplay().addMagicianSwap(this.getPlayer(), this.getPlayer().getMemory().getTarget().getPlayer());
    }


    /**
     * The player discard a certain amount of cards from his hand and draw the same amount.
     *
     * @precondition The player must have chosen which player he wants to target.
     */
    private void recycle() {
        if (this.getPlayer().getMemory().getNumberCardsToDiscard() != 0) {
            District card;
            List<District> discardsCards = new ArrayList<>();
            int iterations = Math.min(this.getPlayer().getMemory().getNumberCardsToDiscard(), this.getPlayer().getHand().size());
            for (int i = 0; i < iterations; i++) {
                card = this.getPlayer().getHand().remove(this.getPlayer().getHand().size() - 1);
                this.getPlayer().getMemory().getPile().placeBelowPile(card);
                discardsCards.add(card);
            }
            this.getPlayer().getMemory().getDisplay().addMagicianDiscard(this.getPlayer(), discardsCards);
            this.getPlayer().getActions().draw(iterations);
        }
    }

}