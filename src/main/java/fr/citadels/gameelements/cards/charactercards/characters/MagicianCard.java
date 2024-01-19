package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.gameelements.cards.districtcards.Hand;
import fr.citadels.players.Player;

import java.util.Arrays;
import java.util.List;

public class MagicianCard extends CharacterCard {

    /* Constructor */

    public MagicianCard() {
        super("Magicien", CardFamily.NEUTRAL, 3);
    }


    public void exchangeHands(Player player, Player target) {
        Hand handPlayer = player.getHand();
        Hand handOther = player.getHand();

        player.setHand(handOther);
        target.setHand(handPlayer);
    }


    public void discardAndDrawHand(Player player, DistrictCardsPile pile, List<DistrictCard> districtCardsToDiscard) {
        // districyCardsToDiscard should be a part of player's hand.
        Hand playerHand = player.getHand();
        playerHand.removeAll(districtCardsToDiscard);
        playerHand.addAll(Arrays.asList(pile.draw(districtCardsToDiscard.size())));
        player.setHand(playerHand);
    }

}
