package fr.citadels.players;

import fr.citadels.cards.characters.CharacterCardsList;
import fr.citadels.cards.districts.DistrictCard;
import fr.citadels.cards.districts.DistrictCardsPile;

import java.util.List;
import java.util.Random;

public class BotFirstStrategy extends Player {

    /*
     * constants
     */

    private final Random RAND;

    /*
     * Constructor
     */
    public BotFirstStrategy(String name, List<DistrictCard> cards, Random random) {
        super(name, cards);
        RAND = random;
    }


    /*
     * Methods
     */

    /***
     * choose a card to play among the cards drawn
     * drawnCards must contain at least 1 card
     * @param pile pile of cards
     * @param drawnCards cards drawn
     * @return the card to play
     */
    public DistrictCard chooseCardAmongDrawn(DistrictCardsPile pile, DistrictCard[] drawnCards) {
        int randomIndex = RAND.nextInt(drawnCards.length);
        DistrictCard cardToPlay = drawnCards[randomIndex];
        putBack(drawnCards, pile, randomIndex);
        return cardToPlay;
    }

    /***
     * choose a card in hand
     * @return the card chosen or null if no card can be chosen
     */
    public DistrictCard chooseCardInHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).getGoldCost() <= gold && !hasCardInCity(cardsInHand.get(i)))
                return cardsInHand.remove(i);
        }
        return null;
    }

    /***
     * play a round for the linked player
     * @param  pile of cards
     * @return the actions of the player
     */
    @Override
    public String play(DistrictCardsPile pile) {
        boolean draw = true;
        StringBuilder actions = new StringBuilder();
        actions.append(this.getName());

        if (RAND.nextBoolean()) {
            draw = false;
            try {
                addGold(2);
            } catch (IllegalArgumentException e) {
                draw = true;
            }
        }
        if (draw) {
            DistrictCard[] drawnCards = pile.draw(2);
            if (drawnCards.length != 0) {//if there is at least 1 card
                DistrictCard cardToPlay = chooseCardAmongDrawn(pile, drawnCards);
                cardsInHand.add(cardToPlay);
            }
        }

        if (RAND.nextBoolean() && !cardsInHand.isEmpty()) {
            DistrictCard cardToPlace = chooseCardInHand();
            if (cardToPlace != null) {
                cityCards.add(cardToPlace);
                pay(cardToPlace.getGoldCost());
                actions.append(" a ajouté a sa ville : ").append(cardToPlace.getCardName());
            } else {
                actions.append(" n'a pas construit ce tour-ci");
            }
        } else actions.append(" n'a pas construit ce tour-ci");

        return actions.toString();
    }


    /**
     * Choose randomly a characterCard from the list of character.
     *
     * @param characters the list of characterCard.
     */
    public void chooseCharacter(CharacterCardsList characters) {
        this.character = characters.remove(this.RAND.nextInt(characters.size()));
    }

}
