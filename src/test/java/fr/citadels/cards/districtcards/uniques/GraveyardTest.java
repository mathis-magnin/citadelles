package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.districtcards.City;
import fr.citadels.cards.districtcards.DistrictCard;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.engine.Game;
import fr.citadels.players.Player;
import fr.citadels.players.bots.KingBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraveyardTest {

    Game game;
    Graveyard graveyard;
    Player warlordPlayer;
    Player targetedPlayer;
    Player graveyardPlayer;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.getPile().initializePile();

        graveyard = (Graveyard) (DistrictCardsPile.allDistrictCards[62]);

        warlordPlayer = new KingBot("WARLORD", new ArrayList<>(), game);
        warlordPlayer.setCharacter(CharacterCardsList.allCharacterCards[7]); // Warlord
        warlordPlayer.getInformation().setTarget(CharacterCardsList.allCharacterCards[2]); // Magician
        warlordPlayer.getActions().addGold(2); // 3 - 1 (cost of the district to destroy - 1)
        warlordPlayer.getInformation().setDistrictToDestroy(DistrictCardsPile.allDistrictCards[0]);

        targetedPlayer = new KingBot("TARGET", new ArrayList<>(), game);
        targetedPlayer.setCharacter(CharacterCardsList.allCharacterCards[2]); // Magician
        targetedPlayer.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15])));

        graveyardPlayer = new KingBot("GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean activateGraveyardEffect(DistrictCard districtRemoved) {
                return true;
            }
        };
        graveyardPlayer.setCharacter(CharacterCardsList.allCharacterCards[5]); // Merchant
        graveyardPlayer.getActions().addGold(1); // 1 to use the effect of the graveyard
        graveyardPlayer.setCity(new City(List.of(graveyard)));
        graveyard.setOwner(graveyardPlayer);
    }


    @Test
    void useEffectTest() {
        /* The graveyardPlayer wants to use graveyard's effect */
        warlordPlayer.getCharacter().usePower();
        assertEquals(0, warlordPlayer.getGold());

        assertFalse(targetedPlayer.getCity().contains(DistrictCardsPile.allDistrictCards[0]));

        assertEquals(0, graveyardPlayer.getGold());
        assertTrue(graveyardPlayer.getHand().contains(DistrictCardsPile.allDistrictCards[0]));
        assertNotEquals(graveyard, game.getPile().getLast());


        /* TARGET and GRAVEYARD are the same player and the district destroyed is graveyard */

        setUp();
        Player targetedGraveyardPlayer = new KingBot("TARGETED_GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean activateGraveyardEffect(DistrictCard removedDistrict) {
                return true;
            }
        };
        targetedGraveyardPlayer.setCharacter(CharacterCardsList.allCharacterCards[2]); // Magician
        targetedGraveyardPlayer.getActions().addGold(1); // 1 to use the effect of the graveyard
        targetedGraveyardPlayer.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], graveyard)));
        graveyard.setOwner(targetedGraveyardPlayer);

        warlordPlayer.getInformation().setDistrictToDestroy(graveyard);
        warlordPlayer.getCharacter().usePower();

        assertEquals(0, warlordPlayer.getGold());

        assertFalse(targetedGraveyardPlayer.getCity().contains(graveyard));

        assertEquals(1, targetedGraveyardPlayer.getGold());
        assertFalse(targetedGraveyardPlayer.getHand().contains(graveyard));
        assertEquals(graveyard, game.getPile().getLast());


        /* WARLORD, TARGET and GRAVEYARD are the same player */

        setUp();
        Player warlordTargetedGraveyardPlayer = new KingBot("WARLORD_TARGETED_GRAVEYARD", new ArrayList<>(), game) {
            @Override
            public boolean activateGraveyardEffect(DistrictCard removedDistrict) {
                return true;
            }
        };
        warlordTargetedGraveyardPlayer.setCharacter(CharacterCardsList.allCharacterCards[7]); // Warlord
        warlordTargetedGraveyardPlayer.getInformation().setTarget(CharacterCardsList.allCharacterCards[7]); // Warlord
        warlordTargetedGraveyardPlayer.getActions().addGold(3); // 3 - 1 + 1 : (cost of the district to destroy - 1) + (cost the effect)
        warlordTargetedGraveyardPlayer.getInformation().setDistrictToDestroy(DistrictCardsPile.allDistrictCards[0]);
        warlordTargetedGraveyardPlayer.setCity(new City(List.of(DistrictCardsPile.allDistrictCards[0], DistrictCardsPile.allDistrictCards[15], graveyard)));
        graveyard.setOwner(warlordTargetedGraveyardPlayer);

        warlordTargetedGraveyardPlayer.getCharacter().usePower();

        assertFalse(warlordTargetedGraveyardPlayer.getCity().contains(DistrictCardsPile.allDistrictCards[0]));
        assertEquals(1, graveyardPlayer.getGold());
        assertFalse(graveyardPlayer.getHand().contains(DistrictCardsPile.allDistrictCards[0]));
        assertNotEquals(graveyard, game.getPile().getLast());
    }
}