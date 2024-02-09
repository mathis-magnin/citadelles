package fr.citadels.cards.characters;

import fr.citadels.cards.characters.roles.*;
import fr.citadels.engine.Game;

import java.util.Map;
import java.util.Random;

public class CharactersDeck {

    /* Static content */

    /**
     * This Map indicates the number of character cards to remove face up (value index 0)
     * and face down (value index 1) from the character cards deck at the beginning of the
     * character selection phase according to the number of players in the game (key).
     */
    private static final Map<Integer, Integer[]> charactersToRemove = Map.ofEntries(
            Map.entry(4, new Integer[]{2, 1}),
            Map.entry(5, new Integer[]{1, 1}),
            Map.entry(6, new Integer[]{0, 1}),
            Map.entry(7, new Integer[]{0, 1})
    );


    /* Attributes */

    private final Random random;

    private final Character[] characters;
    private Character[] faceUpCharacters;
    private Character[] faceDownCharacters;


    /* Constructor */

    public CharactersDeck(Random random) {
        this.random = random;
        characters = new Character[]{
                new Assassin(),
                new Thief(),
                new Magician(),
                new King(),
                new Bishop(),
                new Merchant(),
                new Architect(),
                new Warlord()
        };
        this.faceUpCharacters = null;
        this.faceDownCharacters = null;
    }


    /* Basic methods */

    public Character[] get() {
        return this.characters;
    }


    public Character[] getFaceUpCharacters() {
        return this.faceUpCharacters;
    }


    public Character[] getFaceDownCharacters() {
        return this.faceDownCharacters;
    }


    /* Methods */

    /**
     * Determine a certain amount of character from the deck to be face up, according to the number of player in the game.
     * Determine a certain amount of character from the deck to be face down, according to the number of player in the game.
     */
    public void determineRemovedCharacters() {
        this.determineFaceUpCharacters();
        this.determineFaceDownCharacters();
    }


    /**
     * Determine a certain amount of character from the deck to be face up, according to the number of player in the game.
     */
    private void determineFaceUpCharacters() {
        int removeCardNumber = CharactersDeck.charactersToRemove.get(Game.PLAYER_NUMBER)[0];
        Character[] removedCharacters = new Character[removeCardNumber];
        int i = 0;
        while(i < removeCardNumber) {
            int randomIndex = this.random.nextInt(8);
            if (!this.characters[randomIndex].equals(new King())) {
                removedCharacters[i] = this.characters[randomIndex];
                i++;
            }
        }
        this.faceUpCharacters = removedCharacters;
    }


    /**
     * Determine a certain amount of character from the deck to be face down, according to the number of player in the game.
     */
    private void determineFaceDownCharacters() {
        int removeCardNumber = CharactersDeck.charactersToRemove.get(Game.PLAYER_NUMBER)[1];
        Character[] removedCharacters = new Character[removeCardNumber];
        int i = 0;
        while(i < removeCardNumber) {
            int randomIndex = this.random.nextInt(8);
            int j = 0;
            boolean contains = false;
            while ((j < this.faceUpCharacters.length) && !contains) {
                if (!this.faceUpCharacters[j].equals(this.characters[randomIndex])) {
                    removedCharacters[i] = this.characters[randomIndex];
                    contains = true;
                    i++;
                }
                j++;
            }
        }
        this.faceDownCharacters = removedCharacters;
    }

}