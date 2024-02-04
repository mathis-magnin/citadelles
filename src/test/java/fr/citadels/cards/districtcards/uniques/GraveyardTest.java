package fr.citadels.cards.districtcards.uniques;

import fr.citadels.cards.charactercards.CharacterCardsList;
import fr.citadels.cards.charactercards.characters.ArchitectCard;
import fr.citadels.cards.charactercards.characters.MerchantCard;
import fr.citadels.cards.charactercards.characters.WarlordCard;
import fr.citadels.cards.districtcards.City;
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

    Graveyard graveyard;
    Player warlordPlayer;
    Player targetedPlayer;
    Player graveyardPlayer;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.getPile().initializePile();

        graveyard = new Graveyard();

        warlordPlayer = new KingBot("WARLORD", new ArrayList<>(), game);
        warlordPlayer.setCharacter(CharacterCardsList.allCharacterCards[7]); // Warlord
        warlordPlayer.getInformation().setTarget(CharacterCardsList.allCharacterCards[2]); // Magician
        warlordPlayer.getInformation().setDistrictToDestroy(DistrictCardsPile.allDistrictCards[0]);

        targetedPlayer = new KingBot("TARGET", new ArrayList<>(), game);
        targetedPlayer.setCharacter(CharacterCardsList.allCharacterCards[2]); // Magician



        graveyardPlayer = new KingBot("GRAVEYARD", new ArrayList<>(), game);
        graveyardPlayer.setCharacter(CharacterCardsList.allCharacterCards[5]); // Merchant
        graveyardPlayer.getActions().addGold(1); // 5 for building graveyard and 1 to use the effect of the graveyard
        graveyardPlayer.setCity(new City(List.of(graveyard)));
    }

    @Test
    void useEffect() {
    }
}