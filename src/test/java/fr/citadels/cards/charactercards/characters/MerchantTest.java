package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.districtcards.District;
import fr.citadels.engine.Game;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MerchantTest {

    Merchant merchantCard = new Merchant();
    List<District> cardsPlayer = new ArrayList<>();
    Game game;
    Monarchist player;

    @BeforeEach
    void setup(){
        game = new Game();
        game.getPile().initializePile();
        player = new Monarchist("Hello1", cardsPlayer, game);
    }

    @Test
    void usePower() {
        merchantCard.setPlayer(player);
        player.setCharacter(merchantCard);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

}
