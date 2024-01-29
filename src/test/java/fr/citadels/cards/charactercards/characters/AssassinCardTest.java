package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssassinCardTest {
    Player player = new KingBot("Hello1", List.of(DistrictCardsPile.allDistrictCards[0]), new Game());
    AssassinCard assassin;
    KingCard king;

    @Test
    void usePower() {
        assassin = new AssassinCard();
        king = new KingCard();
        assassin.setPlayer(player);
        player.getInformation().setTarget(king);
        assassin.usePower();
        Assertions.assertTrue(king.isDead());
    }

    @Test
    void getPossibleTargets() {
        CharacterCardsList targets = AssassinCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new ThiefCard(), new MagicianCard(), new KingCard(), new BishopCard(), new MerchantCard(), new ArchitectCard(), new WarlordCard()}), targets);
    }
}