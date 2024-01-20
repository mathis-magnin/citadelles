package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssassinCardTest {
    Player player = new KingBot("Hello1", List.of(DistrictCardsPile.allDistrictCards[0]), new Game());
    AssassinCard assassin;

    @Test
    void usePower() {
        assassin = new AssassinCard();
        assassin.setPlayer(player);
        player.getInformation().setTarget(CharacterCardsList.allCharacterCards[3]);
        assassin.usePower();
        assertTrue(CharacterCardsList.allCharacterCards[3].isDead());
    }
}