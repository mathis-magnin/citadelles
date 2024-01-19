package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Display;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssassinCardTest {

    AssassinCard assassin;

    @Test
    void usePower() {
        assassin = spy(new AssassinCard());

        Player player = mock(Player.class);
        assassin.setPlayer(player);
        CharacterCard kingCard = new KingCard();
        when(player.getTarget()).thenReturn(kingCard);
        when(player.getDisplay()).thenReturn(mock(Display.class));
        assassin.usePower();
        assertTrue(kingCard.isDead());
    }
}