package fr.citadels.cards.charactercards;

import fr.citadels.cards.charactercards.characters.*;
import fr.citadels.engine.Game;

import java.util.*;

public class CharactersList extends ArrayList<Character> {

    /* Static Content */

    public static final Character[] allCharacterCards = new Character[]{
            new Assassin(), // 0
            new Thief(),
            new Magician(),
            new King(),
            new Bishop(),
            new Merchant(), // 5
            new Architect(),
            new Warlord()
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

    public CharactersList() {
    }

    public CharactersList(Character[] characterCards) {
        this.addAll(List.of(characterCards));
    }

    /* Methods */

    /**
     * remove face up a certain amount of character cards from the character cards
     * deck according to the number of players in the game
     *
     * @return the character cards that were removed face up from the character cards deck
     */
    public Character[] removeCharactersFaceUp() {
        int nbCardsToRemove = CharactersList.characterCardsToRemove.get(Game.NB_PLAYERS)[0];
        Character[] removedCharacters = new Character[nbCardsToRemove];
        Collections.shuffle(this);
        for (int i = 0; i < nbCardsToRemove; i++) {
            if (this.get(0).equals(new King())) {
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
    public Character[] removeCharactersFaceDown() {
        int nbCardsToRemove = CharactersList.characterCardsToRemove.get(Game.NB_PLAYERS)[1];
        Character[] removedCharacters = new Character[nbCardsToRemove];
        Collections.shuffle(this);
        for (int i = 0; i < nbCardsToRemove; i++) {
            removedCharacters[i] = this.remove(0);
        }
        return removedCharacters;
    }

}
