package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.DistrictCard;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThiefCardTest {

    CharacterCard thief = new ThiefCard();
    CharacterCard king = new KingCard();

    Game game;
    List<DistrictCard> hand1 = Arrays.asList(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[1], DistrictCardsPile.allDistrictCards[2]);
    List<DistrictCard> hand2 = Arrays.asList(DistrictCardsPile.allDistrictCards[3], DistrictCardsPile.allDistrictCards[4], DistrictCardsPile.allDistrictCards[5]);

    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();

        player1 = new KingBot("Tom", hand1, game);
        player2 = new KingBot("Bob", hand2, game);

        player1.setCharacter(thief);
        player2.setCharacter(king);

        player1.getInformation().setTarget(king);

        player1.setGold(10);
        player2.setGold(10);
    }

    @Test
    void thiefTest() {
        assertEquals("Voleur", thief.getCardName());
        assertEquals(2, thief.getRank());
        assertEquals("Neutre", thief.getCardFamily().toString());
        assertFalse(thief.isRobbed());
        assertEquals(player1, thief.getPlayer());
        assertEquals(player1.getInformation().getTarget(), king);
    }

    @Test
    void bringIntoPlay() {
        assertEquals(0, player1.getCity().size());
        thief.bringIntoPlay();
        assertEquals(1, player1.getCity().size());
    }

    @Test
    void usePower() {
        thief.usePower();
        assertTrue(player2.getCharacter().isRobbed());
    }

    @Test
    void getPossibleTargets() {
        CharacterCardsList targets = ThiefCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new MagicianCard(), new KingCard(), new BishopCard(), new MerchantCard(), new ArchitectCard(), new WarlordCard()}), targets);
        CharacterCardsList.allCharacterCards[3].setDead(true);
        targets = ThiefCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new MagicianCard(), new BishopCard(), new MerchantCard(), new ArchitectCard(), new WarlordCard()}), targets);
    }

    @AfterAll
    static void resetCharacterCards() {
        CharacterCardsList.allCharacterCards[0] = new AssassinCard();
        CharacterCardsList.allCharacterCards[1] = new ThiefCard();
        CharacterCardsList.allCharacterCards[2] = new MagicianCard();
        CharacterCardsList.allCharacterCards[3] = new KingCard();
        CharacterCardsList.allCharacterCards[4] = new BishopCard();
        CharacterCardsList.allCharacterCards[5] = new MerchantCard();
        CharacterCardsList.allCharacterCards[6] = new ArchitectCard();
        CharacterCardsList.allCharacterCards[7] = new WarlordCard();
    }

}