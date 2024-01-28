package fr.citadels.gameelements.cards.charactercards.characters;

import fr.citadels.engine.Game;
import fr.citadels.gameelements.cards.charactercards.CharacterCard;
import fr.citadels.gameelements.cards.charactercards.CharacterCardsList;
import fr.citadels.gameelements.cards.districtcards.City;
import fr.citadels.gameelements.cards.districtcards.DistrictCardsPile;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarlordCardTest {

    Game game;

    KingBot player1;
    KingBot player2;

    @BeforeEach
    void setUp() {
        game = new Game();

        player1 = new KingBot("Tom", new ArrayList<>(), game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);

        player1.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[7]);
    }

    @Test
    void getPossibleTargets() {
        CharacterCardsList targets = WarlordCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new AssassinCard()}), targets);

        player1.setCity(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[20], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[50], DistrictCardsPile.allDistrictCards[60]))));

        targets = WarlordCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{}), targets);
    }

    @Test
    void usePower() {
        player1.setCity(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0]))));

        player2.getActions().addGold(2);
        player2.getInformation().setTarget(CharacterCardsList.allCharacterCards[0]);
        player2.getInformation().setDistrictToDestroy(DistrictCardsPile.allDistrictCards[0]);
        player2.getCharacter().usePower();

        assertEquals(0, player2.getGold());
        assertEquals(new City(), player1.getCity());
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
