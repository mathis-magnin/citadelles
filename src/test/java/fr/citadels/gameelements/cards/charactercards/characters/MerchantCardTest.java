package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.gameelements.Bank;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MerchantCardTest {

    MerchantCard merchantCard = new MerchantCard();
    List<DistrictCard> cardsPlayer = new ArrayList<>();

    Player player = new KingBot("Hello1", cardsPlayer, new Game());

    @Test
    void usePower() {
        merchantCard.setPlayer(player);
        player.setCharacter(merchantCard);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

}
