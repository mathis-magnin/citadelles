package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MerchantCardTest {

    MerchantCard merchantCard = new MerchantCard();
    List<DistrictCard> cardsPlayer = new ArrayList<>();
    DistrictCardsPile pile = new DistrictCardsPile();
    Bank bank = new Bank();
    Display display = new Display();
    Player player = new Player("Luk", cardsPlayer, pile, bank, display) {
        @Override
        public DistrictCard chooseCardAmongDrawn(DistrictCard[] drawnCards) {
            return null;
        }

        @Override
        public DistrictCard chooseCardInHand() {
            return null;
        }

        @Override
        public void chooseCharacter(CharacterCardsList characters) { }

        @Override
        public void playResourcesPhase() {
        }

        @Override
        public void playBuildingPhase() {
        }

        @Override
        public void playAsAssassin() { }

        @Override
        public void playAsThief() { }

        @Override
        public void playAsMagician() { }

        @Override
        public void playAsKing() { }

        @Override
        public void playAsBishop() { }

        @Override
        public void playAsMerchant() {
            this.getCharacter().usePower();
        }

        @Override
        public void playAsArchitect() { }

        @Override
        public void playAsWarlord() { }

    };

    @Test
    void usePower() {
        merchantCard.setPlayer(player);
        player.setCharacter(merchantCard);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

}
