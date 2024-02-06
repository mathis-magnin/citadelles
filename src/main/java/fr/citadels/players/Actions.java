package fr.citadels.players;

import fr.citadels.cards.Family;
import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.cards.districtcards.Hand;
import fr.citadels.cards.districtcards.uniques.SchoolOfMagic;

import java.util.List;

public class Actions {

    /* Attributes */

    private final Player player;
    private final Memory memory;

    /* Constructor */

    public Actions(Player player, Memory memory) {
        this.player = player;
        this.memory = memory;

    }


    /* Methods */


    /**
     * add gold to the player from the bank
     *
     * @param amount the amount of gold earned by the player
     * @precondition amount should be positive
     */
    public void addGold(int amount) {
        player.setGold(player.getGold() + amount);
    }


    /**
     * remove gold from the player to the bank
     *
     * @param amount the amount of gold paid by the player
     * @precondition amount should be positive
     */
    public void removeGold(int amount) {
        if (amount >= player.getGold()) {
            player.setGold(0);
        } else {
            player.setGold(player.getGold() - amount);
        }
    }


    /**
     * Sort the player's hand
     *
     * @param family the family of the cards to put first
     */
    public void sortHand(Family family) {
        Hand hand = player.getHand();
        hand.sortCards(family);
        player.setHand(hand);
    }


    public int putRedundantCardsAtTheEnd() {
        int redundantCards = 0;
        Hand hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            if (player.getCity().contains(hand.get(i - redundantCards))) {
                hand.add(hand.remove(i - redundantCards));
                redundantCards++;
            }
        }
        return redundantCards;
    }


    /**
     * put back the cards drawn except the one played
     *
     * @param drawnCards  cards drawn
     * @param randomIndex index of the card played
     */
    public void putBack(District[] drawnCards, int randomIndex) {
        for (int i = 0; i < drawnCards.length; i++) {
            if (i != randomIndex) {
                player.getMemory().getPile().placeBelowPile(drawnCards[i]);
                drawnCards[i] = null;
            }
        }
    }


    /**
     * Takes 2 cards or 2 golds from the bank and add them to the player
     */
    public void takeCardsOrGold() {
        if (this.memory.getDraw()) {
            int nbToDraw = 2;
            if (player.equals(DistrictsPile.allDistrictCards[59].getOwner())) { // Observatory effect
                player.getMemory().getDisplay().addObservatoryEffect();
                nbToDraw++;
            }
            District[] drawnCards = player.getMemory().getPile().draw(nbToDraw);

            player.getMemory().getDisplay().addDistrictDrawn(drawnCards);
            if (drawnCards.length != 0) { // if there is at least 1 card
                Hand hand = player.getHand();
                if (player.equals(DistrictsPile.allDistrictCards[64].getOwner())) { // Library effect
                    this.player.getMemory().getDisplay().addLibraryEffect();
                    hand.addAll(List.of(drawnCards));
                } else {
                    District cardToPlay = player.chooseCardAmongDrawn(drawnCards);
                    hand.add(cardToPlay);
                    player.getMemory().getDisplay().addDistrictChosen(player, cardToPlay);
                }

                player.getMemory().getDisplay().addBlankLine();
            } else {
                this.memory.setDraw(false);
            }
        }
        if (!this.memory.getDraw()) {
            addGold(2);

            player.getMemory().getDisplay().addGoldTaken(player, 2);
            player.getMemory().getDisplay().addBlankLine();
        }
    }


    /**
     * Take gold from the city if the family of the card is the same as the family of the character.
     * The SchoolOfMagic district count as the same family of the character.
     */
    public void takeGoldFromCity() {
        if (player.getCharacter() != null) {
            int goldToTake = 0;
            boolean activateSchoolOfMagicEffect = false;
            for (District card : player.getCity()) {
                if (card.getFamily().equals(player.getCharacter().getFamily())) {
                    goldToTake++;
                } else if (card.equals(new SchoolOfMagic())) {
                    goldToTake++;
                    activateSchoolOfMagicEffect = true;
                }
            }
            if (goldToTake > 0) {
                addGold(goldToTake);
                player.getMemory().getDisplay().addGoldTakenFromCity(player, goldToTake, activateSchoolOfMagicEffect);
                player.getMemory().getDisplay().addBlankLine();
            }
        }
    }


    /**
     * Build the district present in the attribute PlayerInformation's attribute districtToBuild.
     */
    public void build() {
        if (this.memory.getDistrictToBuild() != null) {
            this.addCardToCity(this.memory.getDistrictToBuild());
            this.player.getHand().remove(this.memory.getDistrictToBuild());
            this.removeGold(this.memory.getDistrictToBuild().getGoldCost());
            this.player.getMemory().getDisplay().addDistrictBuilt(this.player, this.memory.getDistrictToBuild());
        } else {
            this.player.getMemory().getDisplay().addNoDistrictBuilt();
        }
        this.player.getMemory().getDisplay().addBlankLine();
    }


    /**
     * Give all the player's gold to the thief
     */
    public void getRobbed() {
        int goldToTake = this.player.getGold();
        CharactersList.allCharacterCards[1].getPlayer().getActions().addGold(goldToTake);
        this.player.setGold(0);
        this.player.getCharacter().setRobbed(false);
        this.player.getMemory().getDisplay().addRobbed(CharactersList.allCharacterCards[1].getPlayer(), goldToTake);
    }


    /**
     * Remove a card from the player's hand
     *
     * @param index the index of the card to remove
     * @return the card removed
     */
    public District removeCardFromHand(int index) {
        return player.getHand().removeCard(index);
    }


    /**
     * Add a card to the player's city
     */
    public void addCardToCity(District card) {
        player.getCity().add(card);
        card.setOwner(this.player);
    }


    /**
     * Remove a card from player's city
     *
     * @param card the card to remove
     */
    public void removeCardFromCity(District card) {
        this.player.getCity().remove(card);
        card.setOwner(null);
    }


    /**
     * Add cards to the player's city
     */
    public void addCardsToCity(List<District> cards) {
        for (District card : cards) {
            this.addCardToCity(card);
        }
    }


    /**
     * The player draw a certain number of card and place them in his hand.
     *
     * @param number of card to draw.
     */
    public void draw(int number) {
        District[] drawnCards = this.memory.getPile().draw(number);
        this.memory.getDisplay().addDistrictDrawn(drawnCards);
        this.player.getHand().addAll(List.of(drawnCards));
        this.memory.getDisplay().addHandUpdate(this.player.getHand());
    }

}