package fr.citadels.players;

import fr.citadels.gameelements.cards.CardFamily;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.Hand;

import java.util.List;

public class PlayerActions {
    /* Attributes */
    private final Player player;
    private final PlayerInformation information;


    /* Constructor */
    public PlayerActions(Player player, PlayerInformation information) {
        this.player = player;
        this.information = information;

    }

    /* Methods */

    /**
     * add gold to the player from the bank
     *
     * @param amount
     */
    public void addGold(int amount) {
        player.getInformation().getBank().take(amount);
        player.setGold(player.getGold() + amount);
    }

    /**
     * remove gold from the player to the bank
     *
     * @param amount
     */
    public void removeGold(int amount) {
        if (amount >= player.getGold()) {
            player.getInformation().getBank().give(player.getGold());
            player.setGold(0);
        } else {
            player.getInformation().getBank().give(amount);
            player.setGold(player.getGold() - amount);
        }
    }

    /**
     * Sort the player's hand
     *
     * @param family the family of the cards to put first
     */
    public void sortHand(CardFamily family) {
        Hand hand = player.getHand();
        hand.sortCards(family);
        player.setHand(hand);
    }


    /**
     * put back the cards drawn except the one played
     *
     * @param drawnCards  cards drawn
     * @param randomIndex index of the card played
     */
    public void putBack(DistrictCard[] drawnCards, int randomIndex) {
        for (int i = 0; i < drawnCards.length; i++) {
            if (i != randomIndex) {
                player.getInformation().getPile().placeBelowPile(drawnCards[i]);
                drawnCards[i] = null;
            }
        }
    }

    /**
     * takes 2 cards or 2 golds from the bank and add them to the player
     *
     * @param draw true if the player has to draw cards
     */
    public void takeCardsOrGold(boolean draw) {
        if (!draw) {
            if (!player.getInformation().getBank().isEmpty())
                addGold(2);
            else draw = true;

            player.getInformation().getDisplay().addGoldTaken(player, 2);
            player.getInformation().getDisplay().addBlankLine();
        }
        if (draw) {
            DistrictCard[] drawnCards = player.getInformation().getPile().draw(2);
            player.getInformation().getDisplay().addDistrictDrawn(drawnCards);
            if (drawnCards.length != 0) { // if there is at least 1 card
                DistrictCard cardToPlay = player.chooseCardAmongDrawn(drawnCards);

                Hand hand = player.getHand();
                hand.add(cardToPlay);
                player.setHand(hand);

                player.getInformation().getDisplay().addDistrictChosen(player, cardToPlay);
                player.getInformation().getDisplay().addBlankLine();
            }
        }
    }


    /**
     * take gold from the city if the family of the card is the same as the family of the character
     */

    public void takeGoldFromCity() {
        if (player.getCharacter() != null) {
            int goldToTake = 0;
            for (DistrictCard card : player.getCity()) {
                if (card.getCardFamily().equals(player.getCharacter().getCardFamily())) {
                    goldToTake++;
                }
            }
            if (goldToTake > 0) {
                addGold(goldToTake);
                player.getInformation().getBank().take(goldToTake);
                player.getInformation().getDisplay().addGoldTakenFromCity(player, goldToTake);
                player.getInformation().getDisplay().addBlankLine();
            }
        }
    }

    /**
     * place the card as parameter in the city
     *
     * @param cardToPlace
     */
    public void placeCard(DistrictCard cardToPlace) {
        if (cardToPlace != null) {
            addCardToCity(cardToPlace);
            player.getInformation().getBank().give(cardToPlace.getGoldCost());
            removeGold(cardToPlace.getGoldCost());
            player.getInformation().getDisplay().addDistrictBuilt(player, cardToPlace);
        } else {
            player.getInformation().getDisplay().addNoDistrictBuilt();
        }
    }


    /**
     * Remove a card from the player's hand
     *
     * @param index the index of the card to remove
     * @return the card removed
     */
    public DistrictCard removeCardFromHand(int index) {
        return player.getHand().removeCard(index);
    }


    /**
     * Add a card to the player's city
     */
    public void addCardToCity(DistrictCard card) {
        player.getCity().add(card);
    }

    /**
     * Add cards to the player's city
     */
    public void addCardsToCity(List<DistrictCard> cards) {
        player.getCity().addAll(cards);
    }
}