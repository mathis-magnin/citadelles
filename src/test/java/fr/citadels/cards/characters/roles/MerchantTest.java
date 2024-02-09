package fr.citadels.cards.characters.roles;

import fr.citadels.cards.districts.District;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MerchantTest {

    Merchant merchantCard = new Merchant();
    List<District> cardsPlayer = new ArrayList<>();
    Game game;
    Monarchist player;

    @BeforeEach
    void setup() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        player = new Monarchist("Hello1", cardsPlayer, game);
        players[0] = player;
        game.getPile().initializePile();
    }

    @Test
    void usePower() {
        merchantCard.setPlayer(player);
        player.setCharacter(merchantCard);
        player.playAsMerchant();
        assertEquals(1, player.getGold());
    }

}
