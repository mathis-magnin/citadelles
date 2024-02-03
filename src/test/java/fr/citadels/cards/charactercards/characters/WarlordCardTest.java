package fr.citadels.cards.charactercards.characters;

import fr.citadels.cards.charactercards.CharacterCard;
import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarlordCardTest {

    Game game;

    KingBot player1;
    KingBot player2;
    KingBot player3;

    @BeforeEach
    void setUp() {
        game = new Game();

        player1 = new KingBot("Tom", new ArrayList<>(), game);
        player2 = new KingBot("Bob", new ArrayList<>(), game);
        player3 = new KingBot("Sam", new ArrayList<>(), game);

        player1.setCharacter(CharacterCardsList.allCharacterCards[0]);
        player2.setCharacter(CharacterCardsList.allCharacterCards[7]);
        player3.setCharacter(CharacterCardsList.allCharacterCards[1]);
    }

    @Test
    void getPossibleTargets() {
        CharacterCardsList targets = WarlordCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new AssassinCard(), new ThiefCard(), new WarlordCard()}), targets);

        player1.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[20], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[50], DistrictCardsPile.allDistrictCards[60])));

        targets = WarlordCard.getPossibleTargets();
        assertEquals(new CharacterCardsList(new CharacterCard[]{new ThiefCard(), new WarlordCard()}), targets);
    }

    @Test
    void getCharacterWithBiggestCity() {
        player1.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[5], DistrictCardsPile.allDistrictCards[15], DistrictCardsPile.allDistrictCards[25], DistrictCardsPile.allDistrictCards[35], DistrictCardsPile.allDistrictCards[45], DistrictCardsPile.allDistrictCards[55], DistrictCardsPile.allDistrictCards[65])));
        player3.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[10], DistrictCardsPile.allDistrictCards[20], DistrictCardsPile.allDistrictCards[30], DistrictCardsPile.allDistrictCards[40], DistrictCardsPile.allDistrictCards[50], DistrictCardsPile.allDistrictCards[60])));
        CharacterCard characterWithBiggestCity = WarlordCard.getOtherCharacterWithBiggestCity();
        assertNull(characterWithBiggestCity);

        player1.setCity(new City());
        player3.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[1])));
        characterWithBiggestCity = WarlordCard.getOtherCharacterWithBiggestCity();
        assertEquals(new ThiefCard(), characterWithBiggestCity);
    }

    @Test
    void usePower() {
        player1.setCity(new City(new ArrayList<>(List.of(DistrictCardsPile.allDistrictCards[0]))));
        DistrictCardsPile.allDistrictCards[0].build(true, player1);
        player2.getActions().addGold(2);
        player2.getInformation().setTarget(CharacterCardsList.allCharacterCards[0]);
        player2.getInformation().setDistrictToDestroy(DistrictCardsPile.allDistrictCards[0]);
        player2.getCharacter().usePower();
        assertFalse(DistrictCardsPile.allDistrictCards[0].isBuilt());
        assertEquals(0, player2.getGold());
        Assertions.assertEquals(new City(), player1.getCity());
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
