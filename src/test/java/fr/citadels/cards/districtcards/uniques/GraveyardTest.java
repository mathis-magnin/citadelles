package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.CharactersList;
import fr.citadels.cards.charactercards.Power;
import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.District;
import fr.citadels.cards.districtcards.DistrictsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.Monarchist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GraveyardTest {

    Game game;
    Graveyard graveyard;
    Player warlordPlayer;
    Player targetedPlayer;
    Player graveyardPlayer;

    @BeforeEach
    void setUp() {
        Player[] players = new Player[4];
        game = new Game(players, new Random());
        game.getPile().initializePile();

        graveyard = (Graveyard) (DistrictsPile.allDistrictCards[62]);

        warlordPlayer = new Monarchist("WARLORD", new ArrayList<>(), game);
        warlordPlayer.setCharacter(CharactersList.allCharacterCards[7]); // Warlord
        warlordPlayer.getMemory().setTarget(CharactersList.allCharacterCards[2]); // Magician
        warlordPlayer.getActions().addGold(2); // 3 - 1 (cost of the district to destroy - 1)
        warlordPlayer.getMemory().setDistrictToDestroy(DistrictsPile.allDistrictCards[0]);

        targetedPlayer = new Monarchist("TARGET", new ArrayList<>(), game);
        targetedPlayer.setCharacter(CharactersList.allCharacterCards[2]); // Magician
        targetedPlayer.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15])));

        graveyardPlayer = new Monarchist("GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean chooseGraveyardEffect(District districtRemoved) {
                return true;
            }
        };
        graveyardPlayer.setCharacter(CharactersList.allCharacterCards[5]); // Merchant
        graveyardPlayer.getActions().addGold(1); // 1 to use the effect of the graveyard
        graveyardPlayer.setCity(new City(List.of(graveyard)));
        graveyard.setOwner(graveyardPlayer);

        players[0] = warlordPlayer;
        players[1] = targetedPlayer;
        players[2] = graveyardPlayer;
    }

    @AfterEach
    void resetCharacterCards() {
        CharactersList.allCharacterCards[0] = new Assassin();
        CharactersList.allCharacterCards[1] = new Thief();
        CharactersList.allCharacterCards[2] = new Magician();
        CharactersList.allCharacterCards[3] = new King();
        CharactersList.allCharacterCards[4] = new Bishop();
        CharactersList.allCharacterCards[5] = new Merchant();
        CharactersList.allCharacterCards[6] = new Architect();
        CharactersList.allCharacterCards[7] = new Warlord();
    }


    @Test
    void useEffectTest() {
        /* The graveyardPlayer wants to use graveyard's effect */
        warlordPlayer.getMemory().setPowerToUse(Power.DESTROY);
        warlordPlayer.getCharacter().usePower();
        assertEquals(0, warlordPlayer.getGold());

        assertFalse(targetedPlayer.getCity().contains(DistrictsPile.allDistrictCards[0]));

        assertEquals(0, graveyardPlayer.getGold());
        assertTrue(graveyardPlayer.getHand().contains(DistrictsPile.allDistrictCards[0]));
        assertNotEquals(graveyard, game.getPile().getLast());


        /* TARGET and GRAVEYARD are the same player and the district destroyed is graveyard */

        setUp();
        Player targetedGraveyardPlayer = new Monarchist("TARGETED_GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean chooseGraveyardEffect(District removedDistrict) {
                return true;
            }
        };
        targetedGraveyardPlayer.setCharacter(CharactersList.allCharacterCards[2]); // Magician
        targetedGraveyardPlayer.getActions().addGold(1); // 1 to use the effect of the graveyard
        targetedGraveyardPlayer.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], graveyard)));
        graveyard.setOwner(targetedGraveyardPlayer);

        warlordPlayer.getMemory().setDistrictToDestroy(graveyard);
        warlordPlayer.getMemory().setPowerToUse(Power.DESTROY);
        warlordPlayer.getCharacter().usePower();

        assertEquals(0, warlordPlayer.getGold());

        assertFalse(targetedGraveyardPlayer.getCity().contains(graveyard));

        assertEquals(1, targetedGraveyardPlayer.getGold());
        assertFalse(targetedGraveyardPlayer.getHand().contains(graveyard));
        assertEquals(graveyard, game.getPile().getLast());


        /* WARLORD, TARGET and GRAVEYARD are the same player */

        setUp();
        Player warlordTargetedGraveyardPlayer = new Monarchist("WARLORD_TARGETED_GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean chooseGraveyardEffect(District removedDistrict) {
                return true;
            }
        };
        warlordTargetedGraveyardPlayer.setCharacter(CharactersList.allCharacterCards[7]); // Warlord
        warlordTargetedGraveyardPlayer.getMemory().setTarget(CharactersList.allCharacterCards[7]); // Warlord
        warlordTargetedGraveyardPlayer.getActions().addGold(3); // 3 - 1 + 1 : (cost of the district to destroy - 1) + (cost the effect)
        warlordTargetedGraveyardPlayer.getMemory().setDistrictToDestroy(DistrictsPile.allDistrictCards[0]);
        warlordTargetedGraveyardPlayer.setCity(new City(List.of(DistrictsPile.allDistrictCards[0], DistrictsPile.allDistrictCards[15], graveyard)));
        graveyard.setOwner(warlordTargetedGraveyardPlayer);

        warlordTargetedGraveyardPlayer.getMemory().setPowerToUse(Power.DESTROY);
        warlordTargetedGraveyardPlayer.getCharacter().usePower();

        assertFalse(warlordTargetedGraveyardPlayer.getCity().contains(DistrictsPile.allDistrictCards[0]));
        assertEquals(1, graveyardPlayer.getGold());
        assertFalse(graveyardPlayer.getHand().contains(DistrictsPile.allDistrictCards[0]));
        assertNotEquals(graveyard, game.getPile().getLast());
    }
}