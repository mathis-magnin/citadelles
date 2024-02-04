package fr.citadels.cards.charactercards;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.cards.districtcards.DistrictCardsPile;
import fr.citadels.cards.districtcards.uniques.Graveyard;
import fr.citadels.engine.Game;

import java.util.*;

public class CharacterCardsList extends ArrayList<CharacterCard> {

    /* Static Content */

    public static final CharacterCard[] allCharacterCards = new CharacterCard[]{
            new AssassinCard(), // 0
            new ThiefCard(),
            new MagicianCard(),
            new KingCard(),
            new BishopCard(),
            new MerchantCard(), // 5
            new ArchitectCard(),
            new WarlordCard()
    };

    /**
     * This Map indicates the number of character cards to remove face up (value index 0)
     * and face down (value index 1) from the character cards deck at the beginning of the
     * character selection phase according to the number of players in the game (key).
     */
    public static final Map<Integer, Integer[]> characterCardsToRemove = Map.ofEntries(
            Map.entry(4, new Integer[]{2, 1}),
            Map.entry(5, new Integer[]{1, 1}),
            Map.entry(6, new Integer[]{0, 1}),
            Map.entry(7, new Integer[]{0, 1})
    );

    /* Constructors */

    public CharacterCardsList() {
    }

    public CharacterCardsList(CharacterCard[] characterCards) {
        this.addAll(List.of(characterCards));
    }

    /* Methods */

    /**
     * remove face up a certain amount of character cards from the character cards
     * deck according to the number of players in the game
     *
     * @return the character cards that were removed face up from the character cards deck
     */
    public CharacterCard[] removeCharactersFaceUp() {
        int nbCardsToRemove = CharacterCardsList.characterCardsToRemove.get(Game.NB_PLAYERS)[0];
        CharacterCard[] removedCharacters = new CharacterCard[nbCardsToRemove];
        Collections.shuffle(this);
        for (int i = 0; i < nbCardsToRemove; i++) {
            if (this.get(0).equals(new KingCard())) {
                removedCharacters[i] = this.remove(1);
            } else {
                removedCharacters[i] = this.remove(0);
            }
        }
        return removedCharacters;
    }

    /**
     * remove face down a certain amount of character cards from the character cards
     * deck according to the number of players in the game
     *
     * @return the character cards that were removed face down from the character cards deck
     */
    public CharacterCard[] removeCharactersFaceDown() {
        int nbCardsToRemove = CharacterCardsList.characterCardsToRemove.get(Game.NB_PLAYERS)[1];
        CharacterCard[] removedCharacters = new CharacterCard[nbCardsToRemove];
        Collections.shuffle(this);
        for (int i = 0; i < nbCardsToRemove; i++) {
            removedCharacters[i] = this.remove(0);
        }
        return removedCharacters;
    }

}
